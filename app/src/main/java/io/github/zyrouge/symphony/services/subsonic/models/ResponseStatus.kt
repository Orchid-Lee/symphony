package io.github.zyrouge.symphony.services.subsonic.models

import androidx.annotation.Keep

@Keep
class ResponseStatus(val value: String) {
    companion object {
        @JvmField
        var OK = "ok"
        @JvmField
        var FAILED = "failed"
    }
}