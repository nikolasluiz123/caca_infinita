package br.com.schmittsolucoes.cacainfinita.data.repository.mapper

import br.com.schmittsolucoes.cacainfinita.data.model.UserEntity
import br.com.schmittsolucoes.cacainfinita.data.model.dto.UserDTO
import br.com.schmittsolucoes.cacainfinita.domain.model.User

fun User.toEntity() = UserEntity(
    id = id,
    experience = actualExperience,
    level = level,
    puzzlesCompleted = puzzlesCompleted,
    totalWordsFound = totalWordsFound,
    fastestFirstWordMs = fastestFirstWordMs
)

fun UserEntity.toDomain(maxLevelExperience: Long) = User(
    id = id,
    actualExperience = experience,
    maxLevelExperience = maxLevelExperience,
    level = level,
    puzzlesCompleted = puzzlesCompleted,
    totalWordsFound = totalWordsFound,
    fastestFirstWordMs = fastestFirstWordMs
)

fun User.toDTO() = UserDTO(
    id = id,
    experience = actualExperience,
    level = level,
    puzzlesCompleted = puzzlesCompleted,
    totalWordsFound = totalWordsFound,
    fastestFirstWordMs = fastestFirstWordMs
)

fun UserDTO.toDomain(maxLevelExperience: Long) = User(
    id = id,
    actualExperience = experience,
    maxLevelExperience = maxLevelExperience,
    level = level,
    puzzlesCompleted = puzzlesCompleted,
    totalWordsFound = totalWordsFound,
    fastestFirstWordMs = fastestFirstWordMs
)

fun UserEntity.toDTO() = UserDTO(
    id = id,
    experience = experience,
    level = level,
    puzzlesCompleted = puzzlesCompleted,
    totalWordsFound = totalWordsFound,
    fastestFirstWordMs = fastestFirstWordMs
)

fun UserDTO.toEntity() = UserEntity(
    id = id,
    experience = experience,
    level = level,
    puzzlesCompleted = puzzlesCompleted,
    totalWordsFound = totalWordsFound,
    fastestFirstWordMs = fastestFirstWordMs
)
