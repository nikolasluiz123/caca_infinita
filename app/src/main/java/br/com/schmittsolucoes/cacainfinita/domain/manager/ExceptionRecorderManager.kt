package br.com.schmittsolucoes.cacainfinita.domain.manager

interface ExceptionRecorderManager {
    fun record(throwable: Throwable)
}
