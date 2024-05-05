package io.github.zyrouge.symphony.services.groove

import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.MutableLiveData
import com.cappielloantonio.tempo.subsonic.base.ApiResponse
import io.github.zyrouge.symphony.App
import io.github.zyrouge.symphony.Symphony
import io.github.zyrouge.symphony.services.subsonic.models.Child
import io.github.zyrouge.symphony.ui.helpers.Assets
import io.github.zyrouge.symphony.ui.helpers.createHandyImageRequest
import io.github.zyrouge.symphony.utils.FuzzySearchOption
import io.github.zyrouge.symphony.utils.FuzzySearcher
import io.github.zyrouge.symphony.utils.joinToStringIfNotEmpty
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ConcurrentHashMap
import kotlin.math.min

enum class SongSortBy {
    CUSTOM,
    TITLE,
    ARTIST,
    ALBUM,
    DURATION,
    DATE_ADDED,
    DATE_MODIFIED,
    COMPOSER,
    ALBUM_ARTIST,
    YEAR,
    FILENAME,
    TRACK_NUMBER,
}

class SongRepository(private val symphony: Symphony) {
    private val cache = ConcurrentHashMap<Long, Song>()
    internal val pathCache = ConcurrentHashMap<String, Long>()
    private val searcher = FuzzySearcher<Long>(
        options = listOf(
            FuzzySearchOption({ v -> get(v)?.title?.let { compareString(it) } }, 3),
            FuzzySearchOption({ v -> get(v)?.filename?.let { compareString(it) } }, 2),
            FuzzySearchOption({ v -> get(v)?.artists?.let { compareCollection(it) } }),
            FuzzySearchOption({ v -> get(v)?.album?.let { compareString(it) } })
        )
    )

    val isUpdating get() = symphony.groove.mediaStore.isUpdating
    private val _all = MutableStateFlow<List<Long>>(emptyList())
    val all = _all.asStateFlow()
    private val _count = MutableStateFlow(0)
    val count = _count.asStateFlow()
    private val _id = MutableStateFlow(System.currentTimeMillis())
    val id = _id.asStateFlow()
    var explorer = GrooveExplorer.Folder()

    private fun emitCount() = _count.update { cache.size }

    private fun emitIds() = _id.update {
        System.currentTimeMillis()
    }

    internal fun onSong(song: Song) {
        cache[song.id] = song
        pathCache[song.path] = song.id
        val entity = explorer
            .addRelativePath(GrooveExplorer.Path(song.path)) as GrooveExplorer.File
        entity.data = song.id
        emitIds()
        _all.update {
            it + song.id
        }
        emitCount()
    }

    internal fun onFinish() {}

    fun reset() {
        cache.clear()
        pathCache.clear()
        explorer = GrooveExplorer.Folder()
        emitIds()
        _all.update {
            emptyList()
        }
        emitCount()
    }

    fun search(songIds: List<Long>, terms: String, limit: Int = 7) = searcher
        .search(terms, songIds, maxLength = limit)

    fun sort(songIds: List<Long>, by: SongSortBy, reverse: Boolean): List<Long> {
        val sorted = when (by) {
            SongSortBy.CUSTOM -> songIds
            SongSortBy.TITLE -> songIds.sortedBy { get(it)?.title }
            SongSortBy.ARTIST -> songIds.sortedBy { get(it)?.artists?.joinToStringIfNotEmpty() }
            SongSortBy.ALBUM -> songIds.sortedBy { get(it)?.album }
            SongSortBy.DURATION -> songIds.sortedBy { get(it)?.duration }
            SongSortBy.DATE_ADDED -> songIds.sortedBy { get(it)?.dateAdded }
            SongSortBy.DATE_MODIFIED -> songIds.sortedBy { get(it)?.dateModified }
            SongSortBy.COMPOSER -> songIds.sortedBy { get(it)?.composers?.joinToStringIfNotEmpty() }
            SongSortBy.ALBUM_ARTIST -> songIds.sortedBy { get(it)?.additional?.albumArtists?.joinToStringIfNotEmpty() }
            SongSortBy.YEAR -> songIds.sortedBy { get(it)?.year }
            SongSortBy.FILENAME -> songIds.sortedBy { get(it)?.filename }
            SongSortBy.TRACK_NUMBER -> songIds.sortedBy { get(it)?.trackNumber }
        }
        return if (reverse) sorted.reversed() else sorted
    }

