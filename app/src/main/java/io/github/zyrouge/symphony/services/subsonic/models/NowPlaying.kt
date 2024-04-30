package io.github.zyrouge.symphony.services.subsonic.models

import androidx.annotation.Keep

@Keep
class NowPlaying {
    var entries: List<NowPlayingEntry>? = null
}