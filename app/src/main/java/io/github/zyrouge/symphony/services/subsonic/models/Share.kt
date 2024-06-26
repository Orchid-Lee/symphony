package io.github.zyrouge.symphony.services.subsonic.models

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import io.github.zyrouge.symphony.services.subsonic.models.Child
import kotlinx.parcelize.Parcelize
import java.util.*

@Keep
@Parcelize
class Share : Parcelable {
    @SerializedName("entry")
    var entries: List<Child>? = null
    var id: String? = null
    var url: String? = null
    var description: String? = null
    var username: String? = null
    var created: Date? = null
    var expires: Date? = null
    var lastVisited: Date? = null
    var visitCount = 0
}