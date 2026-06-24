package br.com.schmittsolucoes.cacasobmedida.domain.model

data class User(
    override val id: String,
    val actualExperience: Long,
    val maxLevelExperience: Long,
    val level: Long
): UniqueDomain
