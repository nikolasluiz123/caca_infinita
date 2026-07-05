package br.com.schmittsolucoes.cacainfinita.data.database.access

interface EntityLocalDataSource<T> {
    suspend fun insert(entity: List<T>)
    suspend fun update(entity: List<T>)
    suspend fun delete(entity: List<T>)
    suspend fun upsert(entity: List<T>)
}
