package br.com.schmittsolucoes.cacainfinita.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.schmittsolucoes.cacainfinita.domain.manager.ExceptionRecorderManager
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class CommonViewModel(
    protected val exceptionRecorderManager: ExceptionRecorderManager
) : ViewModel() {

    abstract fun getErrorMessageFrom(throwable: Throwable): String

    abstract fun onShowErrorDialog(message: String)

    fun launch(block: suspend (scope: CoroutineScope) -> Unit) = viewModelScope.launch(exceptionHandler) {
        block(this)
    }

    protected open fun onError(throwable: Throwable) {
        Log.e("DEBUG_PROCESS", "${this::class.simpleName} - ${throwable.message}", throwable)
        exceptionRecorderManager.record(throwable)
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onShowCommonError(throwable)
        onError(throwable)
    }

    private fun onShowCommonError(throwable: Throwable) {
        val message = getErrorMessageFrom(throwable)
        onShowErrorDialog(message)
    }
}