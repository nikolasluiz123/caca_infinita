package br.com.schmittsolucoes.cacasobmedida.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import br.com.schmittsolucoes.cacasobmedida.domain.model.BoundingBox
import br.com.schmittsolucoes.cacasobmedida.domain.model.ImageDimension
import br.com.schmittsolucoes.cacasobmedida.domain.model.enumeration.AnalyzerState
import kotlin.math.max

@Composable
fun InteractiveOverlay(
    analyzerState: AnalyzerState,
    boundingBox: BoundingBox?,
    sourceDimensions: ImageDimension?,
    modifier: Modifier = Modifier
) {
    val outlineColor = when (analyzerState) {
        AnalyzerState.NOT_DETECTED -> Color.Red
        AnalyzerState.PARTIAL -> Color.Yellow
        AnalyzerState.ALIGNED -> Color.Green
    }

    val animatedLeft by animateFloatAsState(targetValue = boundingBox?.left ?: 0f, label = "left")
    val animatedTop by animateFloatAsState(targetValue = boundingBox?.top ?: 0f, label = "top")
    val animatedRight by animateFloatAsState(targetValue = boundingBox?.right ?: 0f, label = "right")
    val animatedBottom by animateFloatAsState(targetValue = boundingBox?.bottom ?: 0f, label = "bottom")

    Canvas(modifier = modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        if (boundingBox != null && sourceDimensions != null && analyzerState != AnalyzerState.NOT_DETECTED) {
            drawRect(color = Color.Black.copy(alpha = 0.6f))

            val imageWidth = sourceDimensions.width.toFloat()
            val imageHeight = sourceDimensions.height.toFloat()

            val scale = max(canvasWidth / imageWidth, canvasHeight / imageHeight)

            val scaledImageWidth = imageWidth * scale
            val scaledImageHeight = imageHeight * scale

            val offsetX = (scaledImageWidth - canvasWidth) / 2f
            val offsetY = (scaledImageHeight - canvasHeight) / 2f

            val mappedLeft = (animatedLeft * scale) - offsetX
            val mappedTop = (animatedTop * scale) - offsetY
            val mappedRight = (animatedRight * scale) - offsetX
            val mappedBottom = (animatedBottom * scale) - offsetY

            val rectWidth = mappedRight - mappedLeft
            val rectHeight = mappedBottom - mappedTop

            drawRoundRect(
                color = Color.Transparent,
                topLeft = Offset(mappedLeft, mappedTop),
                size = Size(rectWidth, rectHeight),
                cornerRadius = CornerRadius(16f, 16f),
                blendMode = BlendMode.Clear
            )

            drawRoundRect(
                color = outlineColor,
                topLeft = Offset(mappedLeft, mappedTop),
                size = Size(rectWidth, rectHeight),
                cornerRadius = CornerRadius(16f, 16f),
                style = Stroke(width = 8f)
            )
        }
    }
}