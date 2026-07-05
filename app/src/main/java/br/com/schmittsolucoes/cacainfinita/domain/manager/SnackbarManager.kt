package br.com.schmittsolucoes.cacainfinita.domain.manager

import kotlinx.coroutines.flow.StateFlow

interface SnackbarManager {
    val message: StateFlow<String?>

    fun showSnackbar(message: String)
    fun hideSnackbar()
}
