package io.github.zyrouge.symphony.services.subsonic.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import io.github.zyrouge.symphony.services.subsonic.models.Genre

@Keep
class Genres {
    @SerializedName("genre")
    var genres: List<Genre>? = null
}