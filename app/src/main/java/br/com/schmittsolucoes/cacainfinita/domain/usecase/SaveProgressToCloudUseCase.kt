package br.com.schmittsolucoes.cacainfinita.domain.usecase

import br.com.schmittsolucoes.cacainfinita.domain.manager.PlayGamesManager
import br.com.schmittsolucoes.cacainfinita.domain.model.GameProgress
import br.com.schmittsolucoes.cacainfinita.domain.repository.GameSnapshotRepository
import br.com.schmittsolucoes.cacainfinita.domain.repository.UserRepository
import br.com.schmittsolucoes.cacainfinita.domain.repository.WordSearchPuzzleRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class SaveProgressToCloudUseCase(
    private val userRepository: UserRepository,
    private val wordSearchPuzzleRepository: WordSearchPuzzleRepository,
    private val playGamesManager: PlayGamesManager,
    private val gameSnapshotRepository: GameSnapshotRepository,
    private val getNextUserLevelUseCase: GetNextUserLevelUseCase
) {
    suspend operator fun invoke() = withContext(IO) {
        if (!playGamesManager.isAuthenticated()) return@withContext

        val user = userRepository.getUser { getNextUserLevelUseCase(it) }

        val records = wordSearchPuzzleRepository.getRecords().first()
        val recordIds = records.map { it.id }
        val recordPuzzles = wordSearchPuzzleRepository.getFullPuzzlesByIds(recordIds)
        
        val unfinishedPuzzles = wordSearchPuzzleRepository.getUnfinishedFullPuzzles()
        val allPuzzles = (recordPuzzles + unfinishedPuzzles).distinctBy { it.puzzle.id }

        val progress = GameProgress(
            user = user,
            puzzles = allPuzzles
        )

        gameSnapshotRepository.saveProgress(progress)
    }
}
