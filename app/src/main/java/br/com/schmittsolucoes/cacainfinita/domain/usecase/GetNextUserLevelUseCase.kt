package br.com.schmittsolucoes.cacainfinita.domain.usecase

/**
 * Caso de uso que define a curva de progressão de nível do jogador.
 *
 * A estratégia utilizada é uma **Progressão Quadrática** (XP = Constante * Nível²).
 * Isso significa que o esforço necessário para subir de nível aumenta de forma acelerada,
 * proporcionando um desafio crescente:
 * - Nível 1 -> 500 XP
 * - Nível 2 -> 2.000 XP
 * - Nível 3 -> 4.500 XP
 * - Nível 10 -> 50.000 XP
 *
 * Essa abordagem mantém o engajamento inicial e recompensa a fidelidade do jogador a longo prazo.
 */
class GetNextUserLevelUseCase {

    companion object {
        /** Fator multiplicador que define a "inclinação" da curva de experiência. */
        private const val LEVEL_CURVE_FACTOR = 500L
    }

    /**
     * Calcula a quantidade total de experiência necessária para completar o nível informado.
     *
     * @param currentLevel O nível que o usuário acabou de alcançar ou está progredindo.
     * @return A experiência máxima necessária para este nível.
     */
    operator fun invoke(currentLevel: Long): Long {
        return LEVEL_CURVE_FACTOR * (currentLevel * currentLevel)
    }
}
