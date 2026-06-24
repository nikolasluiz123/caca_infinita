package br.com.schmittsolucoes.cacasobmedida.domain.processor.input

interface InputProcessor<T> {
    suspend fun process(input: T): String
}