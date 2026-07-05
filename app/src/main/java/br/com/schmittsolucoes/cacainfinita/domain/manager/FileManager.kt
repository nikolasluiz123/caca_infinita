package br.com.schmittsolucoes.cacainfinita.domain.manager

import android.net.Uri
import java.io.File

interface FileManager {
    suspend fun copyUriToTempFile(uri: Uri): File?
    suspend fun deleteFile(file: File)
}
