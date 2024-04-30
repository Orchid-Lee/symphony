package io.github.zyrouge.symphony.services.subsonic.models

import androidx.annotation.Keep

@Keep
class Error {
    var code: Int? = null
    var message: String? = null
}