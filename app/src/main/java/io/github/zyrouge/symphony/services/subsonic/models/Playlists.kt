package io.github.zyrouge.symphony.services.subsonic.models

import androidx.annotation.Keep
import com.cappielloantonio.tempo.subsonic.models.Playlist
import com.google.gson.annotations.SerializedName

@Keep
class Playlists(
    @SerializedName("playlist")
    var playlists: List<Playlist>? = null
)