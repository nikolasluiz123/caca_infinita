package br.com.schmittsolucoes.cacasobmedida.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.GetLastUnfinishedPuzzleUseCase
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.GetRecordPuzzlesUseCase
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getUserUseCase: GetUserUseCase,
    getRecordPuzzlesUseCase: GetRecordPuzzlesUseCase,
    getLastUnfinishedPuzzleUseCase: GetLastUnfinishedPuzzleUseCase
): ViewModel() {

    val uiState: StateFlow<HomeUIState> = combine(
        getUserUseCase.observable(),
        getRecordPuzzlesUseCase(),
        getLastUnfinishedPuzzleUseCase()
    ) { user, records, unfinishedPuzzleId ->
        HomeUIState(
            user = user,
            records = records,
            unfinishedPuzzleId = unfinishedPuzzleId
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3_000),
        initialValue = HomeUIState()
    )
}
