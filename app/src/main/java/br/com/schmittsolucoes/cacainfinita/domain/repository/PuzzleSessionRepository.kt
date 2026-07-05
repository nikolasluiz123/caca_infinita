package br.com.schmittsolucoes.cacainfinita.domain.repository

import br.com.schmittsolucoes.cacainfinita.domain.model.PuzzleSession
import kotlinx.coroutines.flow.Flow

interface PuzzleSessionRepository {
    suspend fun save(session: PuzzleSession)
    fun getAllSessionsBy(puzzleId: String): Flow<List<PuzzleSession>>
    suspend fun getActualSessionBy(puzzleId: String): PuzzleSession?
}