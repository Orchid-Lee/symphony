package io.github.zyrouge.symphony.services.subsonic.models

import androidx.annotation.Keep

@Keep
class Line {
    var start: Int? = null
    lateinit var value: String
}