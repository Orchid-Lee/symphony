package io.github.zyrouge.symphony.services.subsonic.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import io.github.zyrouge.symphony.services.subsonic.models.Artist
import io.github.zyrouge.symphony.services.subsonic.models.Child
import io.github.zyrouge.symphony.services.subsonic.models.Index

@Keep
class Indexes {
    var shortcuts: List<Artist>? = null
    @SerializedName("index")
    var indices: List<Index>? = null
    var children: List<Child>? = null
    var lastModified: Long = 0
    var ignoredArticles: String? = null
}