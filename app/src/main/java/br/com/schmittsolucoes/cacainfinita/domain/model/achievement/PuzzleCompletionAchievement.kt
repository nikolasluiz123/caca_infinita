package br.com.schmittsolucoes.cacainfinita.domain.model.achievement

sealed interface PuzzleCompletionAchievement : Achievement {
    data object JourneyBegins : PuzzleCompletionAchievement {
        override val id = "CgkI69PW2sQIEAIQAQ"
    }

    sealed class Incremental(
        override val id: String,
        override val threshold: Int
    ) : PuzzleCompletionAchievement, IncrementalAchievement {
        data object ApprenticeHunter : Incremental("CgkI69PW2sQIEAIQAg", 10)
        data object WordVeteran : Incremental("CgkI69PW2sQIEAIQAw", 50)
        data object PuzzleMaster : Incremental("CgkI69PW2sQIEAIQBA", 100)
        data object LegendaryVeteran : Incremental("CgkI69PW2sQIEAIQBQ", 250)
        data object SupremeMaster : Incremental("CgkI69PW2sQIEAIQBg", 500)
        data object WordSearchLegend : Incremental("CgkI69PW2sQIEAIQBw", 1000)

        companion object {
            fun entries() = listOf(
                ApprenticeHunter,
                WordVeteran,
                PuzzleMaster,
                LegendaryVeteran,
                SupremeMaster,
                WordSearchLegend
            )
        }
    }
}