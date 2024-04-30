package io.github.zyrouge.symphony.services.subsonic.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import io.github.zyrouge.symphony.services.subsonic.models.ArtistID3

@Keep
class IndexID3 {
    @SerializedName("artist")
    var artists: List<ArtistID3>? = null
    var name: String? = null
}