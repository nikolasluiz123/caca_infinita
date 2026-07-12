package br.com.schmittsolucoes.cacainfinita.data.manager

import android.app.Application
import br.com.schmittsolucoes.cacainfinita.data.provider.ActivityProvider
import br.com.schmittsolucoes.cacainfinita.domain.manager.PlayGamesManager
import com.google.android.gms.games.PlayGames
import com.google.android.gms.games.PlayGamesSdk
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

class PlayGamesManagerImpl @Inject constructor(
    private val application: Application,
    private val activityProvider: ActivityProvider
) : PlayGamesManager {

    override fun initialize() {
        PlayGamesSdk.initialize(application)
    }

    override suspend fun isAuthenticated(): Boolean {
        return activityProvider.getActivity()?.let { activity ->
            try {
                PlayGames.getGamesSignInClient(activity).isAuthenticated.await().isAuthenticated
            } catch (e: Exception) {
                false
            }
        } ?: false
    }
}
