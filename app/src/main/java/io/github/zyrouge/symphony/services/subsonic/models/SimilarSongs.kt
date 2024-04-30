package io.github.zyrouge.symphony.services.subsonic.models

import androidx.annotation.Keep
import io.github.zyrouge.symphony.services.subsonic.models.Child

@Keep
class SimilarSongs {
    var songs: List<Child>? = null
}