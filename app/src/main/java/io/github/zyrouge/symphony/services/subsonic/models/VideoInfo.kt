package io.github.zyrouge.symphony.services.subsonic.models

import androidx.annotation.Keep
import io.github.zyrouge.symphony.services.subsonic.models.AudioTrack
import io.github.zyrouge.symphony.services.subsonic.models.Captions
import io.github.zyrouge.symphony.services.subsonic.models.VideoConversion

@Keep
class VideoInfo {
    var captions: List<Captions>? = null
    var audioTracks: List<AudioTrack>? = null
    var conversions: List<VideoConversion>? = null
    var id: String? = null
}