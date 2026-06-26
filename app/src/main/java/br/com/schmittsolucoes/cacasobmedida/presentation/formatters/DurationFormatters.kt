package br.com.schmittsolucoes.cacasobmedida.presentation.formatters

import kotlin.time.Duration

fun Duration.formatToClock(): String {
    return toComponents { hours, minutes, seconds, _ ->
        if (hours > 0) {
            "%02d:%02d:%02d".format(hours, minutes, seconds)
        } else {
            "%02d:%02d".format(minutes, seconds)
        }
    }
}
