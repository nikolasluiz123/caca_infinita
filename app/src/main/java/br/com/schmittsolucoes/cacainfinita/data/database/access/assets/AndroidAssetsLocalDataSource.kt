package br.com.schmittsolucoes.cacainfinita.data.database.access.assets

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AndroidAssetsLocalDataSource @Inject constructor(
    @param:ApplicationContext private val context: Context
) : AssetsLocalDataSource {
    override fun readFile(path: String): String {
        return context.assets.open(path).bufferedReader().use { it.readText() }
    }
}