package br.com.schmittsolucoes.cacainfinita.domain.manager

import br.com.schmittsolucoes.cacainfinita.domain.model.achievement.Achievement

interface AchievementsManager {
    fun unlockAchievement(achievement: Achievement)
    fun incrementAchievement(achievement: Achievement, steps: Int)
}
