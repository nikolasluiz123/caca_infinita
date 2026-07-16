package br.com.schmittsolucoes.cacainfinita.domain.model.achievement

sealed interface IncrementalAchievement : Achievement {
    val threshold: Int
}