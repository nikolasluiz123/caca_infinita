package br.com.schmittsolucoes.cacainfinita.domain.model.achievement

sealed interface WordFoundAchievement : IncrementalAchievement {
    sealed class Incremental(
        override val id: String,
        override val threshold: Int
    ) : WordFoundAchievement {
        data object VocabularyUpToDate : Incremental("CgkI69PW2sQIEAIQCw", 50)
        data object TermCollector : Incremental("CgkI69PW2sQIEAIQDA", 500)
        data object LivingDictionary : Incremental("CgkI69PW2sQIEAIQDQ", 2000)
        data object Lexicographer : Incremental("CgkI69PW2sQIEAIQDg", 5000)
        data object HumanEncyclopedia : Incremental("CgkI69PW2sQIEAIQDw", 8000)
        data object LetterOmniscient : Incremental("CgkI69PW2sQIEAIQEA", 10000)

        companion object {
            fun entries() = listOf(
                VocabularyUpToDate,
                TermCollector,
                LivingDictionary,
                Lexicographer,
                HumanEncyclopedia,
                LetterOmniscient
            )
        }
    }
}