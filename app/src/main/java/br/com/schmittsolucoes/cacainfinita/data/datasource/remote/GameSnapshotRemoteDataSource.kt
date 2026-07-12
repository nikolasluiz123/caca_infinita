package br.com.schmittsolucoes.cacainfinita.data.datasource.remote

import br.com.schmittsolucoes.cacainfinita.data.model.dto.CloudSaveDTO

interface GameSnapshotRemoteDataSource {
    suspend fun saveProgress(data: CloudSaveDTO)
    suspend fun loadProgress(): CloudSaveDTO?
}
