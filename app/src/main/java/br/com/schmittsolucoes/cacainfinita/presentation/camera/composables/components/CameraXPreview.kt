package br.com.schmittsolucoes.cacainfinita.presentation.camera.composables.components

import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.FocusMeteringAction
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.concurrent.futures.await
import androidx.lifecycle.compose.LocalLifecycleOwner
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.time.Duration.Companion.seconds

@Composable
fun CameraXPreview(
    onAnalyzeFrame: (ImageProxy) -> Unit,
    modifier: Modifier = Modifier,
    isCapturing: Boolean = false,
    imageCapture: ImageCapture? = null,
    isTorchActive: Boolean = false
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val previewView = remember { PreviewView(context) }
    val analyzerExecutor = remember { Executors.newSingleThreadExecutor() }
    val camera = remember { mutableStateListOf<androidx.camera.core.Camera>() }
    val scope = rememberCoroutineScope()

    val focusPoint = remember { mutableStateOf<Offset?>(null) }
    val focusAlpha = remember { Animatable(0f) }
    val focusScale = remember { Animatable(1.5f) }

    val currentIsCapturing by rememberUpdatedState(isCapturing)
    val currentOnAnalyzeFrame by rememberUpdatedState(onAnalyzeFrame)

    LaunchedEffect(isTorchActive) {
        camera.firstOrNull()?.cameraControl?.enableTorch(isTorchActive)
    }

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

            val boundCamera = cameraProvider.bindToLifecycle(
                lifecycleOwner = lifecycleOwner,
                cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
                useCases = useCases.toTypedArray()
            )

            camera.clear()
            camera.add(boundCamera)
            boundCamera.cameraControl.enableTorch(isTorchActive)
        } catch (e: Exception) {
            Log.e("DEBUG_PROCESS", "CameraXPreview: Falha ao vincular CameraX", e)
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        AndroidView(
            factory = { previewView },
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(camera) {
                    detectTapGestures { offset ->
                        val currentCamera = camera.firstOrNull() ?: return@detectTapGestures
                        val factory = previewView.meteringPointFactory
                        val point = factory.createPoint(offset.x, offset.y)

                        val action = FocusMeteringAction
                            .Builder(point, FocusMeteringAction.FLAG_AF or FocusMeteringAction.FLAG_AE)
                            .setAutoCancelDuration(3, TimeUnit.SECONDS)
                            .build()

                        currentCamera.cameraControl.startFocusAndMetering(action)

                        scope.launch {
                            focusPoint.value = offset
                            focusAlpha.snapTo(1f)
                            focusScale.snapTo(1.5f)

                            launch {
                                focusScale.animateTo(1f, animationSpec = tween(300))
                            }

                            delay(2.seconds)
                            focusAlpha.animateTo(0f, animationSpec = tween(500))
                            focusPoint.value = null
                        }
                    }
                }
        )

        focusPoint.value?.let { point ->
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                drawCircle(
                    color = Color.White.copy(alpha = focusAlpha.value),
                    radius = 40.dp.toPx() * focusScale.value,
                    center = point,
                    style = Stroke(width = 2.dp.toPx())
                )
            }
        }
    }
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