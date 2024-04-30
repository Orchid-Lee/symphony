package io.github.zyrouge.symphony.services.subsonic.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import io.github.zyrouge.symphony.services.subsonic.models.PodcastChannel

@Keep
class Podcasts {
    @SerializedName("channel")
    var channels: List<PodcastChannel>? = null
}