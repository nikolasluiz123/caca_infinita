package br.com.schmittsolucoes.cacainfinita.data.generator

import android.util.Log
import br.com.schmittsolucoes.cacainfinita.domain.generator.PuzzleGenerator
import br.com.schmittsolucoes.cacainfinita.domain.model.GridDimensions
import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.Direction
import br.com.schmittsolucoes.cacainfinita.domain.model.result.puzzle.Coordinate
import br.com.schmittsolucoes.cacainfinita.domain.model.result.puzzle.PlacedWord
import br.com.schmittsolucoes.cacainfinita.domain.model.result.puzzle.PuzzleResult
import javax.inject.Inject
import kotlin.random.Random

/**
 * Implementação de [PuzzleGenerator] que utiliza uma abordagem heurística baseada em pontuação
 * de sobreposição para gerar grades de caça-palavras com cruzamentos de letras.
 */
class HeuristicPuzzleGenerator @Inject constructor() : PuzzleGenerator {

    companion object {
        /**
         * Controla a densidade de palavras proporcionalmente ao tamanho da grade (Area / Divisor).
         * - Valor mais baixo: Aumenta a densidade, tentando encaixar mais palavras em menos espaço (mais difícil).
         * - Valor mais alto: Diminui a densidade, deixando a grade mais "espaçosa" e fácil.
         */
        private const val AREA_DIVISOR_FOR_WORD_COUNT = 10

        /**
         * Define a quantidade mínima de palavras que uma grade deve conter.
         * - Valor mais alto: Garante um desafio mínimo mesmo em grades pequenas, mas pode dificultar o encaixe.
         * - Valor mais baixo: Permite a geração de grades mais simples e rápidas de resolver.
         */
        private const val MIN_WORDS_PER_PUZZLE = 12

        /**
         * Define o teto máximo de palavras por grade para evitar poluição visual.
         * - Valor mais alto: Permite grades extremamente densas e complexas em dispositivos grandes.
         * - Valor mais baixo: Mantém o jogo mais leve e evita que a busca exaustiva demore demais.
         */
        private const val MAX_WORDS_PER_PUZZLE = 20
    }

