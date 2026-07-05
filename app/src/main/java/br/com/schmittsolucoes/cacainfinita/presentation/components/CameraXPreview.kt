package br.com.schmittsolucoes.cacainfinita.presentation.components

import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.concurrent.futures.await
import androidx.lifecycle.compose.LocalLifecycleOwner
import java.io.File
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@Composable
fun CameraXPreview(
    onAnalyzeFrame: (ImageProxy) -> Unit,
    modifier: Modifier = Modifier,
    isCapturing: Boolean = false,
    imageCapture: ImageCapture? = null
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val previewView = remember { PreviewView(context) }
    val analyzerExecutor = remember { Executors.newSingleThreadExecutor() }

    val currentIsCapturing by rememberUpdatedState(isCapturing)
    val currentOnAnalyzeFrame by rememberUpdatedState(onAnalyzeFrame)

    DisposableEffect(Unit) {
        onDispose { analyzerExecutor.shutdown() }
    }

    LaunchedEffect(lifecycleOwner) {
        val cameraProvider = context.getCameraProvider()

        val preview = Preview.Builder().build().also {
            it.surfaceProvider = previewView.surfaceProvider
        }

        val imageAnalysis = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
            .also { analysis ->
                analysis.setAnalyzer(analyzerExecutor) { imageProxy ->
                    if (currentIsCapturing) {
                        imageProxy.close()
                    } else {
                        currentOnAnalyzeFrame(imageProxy)
                    }
                }
            }

        try {
            cameraProvider.unbindAll()

            val useCases = mutableListOf(preview, imageAnalysis)

            if (imageCapture != null) {
                useCases.add(imageCapture)
            }

            cameraProvider.bindToLifecycle(
                lifecycleOwner = lifecycleOwner,
                cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
                useCases = useCases.toTypedArray()
            )
        } catch (e: Exception) {
            Log.e("CameraXPreview", "Falha ao vincular CameraX", e)
        }
    }

    AndroidView(factory = { previewView }, modifier = modifier.fillMaxSize())
}

/**
 * Função utilitária limpa usando a extensão de corrotina da biblioteca concurrent-futures.
 */
private suspend fun Context.getCameraProvider(): ProcessCameraProvider {
    return ProcessCameraProvider.getInstance(this).await()
}

/**
 * Dispara o processo de captura e salva a imagem no cache do aplicativo.
 */
fun takePhoto(
    context: Context,
    imageCapture: ImageCapture,
    executor: Executor,
    onSuccess: (String) -> Unit,
    onError: (ImageCaptureException) -> Unit
) {
    val photoFile = File(
        context.cacheDir,
        "${System.currentTimeMillis()}.jpg"
    )

    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    imageCapture.takePicture(
        outputOptions,
        executor,
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                onSuccess(photoFile.absolutePath)
            }

            override fun onError(exception: ImageCaptureException) {
                onError(exception)
            }
        }
    )
}