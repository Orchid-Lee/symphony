package io.github.zyrouge.symphony.services.database.adapters

import java.io.File

class FileDatabaseAdapter(val file: File) {
    fun overwrite(content: String) = overwrite(content.toByteArray())
    private fun overwrite(bytes: ByteArray) {
        file.outputStream().use { it.write(bytes) }
    }

    fun read() = file.inputStream().use { String(it.readBytes()) }
}
