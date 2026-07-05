package br.com.schmittsolucoes.cacainfinita.domain.usecase

import br.com.schmittsolucoes.cacainfinita.domain.model.Word
import br.com.schmittsolucoes.cacainfinita.domain.repository.PuzzleSessionRepository
import br.com.schmittsolucoes.cacainfinita.domain.repository.WordRepository
import java.time.Duration
import java.time.Instant
import kotlin.math.max

/**
 * Caso de uso responsável por calcular a quantidade de experiência (XP) que um usuário recebe
 * ao encontrar uma palavra específica em um caça-palavras.
 *
 * O cálculo é baseado em três pilares:
 * 1. **Tamanho da palavra:** Recompensa o esforço de encontrar palavras mais longas.
 * 2. **Complexidade do tabuleiro:** Recompensa proporcionalmente à quantidade total de palavras no desafio.
 * 3. **Bônus de agilidade:** Recompensa a velocidade entre encontrar uma palavra e outra (ou o início do jogo).
 */
class GetUserExperienceByFoundWordUseCase(
    private val wordRepository: WordRepository,
    private val puzzleSessionRepository: PuzzleSessionRepository
) {
    companion object {
        /** Valor de XP concedido para cada caractere da palavra encontrada. */
        private const val XP_PER_CHARACTER = 10L

        /** Valor de XP concedido para cada palavra existente no tabuleiro (fator de complexidade). */
        private const val XP_PER_TOTAL_WORD = 5L

        /** Tempo limite em segundos para receber bônus de agilidade. Se exceder, o bônus é zero. */
        private const val MAX_BONUS_TIME_SECONDS = 100L
    }

    /**
     * Calcula a experiência total para a palavra informada.
     *
     * @param word A palavra encontrada.
     * @return O total de XP calculado.
     */
    suspend operator fun invoke(word: Word): Long {
        val totalWordsInPuzzle = wordRepository.getCountWords(word.puzzleId)
        val allWords = wordRepository.getAllWordsBy(word.puzzleId)
        
        val actualSession = puzzleSessionRepository.getActualSessionBy(word.puzzleId)
        
        val startTime = getStartTime(word, allWords, actualSession?.startedAt)
        val foundTime = word.foundDate ?: Instant.now()
        
        val secondsTaken = Duration.between(startTime, foundTime).seconds
        
        val timeBonus = max(0L, MAX_BONUS_TIME_SECONDS - secondsTaken)
        
        val wordLengthXP = word.text.length * XP_PER_CHARACTER
        val puzzleComplexityXP = totalWordsInPuzzle * XP_PER_TOTAL_WORD
        
        return wordLengthXP + puzzleComplexityXP + timeBonus
    }

    /**
     * Determina o ponto de referência inicial para o cálculo de tempo.
     * Se houver palavras encontradas anteriormente, usa o horário da última encontrada.
     * Caso seja a primeira palavra, usa o horário de início da sessão.
     */
    private fun getStartTime(word: Word, allWords: List<Word>, sessionStartedAt: Instant?): Instant {
        val otherFoundWords = allWords
            .filter { it.id != word.id && it.foundDate != null }
            .sortedByDescending { it.foundDate }
            
        val lastFoundWord = otherFoundWords.firstOrNull()
        
        return lastFoundWord?.foundDate ?: sessionStartedAt ?: Instant.now()
    }
}
