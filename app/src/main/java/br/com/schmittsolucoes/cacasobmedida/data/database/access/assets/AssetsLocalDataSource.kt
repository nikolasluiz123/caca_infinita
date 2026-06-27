package br.com.schmittsolucoes.cacasobmedida.data.database.access.assets

interface AssetsLocalDataSource {
    fun readFile(path: String): String
}