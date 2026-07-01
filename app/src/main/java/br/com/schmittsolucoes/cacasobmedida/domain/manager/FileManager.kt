package br.com.schmittsolucoes.cacasobmedida.domain.manager

import android.net.Uri
import java.io.File

interface FileManager {
    suspend fun copyUriToTempFile(uri: Uri): File?
    suspend fun deleteFile(file: File)
}
