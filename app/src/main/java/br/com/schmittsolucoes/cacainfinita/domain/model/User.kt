package br.com.schmittsolucoes.cacainfinita.domain.model

data class User(
    override val id: String,
    val actualExperience: Long,
    val maxLevelExperience: Long,
    val level: Long,
    val puzzlesCompleted: Long,
    val totalWordsFound: Long,
    val fastestFirstWordMs: Long?
): UniqueDomain
