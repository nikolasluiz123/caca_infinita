package br.com.schmittsolucoes.cacainfinita.data.manager

import br.com.schmittsolucoes.cacainfinita.domain.manager.AppReviewManager
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class AppReviewManagerImpl @Inject constructor() : AppReviewManager {
    private val _reviewRequest = MutableSharedFlow<Unit>()
    override val reviewRequest = _reviewRequest.asSharedFlow()

    override suspend fun triggerReviewRequest() {
        _reviewRequest.emit(Unit)
    }
}
