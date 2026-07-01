package br.com.schmittsolucoes.cacasobmedida.data.manager

import android.content.Context
import android.net.Uri
import br.com.schmittsolucoes.cacasobmedida.domain.manager.FileManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class FileHandler @Inject constructor(
    @param:ApplicationContext private val context: Context,
) : FileManager {

    override suspend fun copyUriToTempFile(uri: Uri): File? {
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        val file = File(context.cacheDir, "${System.currentTimeMillis()}.jpg")
        val outputStream = FileOutputStream(file)

        inputStream.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }

        return file
    }

    override suspend fun deleteFile(file: File) {
        if (file.exists()) {
            file.delete()
        }
    }
}
