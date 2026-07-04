package br.com.schmittsolucoes.cacasobmedida.core.injection

import android.net.Uri
import br.com.schmittsolucoes.cacasobmedida.core.database.transaction.DatabaseTransaction
import br.com.schmittsolucoes.cacasobmedida.domain.calculator.GridDimensionCalculator
import br.com.schmittsolucoes.cacasobmedida.domain.generator.PuzzleGenerator
import br.com.schmittsolucoes.cacasobmedida.domain.manager.FileManager
import br.com.schmittsolucoes.cacasobmedida.domain.processor.input.InputProcessor
import br.com.schmittsolucoes.cacasobmedida.domain.processor.pipeline.TextProcessorPipeline
import br.com.schmittsolucoes.cacasobmedida.domain.provider.DeviceDimensionsProvider
import br.com.schmittsolucoes.cacasobmedida.domain.repository.PuzzleSessionRepository
import br.com.schmittsolucoes.cacasobmedida.domain.repository.UserRepository
import br.com.schmittsolucoes.cacasobmedida.domain.repository.WordRepository
import br.com.schmittsolucoes.cacasobmedida.domain.repository.WordSearchPuzzleRepository
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.CreateUserIfNotExistsUseCase
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.EndSessionUseCase
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.GenerateImagePuzzleUseCase
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.GeneratePDFPuzzleUseCase
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.GetAllPuzzlesUseCase
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.GetCountWordsUseCase
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.GetElapsedTimeUseCase
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.GetLastUnfinishedPuzzleUseCase
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.GetNextUserLevelUseCase
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.GetPuzzleByIdUseCase
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.GetRecordPuzzlesUseCase
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.GetUserExperienceByFoundWordUseCase
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.GetUserUseCase
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.GetWordsFromPuzzleUseCase
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.SaveGeneratedPuzzlesUseCase
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.StartSessionUseCase
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.UpdateFoundWordUseCase
import br.com.schmittsolucoes.cacasobmedida.domain.usecase.UpdateUserExperienceUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.io.File

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGenerateImagePuzzleUseCase(
        @ImageProcessor inputProcessor: InputProcessor<File>,
        dimensionsProvider: DeviceDimensionsProvider,
        gridCalculator: GridDimensionCalculator,
        @ImageProcessor textProcessor: TextProcessorPipeline,
        puzzleGenerator: PuzzleGenerator,
        fileManager: FileManager
    ): GenerateImagePuzzleUseCase {
        return GenerateImagePuzzleUseCase(
            inputProcessor,
            dimensionsProvider,
            gridCalculator,
            textProcessor,
            puzzleGenerator,
            fileManager
        )
    }

    @Provides
    fun provideGeneratePDFPuzzleUseCase(
        @PDFProcessor inputProcessor: InputProcessor<Uri>,
        dimensionsProvider: DeviceDimensionsProvider,
        gridCalculator: GridDimensionCalculator,
        @PDFProcessor textProcessor: TextProcessorPipeline,
        puzzleGenerator: PuzzleGenerator
    ): GeneratePDFPuzzleUseCase {
        return GeneratePDFPuzzleUseCase(
            inputProcessor,
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
    fun provideGetCountWordsUseCase(repository: WordRepository): GetCountWordsUseCase {
        return GetCountWordsUseCase(repository)
    }

    @Provides
    fun provideGetLastUnfinishedPuzzleUseCase(repository: WordSearchPuzzleRepository): GetLastUnfinishedPuzzleUseCase {
        return GetLastUnfinishedPuzzleUseCase(repository)
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
    fun provideEndSessionUseCase(repository: PuzzleSessionRepository): EndSessionUseCase {
        return EndSessionUseCase(repository)
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