    fun count() = cache.size
    fun ids() = cache.keys.toList()
    fun values() = cache.values.toList()

    fun get(id: Long) = cache[id]
    fun get(ids: List<Long>) = ids.mapNotNull { get(it) }

    /**
     * 获取文件资源URI
     */
    fun getArtworkUri(songId: Long): Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        .buildUpon()
        .run {
            appendPath(songId.toString())
            appendPath("albumart")
            build()
        }

    fun getImage(songId: Long): String {
        return "https://pbs.twimg.com/media/EXjZV6_U4AA8hgx.jpg"
    }

    fun getDefaultArtworkUri() = Assets.getPlaceholderUri(symphony)

    fun createArtworkImageRequest(songId: Long) = createHandyImageRequest(
        symphony.applicationContext,
        image = getArtworkUri(songId),
        fallback = Assets.getPlaceholderId(symphony),
    )

    fun getImageByUrl(songId: Long) = createHandyImageRequest(
        symphony.applicationContext,
        image = getImage(songId),
        fallback = Assets.getPlaceholderId(symphony),
    )

    fun getStarredSongs(random: Boolean, size: Int): List<Child?> {
        val starredSongs: List<Child> = mutableListOf()

        App.getSubsonicClientInstance(false)
            .getAlbumSongListClient()
            .getStarred2()
            .enqueue(object : Callback<ApiResponse?> {
                override fun onResponse(
                    call: Call<ApiResponse?>,
                    response: Response<ApiResponse?>,
                ) {
                    if (response.isSuccessful && response.body() != null && response.body()!!.subsonicResponse.starred2 != null) {
                        val songs =
                            response.body()!!.subsonicResponse.starred2!!.songs?.toMutableList()
                        if (songs != null) {
                            if (!random) {
                                starredSongs.plus(songs)
                            } else {
                                songs.shuffle()
                                starredSongs.plus(
                                    songs.subList(
                                        0,
                                        min(size.toDouble(), songs.size.toDouble()).toInt()
                                    )
                                )
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<ApiResponse?>, t: Throwable) {
                }
            })
        return starredSongs
    }


    fun getInstantMix(song: Child, count: Int): MutableLiveData<List<Child>?> {
        val instantMix = MutableLiveData<List<Child>?>()

        App.getSubsonicClientInstance(false)
            .browsingClient
            .getSimilarSongs2(song.id, count)
            .enqueue(object : Callback<ApiResponse?> {
                override fun onResponse(
                    call: Call<ApiResponse?>,
                    response: Response<ApiResponse?>,
                ) {
                    if (response.isSuccessful && response.body() != null && response.body()!!.subsonicResponse.similarSongs2 != null) {
                        instantMix.setValue(response.body()!!.subsonicResponse.similarSongs2!!.songs)
                    }
                }

                override fun onFailure(call: Call<ApiResponse?>, t: Throwable) {
                    instantMix.setValue(null)
                }
            })

        return instantMix
    }

    fun getRandomSample(number: Int, fromYear: Int?, toYear: Int?): MutableLiveData<List<Child>> {
        val randomSongsSample = MutableLiveData<List<Child>>()

        App.getSubsonicClientInstance(false)
            .albumSongListClient
            .getRandomSongs(number, fromYear, toYear)
            .enqueue(object : Callback<ApiResponse?> {
                override fun onResponse(
                    call: Call<ApiResponse?>,
                    response: Response<ApiResponse?>,
                ) {
                    val songs: MutableList<Child> = ArrayList()

                    if (response.isSuccessful && response.body() != null && response.body()!!.subsonicResponse.randomSongs != null && response.body()!!.subsonicResponse.randomSongs!!.songs != null) {
                        songs.addAll(response.body()!!.subsonicResponse.randomSongs!!.songs!!)
                    }

                    randomSongsSample.value = songs
                }

                override fun onFailure(call: Call<ApiResponse?>, t: Throwable) {
                }
            })

        return randomSongsSample
    }

    fun scrobble(id: String?, submission: Boolean) {
        App.getSubsonicClientInstance(false)
            .mediaAnnotationClient
            .scrobble(id, submission)
            .enqueue(object : Callback<ApiResponse?> {
                override fun onResponse(
                    call: Call<ApiResponse?>,
                    response: Response<ApiResponse?>,
                ) {
                }

                override fun onFailure(call: Call<ApiResponse?>, t: Throwable) {
                }
            })
    }

    fun setRating(id: String?, rating: Int) {
        App.getSubsonicClientInstance(false)
            .mediaAnnotationClient
            .setRating(id, rating)
            .enqueue(object : Callback<ApiResponse?> {
                override fun onResponse(
                    call: Call<ApiResponse?>,
                    response: Response<ApiResponse?>,
                ) {
                }

                override fun onFailure(call: Call<ApiResponse?>, t: Throwable) {
                }
            })
    }

    fun getSongsByGenre(id: String?, page: Int): MutableLiveData<List<Child>?> {
        val songsByGenre = MutableLiveData<List<Child>?>()

        App.getSubsonicClientInstance(false)
            .albumSongListClient
            .getSongsByGenre(id, 100, 100 * page)
            .enqueue(object : Callback<ApiResponse?> {
                override fun onResponse(
                    call: Call<ApiResponse?>,
                    response: Response<ApiResponse?>,
                ) {
                    if (response.isSuccessful && response.body() != null && response.body()!!.subsonicResponse.songsByGenre != null) {
                        songsByGenre.value = response.body()!!.subsonicResponse.songsByGenre!!.songs
                    }
                }

                override fun onFailure(call: Call<ApiResponse?>, t: Throwable) {
                }
            })

        return songsByGenre
    }

    fun getSongsByGenres(genresId: java.util.ArrayList<String?>): MutableLiveData<List<Child>> {
        val songsByGenre = MutableLiveData<List<Child>>()

        for (id in genresId) App.getSubsonicClientInstance(false)
            .albumSongListClient
            .getSongsByGenre(id, 500, 0)
            .enqueue(object : Callback<ApiResponse?> {
                override fun onResponse(
                    call: Call<ApiResponse?>,
                    response: Response<ApiResponse?>,
                ) {
                    val songs: MutableList<Child> = java.util.ArrayList()

                    if (response.isSuccessful && response.body() != null && response.body()!!.subsonicResponse.songsByGenre != null) {
                        songs.addAll(response.body()!!.subsonicResponse.songsByGenre!!.songs!!)
                    }

                    songsByGenre.value = songs
                }

                override fun onFailure(call: Call<ApiResponse?>, t: Throwable) {
                }
            })

        return songsByGenre
    }

    fun getSong(id: String?): MutableLiveData<Child?> {
        val song = MutableLiveData<Child?>()

        App.getSubsonicClientInstance(false)
            .browsingClient
            .getSong(id)
            .enqueue(object : Callback<ApiResponse?> {
                override fun onResponse(
                    call: Call<ApiResponse?>,
                    response: Response<ApiResponse?>,
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        song.value = response.body()!!.subsonicResponse.song
                    }
                }

                override fun onFailure(call: Call<ApiResponse?>, t: Throwable) {
                }
            })

        return song
    }

    fun getSongLyrics(song: Child): MutableLiveData<String?> {
        val lyrics = MutableLiveData<String?>(null)

        App.getSubsonicClientInstance(false)
            .mediaRetrievalClient
            .getLyrics(song.artist, song.title)
            .enqueue(object : Callback<ApiResponse?> {
                override fun onResponse(
                    call: Call<ApiResponse?>,
                    response: Response<ApiResponse?>,
                ) {
                    if (response.isSuccessful && response.body() != null && response.body()!!.subsonicResponse.lyrics != null) {
                        lyrics.setValue(response.body()!!.subsonicResponse.lyrics!!.value)
                    }
                }

                override fun onFailure(call: Call<ApiResponse?>, t: Throwable) {
                }
            })

        return lyrics
    }
}
