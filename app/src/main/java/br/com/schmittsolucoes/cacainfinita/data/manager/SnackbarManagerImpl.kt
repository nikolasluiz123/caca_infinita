package br.com.schmittsolucoes.cacainfinita.data.manager

import br.com.schmittsolucoes.cacainfinita.domain.manager.SnackbarManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class SnackbarManagerImpl @Inject constructor() : SnackbarManager {
    private val _message = MutableStateFlow<String?>(null)
    override val message: StateFlow<String?> = _message.asStateFlow()

    override fun showSnackbar(message: String) {
        _message.value = message
    }

    override fun hideSnackbar() {
        _message.value = null
    }
}
