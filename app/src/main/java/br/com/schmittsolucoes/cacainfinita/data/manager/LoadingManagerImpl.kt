package br.com.schmittsolucoes.cacainfinita.data.manager

import br.com.schmittsolucoes.cacainfinita.domain.manager.LoadingManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoadingManagerImpl @Inject constructor() : LoadingManager {
    private val _isLoading = MutableStateFlow(false)
    override val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _message = MutableStateFlow<String?>(null)
    override val message: StateFlow<String?> = _message.asStateFlow()

    override fun showLoading(message: String?) {
        _message.value = message
        _isLoading.value = true
    }

    override fun hideLoading() {
        _isLoading.value = false
        _message.value = null
    }
}
