package br.com.schmittsolucoes.cacainfinita.domain.model.achievement

sealed interface AgilityAchievement : Achievement {
    val thresholdMs: Long

    sealed class Normal(
        override val id: String,
        override val thresholdMs: Long
    ) : AgilityAchievement {
        data object LightSpeed : Normal("CgkI69PW2sQIEAIQCg", 2000)
        data object EagleReflex : Normal("CgkI69PW2sQIEAIQCQ", 5000)
        data object SharpEye : Normal("CgkI69PW2sQIEAIQCA", 10000)

        companion object {
            fun entries() = listOf(LightSpeed, EagleReflex, SharpEye)
        }
    }
}