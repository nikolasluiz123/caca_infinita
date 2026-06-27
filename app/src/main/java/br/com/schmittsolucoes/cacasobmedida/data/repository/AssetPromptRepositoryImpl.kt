package br.com.schmittsolucoes.cacasobmedida.data.repository

import br.com.schmittsolucoes.cacasobmedida.data.database.access.assets.AssetsLocalDataSource
import br.com.schmittsolucoes.cacasobmedida.domain.model.enumeration.AITask
import br.com.schmittsolucoes.cacasobmedida.domain.repository.PromptRepository
import javax.inject.Inject

class AssetPromptRepositoryImpl @Inject constructor(
    private val assetsLocalDataSource: AssetsLocalDataSource
) : PromptRepository {

    override suspend fun getPrompt(task: AITask, version: Int, variables: Map<String, String>): String {
        val path = "prompts/${task.path}/v$version.txt"
        val template = assetsLocalDataSource.readFile(path)
        
        return variables.entries.fold(template) { current, (key, value) ->
            current.replace("{{$key}}", value)
        }
    }
}
