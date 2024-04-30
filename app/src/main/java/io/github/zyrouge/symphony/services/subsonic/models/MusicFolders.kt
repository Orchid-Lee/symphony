package io.github.zyrouge.symphony.services.subsonic.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import io.github.zyrouge.symphony.services.subsonic.models.MusicFolder

@Keep
class MusicFolders {
    @SerializedName("musicFolder")
    var musicFolders: List<MusicFolder>? = null
}