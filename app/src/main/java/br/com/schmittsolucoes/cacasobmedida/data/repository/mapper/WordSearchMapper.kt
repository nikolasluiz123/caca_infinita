package br.com.schmittsolucoes.cacasobmedida.data.repository.mapper

import br.com.schmittsolucoes.cacasobmedida.data.model.PuzzleSessionEntity
import br.com.schmittsolucoes.cacasobmedida.data.model.WordEntity
import br.com.schmittsolucoes.cacasobmedida.data.model.WordSearchPuzzleEntity
import br.com.schmittsolucoes.cacasobmedida.data.model.WordSearchPuzzleWithStats
import br.com.schmittsolucoes.cacasobmedida.domain.model.PuzzleSession
import br.com.schmittsolucoes.cacasobmedida.domain.model.Word
import br.com.schmittsolucoes.cacasobmedida.domain.model.WordSearchPuzzle
import br.com.schmittsolucoes.cacasobmedida.domain.model.WordSearchPuzzleSummary
import br.com.schmittsolucoes.cacasobmedida.domain.model.result.puzzle.PlacedWord
import br.com.schmittsolucoes.cacasobmedida.domain.model.result.puzzle.PuzzleResult

fun PuzzleResult.toEntity(name: String, userId: String): WordSearchPuzzleEntity {
    return WordSearchPuzzleEntity(
        grid = grid.joinToString("") { it.concatToString() },
        name = name,
        rows = grid.size,
        columns = grid[0].size,
        userId = userId
    )
}

fun PlacedWord.toEntity(puzzleId: String): WordEntity {
    return WordEntity(
        puzzleId = puzzleId,
        text = text,
        startRow = startCoordinate.row,
        startCol = startCoordinate.col,
        direction = direction
    )
}

fun WordSearchPuzzleEntity.toDomain(): WordSearchPuzzle {
    return WordSearchPuzzle(
        id = id,
        grid = grid.chunked(columns).map { it.toList() },
        name = name,
        rows = rows,
        columns = columns
    )
}

fun WordSearchPuzzleWithStats.toDomain(): WordSearchPuzzleSummary {
    return WordSearchPuzzleSummary(
        id = puzzle.id,
        name = puzzle.name,
        wordsCount = wordsCount,
        hasUnfinishedWords = hasUnfinishedWords
    )
}

fun Word.toEntity(): WordEntity {
    return WordEntity(
        id = id,
        puzzleId = puzzleId,
        text = text,
        startRow = startRow,
        startCol = startCol,
        direction = direction,
        foundDate = foundDate
    )
}

fun WordEntity.toDomain(): Word {
    return Word(
        id = id,
        puzzleId = puzzleId,
        text = text,
        startRow = startRow,
        startCol = startCol,
        direction = direction,
        foundDate = foundDate
    )
}

fun PuzzleSession.toEntity(): PuzzleSessionEntity {
    return PuzzleSessionEntity(
        id = id,
        puzzleId = puzzleId,
        startedAt = startedAt,
        endedAt = endedAt
    )
}

fun PuzzleSessionEntity.toDomain(): PuzzleSession {
    return PuzzleSession(
        id = id,
        puzzleId = puzzleId,
        startedAt = startedAt,
        endedAt = endedAt
    )
}
