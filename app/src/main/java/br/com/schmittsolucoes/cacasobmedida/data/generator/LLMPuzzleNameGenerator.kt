package br.com.schmittsolucoes.cacasobmedida.data.generator

import br.com.schmittsolucoes.cacasobmedida.domain.generator.PuzzleNameGenerator
import br.com.schmittsolucoes.cacasobmedida.domain.model.enumeration.AITask
import br.com.schmittsolucoes.cacasobmedida.domain.model.result.puzzle.PuzzleResult
import br.com.schmittsolucoes.cacasobmedida.domain.repository.PromptRepository
import br.com.schmittsolucoes.cacasobmedida.domain.service.AIModelService
import javax.inject.Inject

class LLMPuzzleNameGenerator @Inject constructor(
    private val aiModelService: AIModelService,
    private val promptRepository: PromptRepository
): PuzzleNameGenerator {
    override suspend fun generate(results: List<PuzzleResult>): List<PuzzleResult> {
        try {
            if (!aiModelService.isReady()) {
                aiModelService.initialize()
            }

            if (!aiModelService.isReady()) return results

            return results.map { result ->
                val words = result.placedWords.joinToString(", ") { it.text }
                
                val prompt = promptRepository.getPrompt(
                    task = AITask.PUZZLE_NAME_GENERATION,
                    version = 1,
                    variables = mapOf("words" to words)
                )

                val nameResult = aiModelService.generate(prompt)
                val name = nameResult.getOrDefault(result.name)

                result.copy(name = name)
            }
        } finally {
            aiModelService.close()
        }
    }
}