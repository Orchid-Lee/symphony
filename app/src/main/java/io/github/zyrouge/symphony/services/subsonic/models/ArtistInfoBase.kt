package io.github.zyrouge.symphony.services.subsonic.models

import androidx.annotation.Keep

@Keep
open class ArtistInfoBase {
    var biography: String? = null
    var musicBrainzId: String? = null
    var lastFmUrl: String? = null
    var smallImageUrl: String? = null
    var mediumImageUrl: String? = null
    var largeImageUrl: String? = null
}