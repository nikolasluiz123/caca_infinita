package br.com.schmittsolucoes.cacainfinita.data.analyzer.frame

import androidx.camera.core.ImageProxy

/**
 * Implementação de [Frame] que encapsula um [ImageProxy] do CameraX.
 */
class ImageProxyFrame(
    val imageProxy: ImageProxy
) : Frame
