package br.com.schmittsolucoes.cacasobmedida.core.injection

import br.com.schmittsolucoes.cacasobmedida.data.manager.FileHandler
import br.com.schmittsolucoes.cacasobmedida.data.manager.PDFBoxTextExtractorManager
import br.com.schmittsolucoes.cacasobmedida.domain.manager.FileManager
import br.com.schmittsolucoes.cacasobmedida.domain.manager.PDFTextExtractorManager
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
}
