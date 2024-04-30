package com.cappielloantonio.tempo.subsonic.base

import androidx.annotation.Keep
import io.github.zyrouge.symphony.services.subsonic.models.SubsonicResponse
import com.google.gson.annotations.SerializedName

@Keep
class ApiResponse {
    @SerializedName("subsonic-response")
    lateinit var subsonicResponse: SubsonicResponse
}