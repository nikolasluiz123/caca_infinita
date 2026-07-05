package br.com.schmittsolucoes.cacainfinita.domain.manager

import kotlinx.coroutines.flow.StateFlow

interface LoadingManager {
    val isLoading: StateFlow<Boolean>
    val message: StateFlow<String?>

    fun showLoading(message: String? = null)
    fun hideLoading()
}