    /**
     * Gera uma lista de resultados de quebra-cabeça ([PuzzleResult]) a partir de uma lista de palavras.
     * O processo tenta agrupar o máximo de palavras possível em cada grade, respeitando a densidade alvo.
     *
     * @param words Lista de palavras a serem inseridas nos caça-palavras.
     * @param dimensions Dimensões da grade (linhas e colunas).
     * @return Lista de grades geradas com suas respectivas palavras posicionadas.
     */
    override suspend fun generate(words: List<String>, dimensions: GridDimensions): List<PuzzleResult> {
        val tag = this@HeuristicPuzzleGenerator::class.simpleName

        Log.d("DEBUG_PROCESS", "$tag: Iniciando geração de grade(s) heurística")
        
        val results = mutableListOf<PuzzleResult>()
        val allWords = words.shuffled().toMutableList()

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
                results.add(PuzzleResult(grid, placedWords))
                Log.d("DEBUG_PROCESS", "$tag: Novo PuzzleResult gerado com ${placedWords.size} palavras")
            } else {
                break
            }
        }

        return results
    }

    /**
     * Tenta posicionar uma palavra na grade buscando a melhor localização baseada em dificuldade e sobreposição.
     *
     * O processo utiliza três loops aninhados (linhas, colunas e direções) para realizar uma busca exaustiva.
     * O objetivo dessa abordagem é "estressar" o algoritmo, garantindo que ele avalie literalmente todas
     * as possibilidades de encaixe antes de tomar uma decisão, maximizando a densidade e o desafio.
     *
     * Durante a iteração:
     * 1. [calculateOverlapScore] atua como um validador: ele verifica se a palavra cabe na posição e
     *    quantas letras ela compartilha com palavras já posicionadas.
     * 2. [calculateDifficultyScore] transforma essa validação em um ranking, atribuindo pesos à
     *    complexidade da direção e à qualidade dos cruzamentos encontrados.
     *
     * Após mapear todas as possibilidades:
     * - maxScore: Representa o nível máximo de qualidade/dificuldade encontrado em toda a grade.
     * - bestCandidates: Uma lista contendo todas as posições que atingiram esse `maxScore`. Isso é
     *   fundamental para não ignorar empates técnicos entre diferentes localizações ideais.
     * - finalCandidate: Uma escolha aleatória entre os melhores candidatos, o que introduz
     *   variabilidade natural ao gerador, evitando que ele sempre escolha o mesmo canto da grade.
     *
     * @param word A palavra a ser posicionada.
     * @param grid A matriz de caracteres atual.
     * @param dimensions As dimensões da grade.
     * @param placedWords A lista de palavras já posicionadas para registro.
     * @return Verdadeiro se a palavra foi posicionada com sucesso, falso caso contrário.
     */
    private fun tryPlaceWord(
        word: String,
        grid: Array<CharArray>,
        dimensions: GridDimensions,
        placedWords: MutableList<PlacedWord>
    ): Boolean {
        val directions = Direction.entries
        val candidates = mutableListOf<PlacementCandidate>()

        for (row in 0 until dimensions.rows) {
            for (col in 0 until dimensions.columns) {
                for (direction in directions) {
                    val overlapScore = calculateOverlapScore(word, row, col, direction, grid, dimensions)

                    if (overlapScore >= 0) {
                        val difficultyScore = calculateDifficultyScore(overlapScore, direction)
                        candidates.add(PlacementCandidate(row, col, direction, difficultyScore))
                    }
                }
            }
        }

        if (candidates.isEmpty()) return false

        val maxScore = candidates.maxOf { it.score }
        val bestCandidates = candidates.filter { it.score == maxScore }
        val finalCandidate = bestCandidates.random()

        placeWord(word, finalCandidate.row, finalCandidate.col, finalCandidate.direction, grid)
        
        placedWords.add(
            PlacedWord(
                text = word,
                startCoordinate = Coordinate(finalCandidate.row, finalCandidate.col),
                direction = finalCandidate.direction
            )
        )
        return true
    }

    /**
     * Calcula uma pontuação de dificuldade para uma possível posição da palavra.
     *
     * O `overlapWeight` inicia com um valor alto (100) para garantir que a intersecção de letras seja
     * o fator determinante. No design de caça-palavras, palavras cruzadas são o que mais dificulta a
     * percepção visual do jogador, portanto, uma posição com cruzamento deve quase sempre vencer
     * uma posição "limpa", mesmo que a direção desta seja complexa.
     *
     * As pontuações por [Direction] foram escolhidas baseadas no padrão de leitura e ergonomia mobile:
     * - [Direction.HORIZONTAL] (10) e [Direction.VERTICAL] (5): São os eixos naturais. O vertical é o
     *   mais penalizado por ser extremamente fácil de identificar ao fazer o scroll de tela no celular.
     * - [Direction.HORIZONTAL_REVERSE] (25) e [Direction.VERTICAL_REVERSE] (20): Invertem o fluxo de
     *   leitura, exigindo mais esforço cognitivo.
     * - [Direction.DIAGONAL_DOWN_RIGHT] e [Direction.DIAGONAL_UP_RIGHT] (35): Quebram o padrão de
     *   varredura horizontal/vertical.
     * - [Direction.DIAGONAL_DOWN_LEFT] e [Direction.DIAGONAL_UP_LEFT] (40): As mais difíceis, pois
     *   combinam a diagonal com o movimento de leitura reverso (da direita para a esquerda).
     */
    private fun calculateDifficultyScore(overlapScore: Int, direction: Direction): Int {
        val overlapWeight = 100
        var score = overlapScore * overlapWeight

        score += when (direction) {
            Direction.HORIZONTAL -> 10
            Direction.VERTICAL -> 5
            Direction.HORIZONTAL_REVERSE -> 25
            Direction.VERTICAL_REVERSE -> 20
            Direction.DIAGONAL_DOWN_RIGHT, Direction.DIAGONAL_UP_RIGHT -> 35
            Direction.DIAGONAL_DOWN_LEFT, Direction.DIAGONAL_UP_LEFT -> 40 
        }

        return score
    }

    /**
     * Calcula a pontuação de sobreposição para uma palavra em uma posição e direção específica.
     * A pontuação é incrementada para cada letra que coincide com uma letra já presente na grade.
     *
     * @return A quantidade de sobreposições válidas, ou -1 se houver conflito de caracteres ou extrapolar limites.
     */
    private fun calculateOverlapScore(
        word: String,
        row: Int,
        col: Int,
        direction: Direction,
        grid: Array<CharArray>,
        dimensions: GridDimensions
    ): Int {
        val endRow = row + (word.length - 1) * direction.rowStep
        val endCol = col + (word.length - 1) * direction.colStep

        if (endRow !in 0 until dimensions.rows || endCol !in 0 until dimensions.columns) {
            return -1
        }

        var overlaps = 0
        
        for (i in word.indices) {
            val r = row + i * direction.rowStep
            val c = col + i * direction.colStep
            val existingChar = grid[r][c]

            if (existingChar != ' ') {
                if (existingChar == word[i]) {
                    overlaps++
                } else {
                    return -1
                }
            }
        }
        return overlaps
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

    private data class PlacementCandidate(
        val row: Int,
        val col: Int,
        val direction: Direction,
        val score: Int
    )
}
