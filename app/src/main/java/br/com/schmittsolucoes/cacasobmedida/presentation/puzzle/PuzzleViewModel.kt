package br.com.schmittsolucoes.cacasobmedida.presentation.puzzle

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import br.com.schmittsolucoes.cacasobmedida.R
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.EndSessionUseCase
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.GetElapsedTimeUseCase
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.StartSessionUseCase
import br.com.schmittsolucoes.cacasobmedida.presentation.CommonViewModel
import br.com.schmittsolucoes.cacasobmedida.presentation.formatters.formatToClock
import br.com.schmittsolucoes.cacasobmedida.presentation.puzzle.navigation.puzzleIdArg
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PuzzleViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    @param:ApplicationContext private val context: Context,
    private val startSessionUseCase: StartSessionUseCase,
    private val endSessionUseCase: EndSessionUseCase,
    private val getElapsedTimeUseCase: GetElapsedTimeUseCase
) : CommonViewModel() {

    private val puzzleId: String = checkNotNull(savedStateHandle[puzzleIdArg])

    private val _errorMessage = MutableStateFlow<String?>(null)
    private val _isLoading = MutableStateFlow(false)

    val uiState: StateFlow<PuzzleUiState> = combine(
        getElapsedTimeUseCase(puzzleId),
        _errorMessage,
        _isLoading
    ) { elapsed, errorMessage, isLoading ->
        PuzzleUiState(
            elapsedTime = elapsed.inWholeMilliseconds,
            formattedTime = elapsed.formatToClock(forceShowHours = true),
            errorMessage = errorMessage,
            isLoading = isLoading
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(3000),
        initialValue = PuzzleUiState()
    )

    fun onStart() {
        launch {
            startSessionUseCase(puzzleId)
        }
    }

    fun onStop() {
        launch {
            endSessionUseCase(puzzleId)
        }
    }

    fun onDismissErrorDialog() {
        _errorMessage.value = null
    }

    override fun getErrorMessageFrom(throwable: Throwable): String {
        return context.getString(R.string.error_unexpected)
    }

    override fun onShowErrorDialog(message: String) {
        _errorMessage.value = message
    }

    override fun onCleared() {
        onStop()
    }
}
