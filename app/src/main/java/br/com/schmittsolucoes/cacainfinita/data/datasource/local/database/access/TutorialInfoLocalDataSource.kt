package br.com.schmittsolucoes.cacainfinita.data.datasource.local.database.access

import kotlinx.coroutines.flow.Flow

interface TutorialInfoLocalDataSource {
    fun isTutorialShown(tutorialId: String): Flow<Boolean>
    suspend fun markTutorialAsShown(tutorialId: String)
}
