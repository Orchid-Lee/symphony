package io.github.zyrouge.symphony.services.subsonic.models

import androidx.annotation.Keep
import io.github.zyrouge.symphony.services.subsonic.models.Artist
import io.github.zyrouge.symphony.services.subsonic.models.Child

@Keep
class Starred {
    var artists: List<Artist>? = null
    var albums: List<Child>? = null
    var songs: List<Child>? = null
}