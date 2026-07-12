package br.com.schmittsolucoes.cacainfinita.domain.repository

import br.com.schmittsolucoes.cacainfinita.domain.model.GameProgress

interface GameSnapshotRepository {
    suspend fun saveProgress(progress: GameProgress)
    suspend fun loadProgress(calculateMaxLevelXP: (Long) -> Long): GameProgress?
}
