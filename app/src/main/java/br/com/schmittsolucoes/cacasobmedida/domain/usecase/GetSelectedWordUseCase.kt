package br.com.schmittsolucoes.cacasobmedida.domain.usecase

import br.com.schmittsolucoes.cacasobmedida.domain.model.Word
import br.com.schmittsolucoes.cacasobmedida.domain.model.result.puzzle.Coordinate

class GetSelectedWordUseCase {

    operator fun invoke(words: List<Word>, start: Coordinate, end: Coordinate): Word? {
        return words.find { word ->
            val wordEndRow = word.startRow + (word.text.length - 1) * word.direction.rowStep
            val wordEndCol = word.startCol + (word.text.length - 1) * word.direction.colStep

            (word.startRow == start.row && word.startCol == start.col && wordEndRow == end.row && wordEndCol == end.col) ||
            (word.startRow == end.row && word.startCol == end.col && wordEndRow == start.row && wordEndCol == start.col)
        }
    }
}
