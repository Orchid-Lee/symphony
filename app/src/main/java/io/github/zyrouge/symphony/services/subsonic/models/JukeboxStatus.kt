package io.github.zyrouge.symphony.services.subsonic.models

import androidx.annotation.Keep

@Keep
open class JukeboxStatus {
    var currentIndex = 0
    var isPlaying = false
    var gain = 0f
    var position: Int? = null
}