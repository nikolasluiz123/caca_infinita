package br.com.schmittsolucoes.cacainfinita.data.repository.mapper

import br.com.schmittsolucoes.cacainfinita.data.model.PuzzleSessionEntity
import br.com.schmittsolucoes.cacainfinita.data.model.WordEntity
import br.com.schmittsolucoes.cacainfinita.data.model.WordSearchPuzzleEntity
import br.com.schmittsolucoes.cacainfinita.data.model.WordSearchPuzzleWithStats
import br.com.schmittsolucoes.cacainfinita.domain.model.PuzzleSession
import br.com.schmittsolucoes.cacainfinita.domain.model.Word
import br.com.schmittsolucoes.cacainfinita.domain.model.WordSearchPuzzle
import br.com.schmittsolucoes.cacainfinita.domain.model.WordSearchPuzzleSummary
import br.com.schmittsolucoes.cacainfinita.domain.model.result.puzzle.PlacedWord
import br.com.schmittsolucoes.cacainfinita.domain.model.result.puzzle.PuzzleResult

fun PuzzleResult.toEntity(name: String, userId: String): WordSearchPuzzleEntity {
    return WordSearchPuzzleEntity(
        grid = grid.joinToString("") { it.concatToString() },
        name = name,
        rows = grid.size,
        columns = grid[0].size,
        orientation = orientation,
        userId = userId
    )
}

fun PlacedWord.toEntity(puzzleId: String): WordEntity {
    return WordEntity(
        puzzleId = puzzleId,
        text = text,
        startRow = startCoordinate.row,
        startCol = startCoordinate.col,
        direction = direction,
        language = language
    )
}

fun WordSearchPuzzleEntity.toDomain(): WordSearchPuzzle {
    return WordSearchPuzzle(
        id = id,
        grid = grid.chunked(columns).map { it.toList() },
        name = name,
        rows = rows,
        columns = columns,
        orientation = orientation
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
        language = language,
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
        language = language,
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
