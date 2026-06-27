package br.com.schmittsolucoes.cacasobmedida.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.schmittsolucoes.cacasobmedida.domain.manager.PDFTextExtractorManager
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.CreateUserIfNotExistsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val createUserIfNotExistsUseCase: CreateUserIfNotExistsUseCase,
    private val pdfTextExtractorManager: PDFTextExtractorManager,
    application: Application
) : ViewModel() {

    private val _isInitializing = MutableStateFlow(true)
    val isInitializing = _isInitializing.asStateFlow()

    init {
        viewModelScope.launch {
            pdfTextExtractorManager.initialize(application)
            createUserIfNotExistsUseCase()
            _isInitializing.value = false
        }
    }
}
