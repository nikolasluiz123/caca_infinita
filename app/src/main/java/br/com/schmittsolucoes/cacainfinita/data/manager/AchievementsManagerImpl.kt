package br.com.schmittsolucoes.cacainfinita.data.manager

import android.app.Activity
import br.com.schmittsolucoes.cacainfinita.data.provider.ActivityProvider
import br.com.schmittsolucoes.cacainfinita.domain.manager.AchievementsManager
import br.com.schmittsolucoes.cacainfinita.domain.model.achievement.Achievement
import com.google.android.gms.games.AchievementsClient
import com.google.android.gms.games.PlayGames
import java.lang.ref.WeakReference
import javax.inject.Inject

class AchievementsManagerImpl @Inject constructor(
    private val activityProvider: ActivityProvider
) : AchievementsManager {

    private var activityRef = WeakReference<Activity>(null)
    private var clientRef = WeakReference<AchievementsClient>(null)

    override fun unlockAchievement(achievement: Achievement) {
        getAchievementsClient()?.unlock(achievement.id)
    }

    override fun incrementAchievement(achievement: Achievement, steps: Int) {
        getAchievementsClient()?.increment(achievement.id, steps)
    }

    private fun getAchievementsClient(): AchievementsClient? {
        val currentActivity = activityProvider.getActivity() ?: return null
        
        if (activityRef.get() === currentActivity) {
            clientRef.get()?.let { return it }
        }

        val newClient = PlayGames.getAchievementsClient(currentActivity)
        activityRef = WeakReference(currentActivity)
        clientRef = WeakReference(newClient)
        
        return newClient
    }
}
