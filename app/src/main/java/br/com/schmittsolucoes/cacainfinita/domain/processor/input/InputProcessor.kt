package br.com.schmittsolucoes.cacainfinita.domain.processor.input

interface InputProcessor<T> {
    suspend fun process(input: T): String
}