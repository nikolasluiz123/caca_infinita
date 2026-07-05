package br.com.schmittsolucoes.cacainfinita.presentation.puzzle.composables.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.IntSize
import br.com.schmittsolucoes.cacainfinita.domain.model.Word
import br.com.schmittsolucoes.cacainfinita.domain.model.WordSearchPuzzle
import br.com.schmittsolucoes.cacainfinita.domain.model.result.puzzle.Coordinate
import br.com.schmittsolucoes.cacainfinita.presentation.theme.WordSelectionColor
import kotlinx.coroutines.launch

@Composable
fun PuzzleGrid(
    puzzle: WordSearchPuzzle,
    foundWords: List<Word>,
    onWordSelected: (Coordinate, Coordinate) -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    var selectionStart by remember { mutableStateOf<Offset?>(null) }
    var selectionEnd by remember { mutableStateOf<Offset?>(null) }
    val animatedSelectionEnd = remember { Animatable(Offset.Zero, Offset.VectorConverter) }

    val textMeasurer = rememberTextMeasurer()
    val baseStyle = MaterialTheme.typography.bodyLarge.copy(
        color = MaterialTheme.colorScheme.onSurface
    )
    val highlightColor = WordSelectionColor
    val density = LocalDensity.current

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val canvasWidth = constraints.maxWidth.toFloat()
        val canvasHeight = constraints.maxHeight.toFloat()
        
        val cellSize = calculateCellSize(canvasWidth, canvasHeight, puzzle)
        val offsetX = calculateHorizontalOffset(canvasWidth, cellSize, puzzle.columns)
        val offsetY = calculateVerticalOffset(canvasHeight, cellSize, puzzle.rows)

        val textLayouts = remember(puzzle, cellSize, baseStyle) {
            val fontSize = with(density) { (cellSize * 0.6f).toSp() }
            val style = baseStyle.copy(fontSize = fontSize)
            puzzle.grid.map { row ->
                row.map { char ->
                    textMeasurer.measure(char.toString(), style)
                }
            }
        }

        val sharedCoordinates = remember(foundWords) {
            val counts = mutableMapOf<Coordinate, Int>()
            foundWords.forEach { word ->
                for (i in word.text.indices) {
                    val coord = Coordinate(
                        word.startRow + i * word.direction.rowStep,
                        word.startCol + i * word.direction.colStep
                    )
                    counts[coord] = (counts[coord] ?: 0) + 1
                }
            }
            counts.filter { it.value > 1 }.keys
        }

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(puzzle) {
                    detectDragGestures(
                        onDragStart = { offset ->
                            selectionStart = offset
                            selectionEnd = offset
                            coroutineScope.launch {
                                animatedSelectionEnd.snapTo(offset)
                            }
                        },
                        onDrag = { change, dragAmount ->
                            change.consume()
                            val newEnd = (selectionEnd ?: change.position) + dragAmount
                            selectionEnd = newEnd
                            coroutineScope.launch {
                                animatedSelectionEnd.animateTo(
                                    targetValue = newEnd,
                                    animationSpec = spring(stiffness = 800f, dampingRatio = 0.7f)
                                )
                            }
                        },
                        onDragEnd = {
                            processFinalSelection(
                                size = size,
                                selectionStart = selectionStart,
                                selectionEnd = selectionEnd,
                                puzzle = puzzle,
                                onWordSelected = onWordSelected
                            )
                            selectionStart = null
                            selectionEnd = null
                        },
                        onDragCancel = {
                            selectionStart = null
                            selectionEnd = null
                        }
                    )
                }
        ) {
            drawFoundWordsHighlights(
                foundWords = foundWords,
                cellSize = cellSize,
                offsetX = offsetX,
                offsetY = offsetY,
                color = highlightColor,
                sharedCoordinates = sharedCoordinates
            )

            drawCurrentSelectionHighlight(
                selectionStart = selectionStart,
                animatedSelectionEnd = animatedSelectionEnd.value,
                cellSize = cellSize,
                offsetX = offsetX,
                offsetY = offsetY,
                puzzle = puzzle,
                color = highlightColor
            )

            drawGridCharacters(
                textLayouts = textLayouts,
                cellSize = cellSize,
                offsetX = offsetX,
                offsetY = offsetY
            )
        }
    }
}

