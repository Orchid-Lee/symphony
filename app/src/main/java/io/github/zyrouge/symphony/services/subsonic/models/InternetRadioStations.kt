package io.github.zyrouge.symphony.services.subsonic.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import io.github.zyrouge.symphony.services.subsonic.models.InternetRadioStation

@Keep
class InternetRadioStations {
    @SerializedName("internetRadioStation")
    var internetRadioStations: List<InternetRadioStation>? = null
}