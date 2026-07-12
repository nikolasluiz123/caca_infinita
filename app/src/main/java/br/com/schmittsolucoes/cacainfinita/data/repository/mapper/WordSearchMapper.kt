package br.com.schmittsolucoes.cacainfinita.data.repository.mapper

import br.com.schmittsolucoes.cacainfinita.data.model.PuzzleSessionEntity
import br.com.schmittsolucoes.cacainfinita.data.model.WordEntity
import br.com.schmittsolucoes.cacainfinita.data.model.WordSearchPuzzleEntity
import br.com.schmittsolucoes.cacainfinita.data.model.WordSearchPuzzleWithStats
import br.com.schmittsolucoes.cacainfinita.data.model.dto.FullPuzzleDTO
import br.com.schmittsolucoes.cacainfinita.data.model.dto.PuzzleSessionDTO
import br.com.schmittsolucoes.cacainfinita.data.model.dto.WordDTO
import br.com.schmittsolucoes.cacainfinita.data.model.dto.WordSearchPuzzleDTO
import br.com.schmittsolucoes.cacainfinita.domain.model.FullPuzzle
import br.com.schmittsolucoes.cacainfinita.domain.model.PuzzleSession
import br.com.schmittsolucoes.cacainfinita.domain.model.Word
import br.com.schmittsolucoes.cacainfinita.domain.model.WordSearchPuzzle
import br.com.schmittsolucoes.cacainfinita.domain.model.WordSearchPuzzleSummary
import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.Language
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

fun WordSearchPuzzle.toEntity(userId: String): WordSearchPuzzleEntity {
    return WordSearchPuzzleEntity(
        id = id,
        grid = grid.joinToString("") { it.joinToString("") },
        name = name,
        rows = rows,
        columns = columns,
        orientation = orientation,
        userId = userId
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

fun WordSearchPuzzle.toDTO(userId: String) = WordSearchPuzzleDTO(
    id = id,
    grid = grid.joinToString("") { it.joinToString("") },
    name = name,
    rows = rows,
    columns = columns,
    orientation = orientation,
    userId = userId
)

fun WordSearchPuzzleDTO.toDomain() = WordSearchPuzzle(
    id = id,
    grid = grid.chunked(columns).map { it.toList() },
    name = name,
    rows = rows,
    columns = columns,
    orientation = orientation
)

fun WordSearchPuzzleEntity.toDTO() = WordSearchPuzzleDTO(
    id = id,
    grid = grid,
    name = name,
    rows = rows,
    columns = columns,
    orientation = orientation,
    userId = userId
)

fun WordSearchPuzzleDTO.toEntity() = WordSearchPuzzleEntity(
    id = id,
    grid = grid,
    name = name,
    rows = rows,
    columns = columns,
    orientation = orientation,
    userId = userId
)

fun WordSearchPuzzleWithStats.toDomain(): WordSearchPuzzleSummary {
    return WordSearchPuzzleSummary(
        id = puzzle.id,
        name = puzzle.name,
        wordsCount = wordsCount,
        hasUnfinishedWords = hasUnfinishedWords,
        languages = languages.split(",").map { Language.valueOf(it) },
        orientation = puzzle.orientation
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

fun Word.toDTO() = WordDTO(
    id = id,
    puzzleId = puzzleId,
    text = text,
    startRow = startRow,
    startCol = startCol,
    direction = direction,
    language = language,
    foundDate = foundDate
)

fun WordDTO.toDomain() = Word(
    id = id,
    puzzleId = puzzleId,
    text = text,
    startRow = startRow,
    startCol = startCol,
    direction = direction,
    language = language,
    foundDate = foundDate
)

fun WordEntity.toDTO() = WordDTO(
    id = id,
    puzzleId = puzzleId,
    text = text,
    startRow = startRow,
    startCol = startCol,
    direction = direction,
    language = language,
    foundDate = foundDate
)

fun WordDTO.toEntity() = WordEntity(
    id = id,
    puzzleId = puzzleId,
    text = text,
    startRow = startRow,
    startCol = startCol,
    direction = direction,
    language = language,
    foundDate = foundDate
)

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

fun PuzzleSession.toDTO() = PuzzleSessionDTO(
    id = id,
    puzzleId = puzzleId,
    startedAt = startedAt,
    endedAt = endedAt
)

fun PuzzleSessionDTO.toDomain() = PuzzleSession(
    id = id,
    puzzleId = puzzleId,
    startedAt = startedAt,
    endedAt = endedAt
)

fun PuzzleSessionEntity.toDTO() = PuzzleSessionDTO(
    id = id,
    puzzleId = puzzleId,
    startedAt = startedAt,
    endedAt = endedAt
)

fun PuzzleSessionDTO.toEntity() = PuzzleSessionEntity(
    id = id,
    puzzleId = puzzleId,
    startedAt = startedAt,
    endedAt = endedAt
)

fun FullPuzzle.toDTO(userId: String) = FullPuzzleDTO(
    puzzle = puzzle.toDTO(userId),
    words = words.map { it.toDTO() },
    sessions = sessions.map { it.toDTO() }
)

fun FullPuzzleDTO.toDomain() = FullPuzzle(
    puzzle = puzzle.toDomain(),
    words = words.map { it.toDomain() },
    sessions = sessions.map { it.toDomain() }
)
