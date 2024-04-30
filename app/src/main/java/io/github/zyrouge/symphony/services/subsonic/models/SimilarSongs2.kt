package io.github.zyrouge.symphony.services.subsonic.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import io.github.zyrouge.symphony.services.subsonic.models.Child

@Keep
class SimilarSongs2 {
    @SerializedName("song")
    var songs: List<Child>? = null
}