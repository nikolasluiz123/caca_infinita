package br.com.schmittsolucoes.cacainfinita.data.database.access.assets

interface AssetsLocalDataSource {
    fun readFile(path: String): String
}