private fun processFinalSelection(
    size: IntSize,
    selectionStart: Offset?,
    selectionEnd: Offset?,
    puzzle: WordSearchPuzzle,
    onWordSelected: (Coordinate, Coordinate) -> Unit
) {
    if (selectionStart == null || selectionEnd == null) return

    val cellSize = calculateCellSize(size.width.toFloat(), size.height.toFloat(), puzzle)
    val offsetX = calculateHorizontalOffset(size.width.toFloat(), cellSize, puzzle.columns)
    val offsetY = calculateVerticalOffset(size.height.toFloat(), cellSize, puzzle.rows)

    val startCoord = getCoordinateFromOffset(selectionStart, cellSize, offsetX, offsetY, puzzle)
    val endCoord = getCoordinateFromOffset(selectionEnd, cellSize, offsetX, offsetY, puzzle)

    if (startCoord != null && endCoord != null) {
        onWordSelected(startCoord, endCoord)
    }
}

private fun DrawScope.drawFoundWordsHighlights(
    foundWords: List<Word>,
    cellSize: Float,
    offsetX: Float,
    offsetY: Float,
    color: Color,
    sharedCoordinates: Set<Coordinate>
) {
    foundWords.forEach { word ->
        val startCenter = getCellCenter(Coordinate(word.startRow, word.startCol), cellSize, offsetX, offsetY)
        val endCoord = Coordinate(
            word.startRow + (word.text.length - 1) * word.direction.rowStep,
            word.startCol + (word.text.length - 1) * word.direction.colStep
        )
        val endCenter = getCellCenter(endCoord, cellSize, offsetX, offsetY)

        drawHighlightLine(startCenter, endCenter, cellSize, color, alpha = 0.6f)
    }

    sharedCoordinates.forEach { coord ->
        val center = getCellCenter(coord, cellSize, offsetX, offsetY)
        drawCircle(
            color = color,
            radius = cellSize * 0.4f,
            center = center,
            alpha = 0.3f
        )
    }
}

private fun DrawScope.drawCurrentSelectionHighlight(
    selectionStart: Offset?,
    animatedSelectionEnd: Offset,
    cellSize: Float,
    offsetX: Float,
    offsetY: Float,
    puzzle: WordSearchPuzzle,
    color: Color
) {
    if (selectionStart == null) return

    val startCoord = getCoordinateFromOffset(selectionStart, cellSize, offsetX, offsetY, puzzle)
    if (startCoord != null) {
        val startCenter = getCellCenter(startCoord, cellSize, offsetX, offsetY)
        drawHighlightLine(startCenter, animatedSelectionEnd, cellSize, color)
    }
}

private fun DrawScope.drawHighlightLine(
    start: Offset,
    end: Offset,
    cellSize: Float,
    color: Color,
    alpha: Float = 1f
) {
    drawLine(
        color = color,
        start = start,
        end = end,
        strokeWidth = cellSize * 0.8f,
        cap = StrokeCap.Round,
        alpha = alpha
    )
}

private fun DrawScope.drawGridCharacters(
    textLayouts: List<List<TextLayoutResult>>,
    cellSize: Float,
    offsetX: Float,
    offsetY: Float
) {
    textLayouts.forEachIndexed { row, layouts ->
        layouts.forEachIndexed { col, textLayoutResult ->
            val charWidth = textLayoutResult.size.width
            val charHeight = textLayoutResult.size.height
            
            val x = offsetX + col * cellSize + (cellSize - charWidth) / 2
            val y = offsetY + row * cellSize + (cellSize - charHeight) / 2

            drawText(
                textLayoutResult = textLayoutResult,
                topLeft = Offset(x, y)
            )
        }
    }
}

private fun calculateCellSize(width: Float, height: Float, puzzle: WordSearchPuzzle): Float {
    return minOf(width / puzzle.columns, height / puzzle.rows)
}

private fun calculateHorizontalOffset(canvasWidth: Float, cellSize: Float, columns: Int): Float {
    return (canvasWidth - (cellSize * columns)) / 2
}

private fun calculateVerticalOffset(canvasHeight: Float, cellSize: Float, rows: Int): Float {
    return (canvasHeight - (cellSize * rows)) / 2
}

private fun getCoordinateFromOffset(
    offset: Offset,
    cellSize: Float,
    offsetX: Float,
    offsetY: Float,
    puzzle: WordSearchPuzzle
): Coordinate? {
    val col = ((offset.x - offsetX) / cellSize).toInt()
    val row = ((offset.y - offsetY) / cellSize).toInt()
    if (row in 0 until puzzle.rows && col in 0 until puzzle.columns) {
        return Coordinate(row, col)
    }
    return null
}

private fun getCellCenter(
    coord: Coordinate,
    cellSize: Float,
    offsetX: Float,
    offsetY: Float
): Offset {
    return Offset(
        x = offsetX + coord.col * cellSize + cellSize / 2,
        y = offsetY + coord.row * cellSize + cellSize / 2
    )
}
