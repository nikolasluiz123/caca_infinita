package br.com.schmittsolucoes.cacainfinita.data.repository

import br.com.schmittsolucoes.cacainfinita.data.datasource.remote.GameSnapshotRemoteDataSource
import br.com.schmittsolucoes.cacainfinita.data.model.dto.CloudSaveDTO
import br.com.schmittsolucoes.cacainfinita.data.repository.mapper.toDTO
import br.com.schmittsolucoes.cacainfinita.data.repository.mapper.toDomain
import br.com.schmittsolucoes.cacainfinita.domain.model.GameProgress
import br.com.schmittsolucoes.cacainfinita.domain.repository.GameSnapshotRepository
import javax.inject.Inject

class GameSnapshotRepositoryImpl @Inject constructor(
    private val remoteDataSource: GameSnapshotRemoteDataSource
) : GameSnapshotRepository {

    override suspend fun saveProgress(progress: GameProgress): Boolean {
        val cloudSaveDTO = CloudSaveDTO(
            user = progress.user.toDTO(),
            puzzles = progress.puzzles.map { it.toDTO(progress.user.id) }
        )

        return remoteDataSource.saveProgress(cloudSaveDTO)
    }

    override suspend fun loadProgress(calculateMaxLevelXP: (Long) -> Long): GameProgress? {
        val cloudSaveDTO = remoteDataSource.loadProgress() ?: return null

        return GameProgress(
            user = cloudSaveDTO.user.toDomain(calculateMaxLevelXP(cloudSaveDTO.user.level)),
            puzzles = cloudSaveDTO.puzzles.map { it.toDomain() }
        )
    }
}
