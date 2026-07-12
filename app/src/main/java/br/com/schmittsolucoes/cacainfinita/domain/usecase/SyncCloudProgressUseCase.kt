package br.com.schmittsolucoes.cacainfinita.domain.usecase

import br.com.schmittsolucoes.cacainfinita.core.database.transaction.DatabaseTransaction
import br.com.schmittsolucoes.cacainfinita.domain.manager.PlayGamesManager
import br.com.schmittsolucoes.cacainfinita.domain.model.GameProgress
import br.com.schmittsolucoes.cacainfinita.domain.repository.GameSnapshotRepository
import br.com.schmittsolucoes.cacainfinita.domain.repository.UserRepository
import br.com.schmittsolucoes.cacainfinita.domain.repository.WordSearchPuzzleRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class SyncCloudProgressUseCase(
    private val userRepository: UserRepository,
    private val wordSearchPuzzleRepository: WordSearchPuzzleRepository,
    private val playGamesManager: PlayGamesManager,
    private val gameSnapshotRepository: GameSnapshotRepository,
    private val getNextUserLevelUseCase: GetNextUserLevelUseCase,
    private val transaction: DatabaseTransaction
) {
    suspend operator fun invoke() = withContext(IO) {
        if (!playGamesManager.isAuthenticated()) return@withContext

        val progress = gameSnapshotRepository.loadProgress { getNextUserLevelUseCase(it) } ?: return@withContext

        if (userRepository.getExistsUser()) {
            val localUser = userRepository.getUser { getNextUserLevelUseCase(it) }

            if (progress.user.actualExperience > localUser.actualExperience) {
                saveInfo(progress)
            }
        } else {
            saveInfo(progress)
        }
    }

    private suspend fun saveInfo(progress: GameProgress) {
        transaction.run {
            userRepository.save(progress.user)
            wordSearchPuzzleRepository.insertFull(progress.puzzles)
        }
    }
}
