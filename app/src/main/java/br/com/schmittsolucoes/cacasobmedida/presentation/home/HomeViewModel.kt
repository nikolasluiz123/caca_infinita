package br.com.schmittsolucoes.cacasobmedida.presentation.home

import androidx.lifecycle.viewModelScope
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.GetLastUnfinishedPuzzleUseCase
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.GetRecordPuzzlesUseCase
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.GetUserUseCase
import br.com.schmittsolucoes.cacasobmedida.R
import br.com.schmittsolucoes.cacasobmedida.presentation.CommonViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getUserUseCase: GetUserUseCase,
    getRecordPuzzlesUseCase: GetRecordPuzzlesUseCase,
    getLastUnfinishedPuzzleUseCase: GetLastUnfinishedPuzzleUseCase,
    private val application: android.app.Application
): CommonViewModel() {

    private val _errorMessage = MutableStateFlow<String?>(null)

    val uiState: StateFlow<HomeUIState> = combine(
        getUserUseCase.observable(),
        getRecordPuzzlesUseCase(),
        getLastUnfinishedPuzzleUseCase(),
        _errorMessage
    ) { user, records, unfinishedPuzzleId, errorMessage ->
        HomeUIState(
            user = user,
            records = records,
            unfinishedPuzzleId = unfinishedPuzzleId,
            errorMessage = errorMessage
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3_000),
        initialValue = HomeUIState()
    )

    override fun getErrorMessageFrom(throwable: Throwable): String {
        return application.getString(R.string.error_unexpected)
    }

    override fun onShowErrorDialog(message: String) {
        _errorMessage.value = message
    }

    fun onDismissErrorDialog() {
        _errorMessage.value = null
    }
}
