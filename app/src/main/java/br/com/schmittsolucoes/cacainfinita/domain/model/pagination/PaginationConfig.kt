package br.com.schmittsolucoes.cacainfinita.domain.model.pagination

data class PaginationConfig(
    val pageSize: Int,
    val enablePlaceholders: Boolean = false
)
