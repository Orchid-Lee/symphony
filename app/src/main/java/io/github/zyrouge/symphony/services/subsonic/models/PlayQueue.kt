package io.github.zyrouge.symphony.services.subsonic.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import io.github.zyrouge.symphony.services.subsonic.models.Child
import java.util.*

@Keep
class PlayQueue {
    @SerializedName("entry")
    var entries: List<Child>? = null
    var current: String? = null
    var position: Long? = null
    var username: String? = null
    var changed: Date? = null
    var changedBy: String? = null
}