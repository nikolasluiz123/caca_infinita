package br.com.schmittsolucoes.cacainfinita.core.injection

import android.net.Uri
import br.com.schmittsolucoes.cacainfinita.core.database.transaction.DatabaseTransaction
import br.com.schmittsolucoes.cacainfinita.domain.calculator.GridDimensionCalculator
import br.com.schmittsolucoes.cacainfinita.domain.generator.PuzzleGenerator
import br.com.schmittsolucoes.cacainfinita.domain.manager.FileManager
import br.com.schmittsolucoes.cacainfinita.domain.processor.input.InputProcessor
import br.com.schmittsolucoes.cacainfinita.domain.processor.language.LanguageIdentifierProcessor
import br.com.schmittsolucoes.cacainfinita.domain.processor.pipeline.LanguageIdentifierProcessorPipeline
import br.com.schmittsolucoes.cacainfinita.domain.processor.pipeline.TextProcessorPipeline
import br.com.schmittsolucoes.cacainfinita.domain.provider.DeviceDimensionsProvider
import br.com.schmittsolucoes.cacainfinita.domain.manager.PlayGamesManager
import br.com.schmittsolucoes.cacainfinita.domain.repository.GameSnapshotRepository
import br.com.schmittsolucoes.cacainfinita.domain.repository.PuzzleSessionRepository
import br.com.schmittsolucoes.cacainfinita.domain.repository.UserRepository
import br.com.schmittsolucoes.cacainfinita.domain.repository.WordRepository
import br.com.schmittsolucoes.cacainfinita.domain.repository.WordSearchPuzzleRepository
import br.com.schmittsolucoes.cacainfinita.domain.usecase.CreateUserIfNotExistsUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.DeleteWordSearchPuzzleUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.EndSessionUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.GenerateImagePuzzleUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.GeneratePDFPuzzleUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.GetAllPuzzlesUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.GetElapsedTimeUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.GetHasWordsToSearchUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.GetLastUnfinishedPuzzleUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.GetNextPuzzleToPlayUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.GetNextUserLevelUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.GetPuzzleByIdUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.GetRecordPuzzlesUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.GetSelectedWordUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.GetUserExperienceByFoundWordUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.GetUserUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.GetWordsFromPuzzleUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.LanguageIdentifierUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.SaveGeneratedPuzzlesUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.SaveProgressToCloudUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.StartSessionUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.SyncCloudProgressUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.UpdateFoundWordUseCase
import br.com.schmittsolucoes.cacainfinita.domain.usecase.UpdateUserExperienceUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.io.File

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideImageLanguageIdentifierUseCase(
        @ImageProcessor inputProcessor: InputProcessor<File>,
        @ImageLanguageProcessor pipeline: LanguageIdentifierProcessorPipeline,
        identifierProcessor: LanguageIdentifierProcessor
    ): LanguageIdentifierUseCase<File> {
        return LanguageIdentifierUseCase(inputProcessor, pipeline, identifierProcessor)
    }

    @Provides
    fun providePDFLanguageIdentifierUseCase(
        @PDFProcessor inputProcessor: InputProcessor<Uri>,
        @PDFLanguageProcessor pipeline: LanguageIdentifierProcessorPipeline,
        identifierProcessor: LanguageIdentifierProcessor
    ): LanguageIdentifierUseCase<Uri> {
        return LanguageIdentifierUseCase(inputProcessor, pipeline, identifierProcessor)
    }

    @Provides
    fun provideGenerateImagePuzzleUseCase(
        dimensionsProvider: DeviceDimensionsProvider,
        gridCalculator: GridDimensionCalculator,
        @ImageProcessor textProcessor: TextProcessorPipeline,
        puzzleGenerator: PuzzleGenerator
    ): GenerateImagePuzzleUseCase {
        return GenerateImagePuzzleUseCase(
            dimensionsProvider,
            gridCalculator,
            textProcessor,
            puzzleGenerator
        )
    }

    @Provides
    fun provideGeneratePDFPuzzleUseCase(
        dimensionsProvider: DeviceDimensionsProvider,
        gridCalculator: GridDimensionCalculator,
        @PDFProcessor textProcessor: TextProcessorPipeline,
        puzzleGenerator: PuzzleGenerator
    ): GeneratePDFPuzzleUseCase {
        return GeneratePDFPuzzleUseCase(
            dimensionsProvider,
            gridCalculator,
            textProcessor,
            puzzleGenerator
        )
    }

    @Provides
    fun provideGetAllPuzzlesUseCase(repository: WordSearchPuzzleRepository): GetAllPuzzlesUseCase {
        return GetAllPuzzlesUseCase(repository)
    }

    @Provides
    fun provideDeleteWordSearchPuzzleUseCase(repository: WordSearchPuzzleRepository): DeleteWordSearchPuzzleUseCase {
        return DeleteWordSearchPuzzleUseCase(repository)
    }

    @Provides
    fun provideGetHasWordsToSearchUseCase(repository: WordRepository): GetHasWordsToSearchUseCase {
        return GetHasWordsToSearchUseCase(repository)
    }

    @Provides
    fun provideGetLastUnfinishedPuzzleUseCase(repository: WordSearchPuzzleRepository): GetLastUnfinishedPuzzleUseCase {
        return GetLastUnfinishedPuzzleUseCase(repository)
    }

    @Provides
    fun provideGetNextPuzzleToPlayUseCase(repository: WordSearchPuzzleRepository): GetNextPuzzleToPlayUseCase {
        return GetNextPuzzleToPlayUseCase(repository)
    }

    @Provides
    fun provideGetNextUserLevelUseCase(): GetNextUserLevelUseCase {
        return GetNextUserLevelUseCase()
    }

    @Provides
    fun provideGetPuzzleByIdUseCase(repository: WordSearchPuzzleRepository): GetPuzzleByIdUseCase {
        return GetPuzzleByIdUseCase(repository)
    }

    @Provides
    fun provideGetRecordPuzzlesUseCase(repository: WordSearchPuzzleRepository): GetRecordPuzzlesUseCase {
        return GetRecordPuzzlesUseCase(repository)
    }

    @Provides
    fun provideGetSelectedWordUseCase(): GetSelectedWordUseCase {
        return GetSelectedWordUseCase()
    }

    @Provides
    fun provideGetUserExperienceByFoundWordUseCase(
        wordRepository: WordRepository,
        puzzleSessionRepository: PuzzleSessionRepository
    ): GetUserExperienceByFoundWordUseCase {
        return GetUserExperienceByFoundWordUseCase(wordRepository, puzzleSessionRepository)
    }

    @Provides
    fun provideGetUserUseCase(
        userRepository: UserRepository,
        getNextUserLevelUseCase: GetNextUserLevelUseCase
    ): GetUserUseCase {
        return GetUserUseCase(userRepository, getNextUserLevelUseCase)
    }

    @Provides
    fun provideCreateUserIfNotExistsUseCase(
        userRepository: UserRepository,
        getNextUserLevelUseCase: GetNextUserLevelUseCase
    ): CreateUserIfNotExistsUseCase {
        return CreateUserIfNotExistsUseCase(userRepository, getNextUserLevelUseCase)
    }

    @Provides
    fun provideGetElapsedTimeUseCase(repository: PuzzleSessionRepository): GetElapsedTimeUseCase {
        return GetElapsedTimeUseCase(repository)
    }

    @Provides
    fun provideStartSessionUseCase(repository: PuzzleSessionRepository): StartSessionUseCase {
        return StartSessionUseCase(repository)
    }

    @Provides
    fun provideEndSessionUseCase(
        repository: PuzzleSessionRepository,
        saveProgressToCloudUseCase: SaveProgressToCloudUseCase
    ): EndSessionUseCase {
        return EndSessionUseCase(repository, saveProgressToCloudUseCase)
    }

    @Provides
    fun provideSaveProgressToCloudUseCase(
        userRepository: UserRepository,
        wordSearchPuzzleRepository: WordSearchPuzzleRepository,
        playGamesManager: PlayGamesManager,
        gameSnapshotRepository: GameSnapshotRepository,
        getNextUserLevelUseCase: GetNextUserLevelUseCase
    ): SaveProgressToCloudUseCase {
        return SaveProgressToCloudUseCase(
            userRepository,
            wordSearchPuzzleRepository,
            playGamesManager,
            gameSnapshotRepository,
            getNextUserLevelUseCase
        )
    }

    @Provides
    fun provideSyncCloudProgressUseCase(
        userRepository: UserRepository,
        wordSearchPuzzleRepository: WordSearchPuzzleRepository,
        playGamesManager: PlayGamesManager,
        gameSnapshotRepository: GameSnapshotRepository,
        getNextUserLevelUseCase: GetNextUserLevelUseCase,
        transaction: DatabaseTransaction
    ): SyncCloudProgressUseCase {
        return SyncCloudProgressUseCase(
            userRepository,
            wordSearchPuzzleRepository,
            playGamesManager,
            gameSnapshotRepository,
            getNextUserLevelUseCase,
            transaction
        )
    }

    @Provides
    fun provideGetWordsFromPuzzleUseCase(repository: WordRepository): GetWordsFromPuzzleUseCase {
        return GetWordsFromPuzzleUseCase(repository)
    }

    @Provides
    fun provideSaveGeneratedPuzzlesUseCase(
        wordSearchPuzzleRepository: WordSearchPuzzleRepository,
    ): SaveGeneratedPuzzlesUseCase {
        return SaveGeneratedPuzzlesUseCase(wordSearchPuzzleRepository)
    }

    @Provides
    fun provideUpdateFoundWordUseCase(
        wordRepository: WordRepository,
        updateUserExperienceUseCase: UpdateUserExperienceUseCase,
        transaction: DatabaseTransaction
    ): UpdateFoundWordUseCase {
        return UpdateFoundWordUseCase(wordRepository, updateUserExperienceUseCase, transaction)
    }

    @Provides
    fun provideUpdateUserExperienceUseCase(
        getUserUseCase: GetUserUseCase,
        userRepository: UserRepository,
        getUserExperienceByFoundWordUseCase: GetUserExperienceByFoundWordUseCase,
        getNextUserLevelUseCase: GetNextUserLevelUseCase
    ): UpdateUserExperienceUseCase {
        return UpdateUserExperienceUseCase(
            getUserUseCase,
            userRepository,
            getUserExperienceByFoundWordUseCase,
            getNextUserLevelUseCase
        )
    }
}
