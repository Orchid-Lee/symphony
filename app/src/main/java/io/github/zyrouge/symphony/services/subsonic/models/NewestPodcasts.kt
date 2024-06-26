package io.github.zyrouge.symphony.services.subsonic.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
class NewestPodcasts {
    @SerializedName("episode")
    var episodes: List<PodcastEpisode>? = null
}