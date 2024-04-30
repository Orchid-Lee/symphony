package io.github.zyrouge.symphony.services.subsonic.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import io.github.zyrouge.symphony.services.subsonic.models.AlbumID3
import io.github.zyrouge.symphony.services.subsonic.models.ArtistID3
import io.github.zyrouge.symphony.services.subsonic.models.Child

@Keep
class Starred2 {
    @SerializedName("artist")
    var artists: List<ArtistID3>? = null
    @SerializedName("album")
    var albums: List<AlbumID3>? = null
    @SerializedName("song")
    var songs: List<Child>? = null
}