package io.github.zyrouge.symphony.services.subsonic.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import io.github.zyrouge.symphony.services.subsonic.models.Child
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
class NowPlayingEntry(
    @SerializedName("_id")
    override val id: String
) : Child(id) {
    var username: String? = null
    var minutesAgo = 0
    var playerId = 0
    var playerName: String? = null
}