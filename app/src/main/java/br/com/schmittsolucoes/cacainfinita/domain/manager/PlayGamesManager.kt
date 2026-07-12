package br.com.schmittsolucoes.cacainfinita.domain.manager

interface PlayGamesManager {
    fun initialize()
    suspend fun isAuthenticated(): Boolean
}
