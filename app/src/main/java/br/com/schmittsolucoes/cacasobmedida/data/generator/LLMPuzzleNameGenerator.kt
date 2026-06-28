package br.com.schmittsolucoes.cacasobmedida.data.generator

import android.util.Log
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
        val tag = this@LLMPuzzleNameGenerator::class.simpleName

        Log.d("DEBUG_PROCESS", "$tag: Iniciando geração de nomes para os quebra-cabeças com LLM")
        
        try {
            if (!aiModelService.isReady()) {
                aiModelService.initialize()
            }

            if (!aiModelService.isReady()) {
                Log.d("DEBUG_PROCESS", "$tag: IA não está pronta, ignorando geração de nomes")
                return results
            }

            return results.map { result ->
                val words = result.placedWords.joinToString(", ") { it.text }
                
                Log.d("DEBUG_PROCESS", "$tag: Enviando palavras para gerar nome: $words")

                val prompt = promptRepository.getPrompt(
                    task = AITask.PUZZLE_NAME_GENERATION,
                    version = 1,
                    variables = mapOf("words" to words)
                )

                val nameResult = aiModelService.generate(prompt)
                val name = nameResult.getOrDefault(result.name)

                Log.d("DEBUG_PROCESS", "$tag: Nome gerado pela LLM: $name")

                result.copy(name = name)
            }
        } finally {
            aiModelService.close()
            Log.d("DEBUG_PROCESS", "$tag: Fim da geração de nomes com LLM")
        }
    }
}