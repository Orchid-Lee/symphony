package io.github.zyrouge.symphony.services.subsonic.models

import androidx.annotation.Keep

@Keep
class VideoConversion {
    var id: String? = null
    var bitRate: Int? = null
    var audioTrackId: Int? = null
}