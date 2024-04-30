package io.github.zyrouge.symphony.services.subsonic.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import io.github.zyrouge.symphony.services.subsonic.models.AlbumID3

@Keep
class AlbumList2 {
    @SerializedName("album")
    var albums: List<AlbumID3>? = null
}