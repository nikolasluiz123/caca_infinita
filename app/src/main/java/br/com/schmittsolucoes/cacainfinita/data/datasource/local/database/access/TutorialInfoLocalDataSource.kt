package br.com.schmittsolucoes.cacainfinita.data.datasource.local.database.access

import kotlinx.coroutines.flow.Flow

interface TutorialInfoLocalDataSource {
    fun isShowcaseShown(showcaseId: String): Flow<Boolean>
    suspend fun markShowcaseAsShown(showcaseId: String)
}
