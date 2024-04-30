package io.github.zyrouge.symphony.services.subsonic.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import io.github.zyrouge.symphony.services.subsonic.models.Artist

@Keep
class Index {
    @SerializedName("artist")
    var artists: List<Artist>? = null
    var name: String? = null
}