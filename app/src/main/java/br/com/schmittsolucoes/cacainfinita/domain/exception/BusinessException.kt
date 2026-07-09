package br.com.schmittsolucoes.cacainfinita.domain.exception

open class BusinessException(message: String? = null, cause: Throwable? = null) : Exception(message, cause)
