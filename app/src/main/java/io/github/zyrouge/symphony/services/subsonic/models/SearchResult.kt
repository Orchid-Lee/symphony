package io.github.zyrouge.symphony.services.subsonic.models

import androidx.annotation.Keep
import io.github.zyrouge.symphony.services.subsonic.models.Child

@Keep
class SearchResult {
    var matches: List<Child>? = null
    var offset = 0
    var totalHits = 0
}