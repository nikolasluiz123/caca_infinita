package br.com.schmittsolucoes.cacainfinita.core.injection

import br.com.schmittsolucoes.cacainfinita.data.analytics.FirebaseAnalyticsManager
import br.com.schmittsolucoes.cacainfinita.data.manager.AchievementsManagerImpl
import br.com.schmittsolucoes.cacainfinita.data.manager.CrashlyticsExceptionRecorderManager
import br.com.schmittsolucoes.cacainfinita.data.manager.FileHandler
import br.com.schmittsolucoes.cacainfinita.data.manager.LoadingManagerImpl
import br.com.schmittsolucoes.cacainfinita.data.manager.PDFBoxTextExtractorManager
import br.com.schmittsolucoes.cacainfinita.data.manager.PlayGamesManagerImpl
import br.com.schmittsolucoes.cacainfinita.data.manager.SnackbarManagerImpl
import br.com.schmittsolucoes.cacainfinita.data.manager.TutorialManagerImpl
import br.com.schmittsolucoes.cacainfinita.domain.manager.AchievementsManager
import br.com.schmittsolucoes.cacainfinita.domain.manager.ExceptionRecorderManager
import br.com.schmittsolucoes.cacainfinita.domain.manager.FileManager
import br.com.schmittsolucoes.cacainfinita.domain.manager.LoadingManager
import br.com.schmittsolucoes.cacainfinita.domain.manager.PDFTextExtractorManager
import br.com.schmittsolucoes.cacainfinita.domain.manager.PlayGamesManager
import br.com.schmittsolucoes.cacainfinita.domain.manager.SnackbarManager
import br.com.schmittsolucoes.cacainfinita.domain.manager.TutorialManager
import br.com.schmittsolucoes.cacainfinita.presentation.analytics.AnalyticsManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ManagerModule {

    @Binds
    abstract fun bindPDFTextExtractorManager(impl: PDFBoxTextExtractorManager): PDFTextExtractorManager

    @Binds
    abstract fun bindFileManager(impl: FileHandler): FileManager

    @Binds
    @Singleton
    abstract fun bindLoadingManager(impl: LoadingManagerImpl): LoadingManager

    @Binds
    @Singleton
    abstract fun bindSnackbarManager(impl: SnackbarManagerImpl): SnackbarManager

    @Binds
    @Singleton
    abstract fun bindTutorialManager(impl: TutorialManagerImpl): TutorialManager

    @Binds
    abstract fun bindExceptionRecorderManager(impl: CrashlyticsExceptionRecorderManager): ExceptionRecorderManager

    @Binds
    abstract fun bindAnalyticsManager(impl: FirebaseAnalyticsManager): AnalyticsManager

    @Binds
    @Singleton
    abstract fun bindPlayGamesManager(impl: PlayGamesManagerImpl): PlayGamesManager

    @Binds
    @Singleton
    abstract fun bindAchievementsManager(impl: AchievementsManagerImpl): AchievementsManager
}
