package br.com.schmittsolucoes.cacainfinita.presentation.core

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

object PermissionUtils {
    @Composable
    fun requestMultiplePermissionsLauncher(onResult: (Map<String, Boolean>) -> Unit = { }): ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>> {
        return rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions(),
            onResult = onResult
        )
    }
}