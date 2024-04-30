package io.github.zyrouge.symphony.services.subsonic.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import io.github.zyrouge.symphony.services.subsonic.models.Share

@Keep
class Shares {
    @SerializedName("share")
    var shares: List<Share>? = null
}