package br.com.schmittsolucoes.cacainfinita.data.database.access

import kotlinx.coroutines.flow.Flow

interface TutorialInfoLocalDataSource {
    fun isTutorialShown(tutorialId: String): Flow<Boolean>
    suspend fun markTutorialAsShown(tutorialId: String)
}
