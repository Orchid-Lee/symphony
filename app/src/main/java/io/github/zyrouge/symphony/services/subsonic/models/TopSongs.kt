package io.github.zyrouge.symphony.services.subsonic.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import io.github.zyrouge.symphony.services.subsonic.models.Child

@Keep
class TopSongs {
    @SerializedName("song")
    var songs: List<Child>? = null
}