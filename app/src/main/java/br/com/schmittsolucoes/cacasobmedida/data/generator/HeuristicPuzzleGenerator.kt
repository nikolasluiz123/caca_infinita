package br.com.schmittsolucoes.cacasobmedida.data.generator

import android.content.Context
import android.util.Log
import br.com.schmittsolucoes.cacasobmedida.R
import br.com.schmittsolucoes.cacasobmedida.domain.generator.PuzzleGenerator
import br.com.schmittsolucoes.cacasobmedida.domain.model.GridDimensions
import br.com.schmittsolucoes.cacasobmedida.domain.model.enumeration.Direction
import br.com.schmittsolucoes.cacasobmedida.domain.model.result.puzzle.Coordinate
import br.com.schmittsolucoes.cacasobmedida.domain.model.result.puzzle.PlacedWord
import br.com.schmittsolucoes.cacasobmedida.domain.model.result.puzzle.PuzzleResult
import br.com.schmittsolucoes.cacasobmedida.domain.repository.WordSearchPuzzleRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.math.min
import kotlin.random.Random

/**
 * Gerador de quebra-cabeças que utiliza uma heurística gulosa (greedy) para posicionar palavras
 * em múltiplas grades, se necessário.
 *
 * A estratégia consiste em:
 * 1. Ordenar as palavras por tamanho (decrescente) para priorizar o encaixe das mais difíceis.
 * 2. Dividir as palavras em múltiplos [PuzzleResult] baseando-se na capacidade da grade.
 * 3. Tentar posicionar cada palavra em locais e direções aleatórias com detecção de colisão.
 * 4. Preencher espaços vazios com letras aleatórias.
 */
class HeuristicPuzzleGenerator @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val wordSearchPuzzleRepository: WordSearchPuzzleRepository,
) : PuzzleGenerator {

    companion object {
        private const val AREA_DIVISOR_FOR_WORD_COUNT = 12
        private const val MIN_WORDS_PER_PUZZLE = 8
        private const val MAX_WORDS_PER_PUZZLE = 20
        private const val MAX_PLACEMENT_ATTEMPTS_NORMAL = 100
        private const val MAX_PLACEMENT_ATTEMPTS_LONG_WORD = 200
    }

    override suspend fun generate(words: List<String>, dimensions: GridDimensions): List<PuzzleResult> {
        val tag = this@HeuristicPuzzleGenerator::class.simpleName

        Log.d("DEBUG_PROCESS", "$tag: Iniciando geração de grade(s) heurística")
        
        val results = mutableListOf<PuzzleResult>()
        val allWords = prepareAndSortWords(words)

        val totalArea = dimensions.rows * dimensions.columns
        val targetWordsPerPuzzle = (totalArea / AREA_DIVISOR_FOR_WORD_COUNT)
            .coerceIn(MIN_WORDS_PER_PUZZLE, MAX_WORDS_PER_PUZZLE)

        while (allWords.isNotEmpty()) {
            val grid = Array(dimensions.rows) { CharArray(dimensions.columns) { ' ' } }
            val placedWords = mutableListOf<PlacedWord>()
            val wordsToRemove = mutableListOf<String>()

            for (word in allWords) {
                if (placedWords.size >= targetWordsPerPuzzle) break

                val wasPlacedSuccessfully = tryPlaceWord(word, grid, dimensions, placedWords)

                if (wasPlacedSuccessfully) {
                    wordsToRemove.add(word)
                }
            }

            allWords.removeAll(wordsToRemove)

            if (placedWords.isNotEmpty()) {
                fillEmptySpaces(grid)

                val result = PuzzleResult(
                    grid = grid,
                    placedWords = placedWords
                )
                
                Log.d("DEBUG_PROCESS", "$tag: Novo PuzzleResult gerado: Palavras (${placedWords.size}): ${placedWords.joinToString { it.text }}")
                results.add(result)
            } else {
                break
            }
        }

        return results
    }

    /**
     * Limpa, normaliza e ordena as palavras por tamanho decrescente.
     * Palavras maiores são mais difíceis de encaixar, por isso devem ser processadas primeiro.
     */
    private fun prepareAndSortWords(words: List<String>): MutableList<String> {
        return words
            .filter { it.isNotBlank() }
            .map { it.uppercase().trim() }
            .sortedByDescending { it.length }
            .toMutableList()
    }

    /**
     * Tenta encontrar uma posição e direção válida para a palavra no grid.
     * Se a palavra for considerada longa (quase o tamanho da menor dimensão da grade),
     * o número de tentativas é dobrado para aumentar as chances de sucesso.
     */
    private fun tryPlaceWord(
        word: String,
        grid: Array<CharArray>,
        dimensions: GridDimensions,
        placedWords: MutableList<PlacedWord>
    ): Boolean {
        val directions = Direction.entries.shuffled()
        val isLongWord = word.length >= min(dimensions.rows, dimensions.columns)
        val maxAttempts = if (isLongWord) MAX_PLACEMENT_ATTEMPTS_LONG_WORD else MAX_PLACEMENT_ATTEMPTS_NORMAL

        for (i in 0 until maxAttempts) {
            val startRow = Random.nextInt(dimensions.rows)
            val startCol = Random.nextInt(dimensions.columns)

            for (direction in directions) {
                val canBePlaced = canPlaceWord(word, startRow, startCol, direction, grid, dimensions)

                if (canBePlaced) {
                    placeWord(word, startRow, startCol, direction, grid)

                    placedWords.add(
                        PlacedWord(
                            text = word,
                            startCoordinate = Coordinate(startRow, startCol),
                            direction = direction
                        )
                    )

                    return true
                }
            }
        }
        return false
    }

    /**
     * Verifica se a palavra cabe nos limites da grade a partir da posição inicial e na direção dada,
     * e se não há colisões com letras de outras palavras já posicionadas (exceto se a letra for idêntica).
     */
    private fun canPlaceWord(
        word: String,
        row: Int,
        col: Int,
        direction: Direction,
        grid: Array<CharArray>,
        dimensions: GridDimensions
    ): Boolean {
        val endRow = row + (word.length - 1) * direction.rowStep
        val endCol = col + (word.length - 1) * direction.colStep

        val isOutOfBounds = endRow !in 0 until dimensions.rows || endCol !in 0 until dimensions.columns
        if (isOutOfBounds) {
            return false
        }

        for (i in word.indices) {
            val r = row + i * direction.rowStep
            val c = col + i * direction.colStep
            val existingChar = grid[r][c]

            val isConflict = existingChar != ' ' && existingChar != word[i]

            if (isConflict) {
                return false
            }
        }

        return true
    }

    /**
     * Insere os caracteres da palavra na matriz da grade seguindo a direção especificada.
     */
    private fun placeWord(
        word: String,
        row: Int,
        col: Int,
        direction: Direction,
        grid: Array<CharArray>
    ) {
        for (i in word.indices) {
            val r = row + i * direction.rowStep
            val c = col + i * direction.colStep
            grid[r][c] = word[i]
        }
    }

    /**
     * Preenche as células vazias da matriz (marcadas com espaço) com letras aleatórias do alfabeto.
     */
    private fun fillEmptySpaces(grid: Array<CharArray>) {
        val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        for (r in grid.indices) {
            for (c in grid[r].indices) {
                if (grid[r][c] == ' ') {
                    grid[r][c] = alphabet[Random.nextInt(alphabet.length)]
                }
            }
        }
    }
}
