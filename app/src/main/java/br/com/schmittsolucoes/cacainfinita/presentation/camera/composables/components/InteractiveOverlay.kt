package br.com.schmittsolucoes.cacainfinita.presentation.camera.composables.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import br.com.schmittsolucoes.cacainfinita.domain.model.DetectedLine
import br.com.schmittsolucoes.cacainfinita.domain.model.ImageDimension
import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.AnalyzerState
import kotlin.math.max

@Composable
fun InteractiveOverlay(
    analyzerState: AnalyzerState,
    detectedLines: List<DetectedLine>,
    sourceDimensions: ImageDimension?,
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier
            .fillMaxSize()
            .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        if (detectedLines.isNotEmpty() && sourceDimensions != null && analyzerState != AnalyzerState.NOT_DETECTED) {
            drawRect(color = Color.Black.copy(alpha = 0.6f))

            val imageWidth = sourceDimensions.width.toFloat()
            val imageHeight = sourceDimensions.height.toFloat()

            val scale = max(canvasWidth / imageWidth, canvasHeight / imageHeight)

            val scaledImageWidth = imageWidth * scale
            val scaledImageHeight = imageHeight * scale

            val offsetX = (scaledImageWidth - canvasWidth) / 2f
            val offsetY = (scaledImageHeight - canvasHeight) / 2f

            detectedLines.forEach { line ->
                val confidence = line.confidence ?: 0f

                if (line.state != AnalyzerState.NOT_DETECTED && confidence >= 0.3f) {
                    val box = line.boundingBox
                    
                    val mappedLeft = (box.left * scale) - offsetX
                    val mappedTop = (box.top * scale) - offsetY
                    val mappedRight = (box.right * scale) - offsetX
                    val mappedBottom = (box.bottom * scale) - offsetY

                    val rectWidth = mappedRight - mappedLeft
                    val rectHeight = mappedBottom - mappedTop

                    val individualColor = getConfidenceColor(confidence)

                    drawRoundRect(
                        color = Color.Transparent,
                        topLeft = Offset(mappedLeft, mappedTop),
                        size = Size(rectWidth, rectHeight),
                        cornerRadius = CornerRadius(8f, 8f),
                        blendMode = BlendMode.Clear
                    )

                    drawRoundRect(
                        color = individualColor,
                        topLeft = Offset(mappedLeft, mappedTop),
                        size = Size(rectWidth, rectHeight),
                        cornerRadius = CornerRadius(8f, 8f),
                        style = Stroke(width = 4f)
                    )
                }
            }
        }
    }
}

private fun getConfidenceColor(confidence: Float): Color {
    return when {
        confidence >= 0.7f -> {
            val fraction = (confidence - 0.7f) / 0.3f
            lerp(Color.Yellow, Color.Green, fraction.coerceIn(0f, 1f))
        }
        confidence >= 0.4f -> {
            val fraction = (confidence - 0.4f) / 0.3f
            lerp(Color.Red, Color.Yellow, fraction.coerceIn(0f, 1f))
        }
        else -> Color.Red
    }
}
