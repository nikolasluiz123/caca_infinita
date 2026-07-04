package br.com.schmittsolucoes.cacasobmedida.domain.model.pagination

data class PaginationConfig(
    val pageSize: Int,
    val enablePlaceholders: Boolean = false
)
