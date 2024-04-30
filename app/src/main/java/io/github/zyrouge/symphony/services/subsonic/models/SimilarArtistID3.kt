package io.github.zyrouge.symphony.services.subsonic.models

import android.os.Parcelable
import androidx.annotation.Keep
import io.github.zyrouge.symphony.services.subsonic.models.ArtistID3
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
class SimilarArtistID3 : ArtistID3(), Parcelable