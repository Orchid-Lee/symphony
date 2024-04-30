package io.github.zyrouge.symphony.services.subsonic.models

import androidx.annotation.Keep

@Keep
class JukeboxPlaylist : JukeboxStatus() {
    var entries: List<Child>? = null
}