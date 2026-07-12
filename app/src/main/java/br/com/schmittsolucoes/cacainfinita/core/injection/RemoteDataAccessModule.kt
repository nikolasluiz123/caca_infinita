package br.com.schmittsolucoes.cacainfinita.core.injection

import br.com.schmittsolucoes.cacainfinita.data.datasource.remote.GameSnapshotRemoteDataSource
import br.com.schmittsolucoes.cacainfinita.data.datasource.remote.GooglePlayGamesRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataAccessModule {

    @Binds
    @Singleton
    abstract fun bindGameSnapshotRemoteDataSource(impl: GooglePlayGamesRemoteDataSource): GameSnapshotRemoteDataSource
}
