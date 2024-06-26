package io.github.zyrouge.symphony.services.groove

import androidx.annotation.Keep

@Keep
data class HomeSector(
        val id: String,
        val sectorTitle: String,
        var isVisible: Boolean,
        val order: Int,
)