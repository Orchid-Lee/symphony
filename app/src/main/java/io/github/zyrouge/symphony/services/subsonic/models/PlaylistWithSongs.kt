package io.github.zyrouge.symphony.services.subsonic.models

import android.os.Parcelable
import androidx.annotation.Keep
import com.cappielloantonio.tempo.subsonic.models.Playlist
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
class PlaylistWithSongs(
    @SerializedName("_id")
    override var id: String
) : Playlist(id), Parcelable {
    @SerializedName("entry")
    var entries: List<Child>? = null
}