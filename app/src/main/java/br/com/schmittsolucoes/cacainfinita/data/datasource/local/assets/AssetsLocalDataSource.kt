package br.com.schmittsolucoes.cacainfinita.data.datasource.local.assets

interface AssetsLocalDataSource {
    fun readFile(path: String): String
}