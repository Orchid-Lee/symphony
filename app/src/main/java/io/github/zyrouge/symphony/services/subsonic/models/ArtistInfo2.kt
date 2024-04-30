package io.github.zyrouge.symphony.services.subsonic.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.util.*

@Keep
class ArtistInfo2 : ArtistInfoBase() {
    @SerializedName("similarArtist")
    var similarArtists: List<SimilarArtistID3>? = emptyList()
}