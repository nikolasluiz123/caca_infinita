package br.com.schmittsolucoes.cacasobmedida.presentation.puzzle.composables.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import br.com.schmittsolucoes.cacasobmedida.domain.model.WordSearchPuzzle

@Composable
fun PuzzleGrid(
    puzzle: WordSearchPuzzle,
    modifier: Modifier = Modifier
) {
    val textMeasurer = rememberTextMeasurer()
    val baseStyle = MaterialTheme.typography.bodyLarge.copy(
        color = MaterialTheme.colorScheme.onSurface
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val cellWidth = canvasWidth / puzzle.columns
        val cellHeight = canvasHeight / puzzle.rows

        val cellSize = minOf(cellWidth, cellHeight)
        
        val fontSize = (cellSize * 0.6f).toSp()
        val textStyle = baseStyle.copy(fontSize = fontSize)

        val sampleTextLayout = textMeasurer.measure("W", textStyle)
        val textWidth = sampleTextLayout.size.width
        val textHeight = sampleTextLayout.size.height

        val offsetX = (canvasWidth - (cellSize * puzzle.columns)) / 2
        val offsetY = (canvasHeight - (cellSize * puzzle.rows)) / 2

        for (row in 0 until puzzle.rows) {
            for (col in 0 until puzzle.columns) {
                val char = puzzle.grid[row][col].toString()

                val x = offsetX + col * cellSize + (cellSize - textWidth) / 2
                val y = offsetY + row * cellSize + (cellSize - textHeight) / 2

                drawText(
                    textMeasurer = textMeasurer,
                    text = char,
                    style = textStyle,
                    topLeft = Offset(x, y)
                )
            }
        }
    }
}
