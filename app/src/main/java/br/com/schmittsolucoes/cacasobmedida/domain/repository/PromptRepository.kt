package br.com.schmittsolucoes.cacasobmedida.domain.repository

import br.com.schmittsolucoes.cacasobmedida.domain.model.enumeration.AITask

interface PromptRepository {
    suspend fun getPrompt(task: AITask, version: Int, variables: Map<String, String>): String
}
