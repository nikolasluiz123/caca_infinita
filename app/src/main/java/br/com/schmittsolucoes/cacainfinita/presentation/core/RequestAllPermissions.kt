package br.com.schmittsolucoes.cacainfinita.presentation.core

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.content.ContextCompat

@Composable
fun RequestAllPermissions(context: Context) {
    val standardPermissionLauncher = PermissionUtils.requestMultiplePermissionsLauncher()

    LaunchedEffect(Unit) {
        val standardPermissions = mutableListOf<String>()
        addCameraPermission(context, standardPermissions)

        if (standardPermissions.isNotEmpty()) {
            standardPermissionLauncher.launch(standardPermissions.toTypedArray())
        }
    }
}

private fun addCameraPermission(
    context: Context,
    permissions: MutableList<String>
) {
    if (!context.verifyPermissionGranted(Manifest.permission.CAMERA)) {
        permissions.add(Manifest.permission.CAMERA)
    }
}

fun Context.verifyPermissionGranted(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}