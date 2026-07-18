package br.com.schmittsolucoes.cacainfinita.domain.manager

import kotlinx.coroutines.flow.SharedFlow

interface AppReviewManager {
    val reviewRequest: SharedFlow<Unit>

    suspend fun triggerReviewRequest()
}
