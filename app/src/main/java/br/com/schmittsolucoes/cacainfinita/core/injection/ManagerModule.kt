package br.com.schmittsolucoes.cacainfinita.core.injection

import br.com.schmittsolucoes.cacainfinita.data.manager.CrashlyticsExceptionRecorderManager
import br.com.schmittsolucoes.cacainfinita.data.manager.FileHandler
import br.com.schmittsolucoes.cacainfinita.data.manager.LoadingManagerImpl
import br.com.schmittsolucoes.cacainfinita.data.manager.PDFBoxTextExtractorManager
import br.com.schmittsolucoes.cacainfinita.data.manager.SnackbarManagerImpl
import br.com.schmittsolucoes.cacainfinita.domain.manager.ExceptionRecorderManager
import br.com.schmittsolucoes.cacainfinita.domain.manager.FileManager
import br.com.schmittsolucoes.cacainfinita.domain.manager.LoadingManager
import br.com.schmittsolucoes.cacainfinita.domain.manager.PDFTextExtractorManager
import br.com.schmittsolucoes.cacainfinita.domain.manager.SnackbarManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ManagerModule {

    @Binds
    abstract fun bindPDFTextExtractorManager(impl: PDFBoxTextExtractorManager): PDFTextExtractorManager

    @Binds
    abstract fun bindFileManager(impl: FileHandler): FileManager

    @Binds
    abstract fun bindLoadingManager(impl: LoadingManagerImpl): LoadingManager

    @Binds
    abstract fun bindSnackbarManager(impl: SnackbarManagerImpl): SnackbarManager

    @Binds
    abstract fun bindExceptionRecorderManager(impl: CrashlyticsExceptionRecorderManager): ExceptionRecorderManager
}
