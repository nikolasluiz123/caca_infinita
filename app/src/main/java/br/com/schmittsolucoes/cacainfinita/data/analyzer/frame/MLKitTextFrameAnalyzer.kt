package br.com.schmittsolucoes.cacainfinita.data.analyzer.frame

import android.media.Image
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import br.com.schmittsolucoes.cacainfinita.domain.model.BoundingBox
import br.com.schmittsolucoes.cacainfinita.domain.model.DetectedLine
import br.com.schmittsolucoes.cacainfinita.domain.model.ImageDimension
import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.AnalyzerState
import br.com.schmittsolucoes.cacainfinita.domain.model.result.FrameAnalysisResult
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class MLKitTextFrameAnalyzer @Inject constructor(): FrameAnalyzer {
    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    private val _state = MutableStateFlow(FrameAnalysisResult())
    override val state: StateFlow<FrameAnalysisResult> = _state.asStateFlow()

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(frame: Frame) {
        val imageProxyFrame = frame as ImageProxyFrame
        val imageProxy = imageProxyFrame.imageProxy
        val mediaImage: Image? = imageProxy.image

        if (mediaImage != null) {
            val rotationDegrees = imageProxy.imageInfo.rotationDegrees
            val isPortrait = rotationDegrees == 90 || rotationDegrees == 270

            val imageWidth = if (isPortrait) imageProxy.height else imageProxy.width
            val imageHeight = if (isPortrait) imageProxy.width else imageProxy.height
            val imageDimensions = ImageDimension(width = imageWidth, height = imageHeight)

            val image = InputImage.fromMediaImage(mediaImage, rotationDegrees)

            recognizer.process(image)
                .addOnSuccessListener { visionText ->
                    val lines = visionText.textBlocks.flatMap { block ->
                        block.lines.mapNotNull { line ->
                            line.boundingBox?.let { box ->
                                val confidence = line.confidence

                                val individualState = when {
                                    confidence >= 0.7f -> AnalyzerState.ALIGNED
                                    confidence >= 0.3f -> AnalyzerState.PARTIAL
                                    else -> AnalyzerState.NOT_DETECTED
                                }

                                DetectedLine(
                                    boundingBox = BoundingBox(
                                        left = box.left.toFloat(),
                                        top = box.top.toFloat(),
                                        right = box.right.toFloat(),
                                        bottom = box.bottom.toFloat()
                                    ),
                                    state = individualState,
                                    confidence = confidence
                                )
                            }
                        }
                    }

                    if (lines.isEmpty()) {
                        _state.value = FrameAnalysisResult(
                            state = AnalyzerState.NOT_DETECTED,
                            lines = emptyList(),
                            sourceDimensions = imageDimensions
                        )
                    } else {
                        val highConfidenceCount = lines.count { (it.confidence ?: 0f) >= 0.7f }

                        val globalState = if (highConfidenceCount >= 5) {
                            AnalyzerState.ALIGNED
                        } else {
                            AnalyzerState.PARTIAL
                        }

                        _state.value = FrameAnalysisResult(
                            state = globalState,
                            lines = lines,
                            sourceDimensions = imageDimensions
                        )
                    }
                }
                .addOnFailureListener {
                    _state.value = FrameAnalysisResult(
                        state = AnalyzerState.NOT_DETECTED,
                        lines = emptyList(),
                        sourceDimensions = imageDimensions
                    )
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        } else {
            imageProxy.close()
        }
    }

    override fun close() {
        recognizer.close()
    }
}