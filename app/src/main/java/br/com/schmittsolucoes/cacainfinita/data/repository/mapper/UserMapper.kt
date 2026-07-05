package br.com.schmittsolucoes.cacainfinita.data.repository.mapper

import br.com.schmittsolucoes.cacainfinita.data.model.UserEntity
import br.com.schmittsolucoes.cacainfinita.domain.model.User

fun User.toEntity() = UserEntity(
    id = id,
    experience = actualExperience,
    level = level
)

fun UserEntity.toDomain(maxLevelExperience: Long) = User(
    id = id,
    actualExperience = experience,
    maxLevelExperience = maxLevelExperience,
    level = level
)
