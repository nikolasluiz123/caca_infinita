package br.com.schmittsolucoes.cacainfinita.presentation.formatters

import java.util.Locale
import kotlin.time.Duration

fun Duration.formatToClock(forceShowHours: Boolean = false): String {
    return toComponents { hours, minutes, seconds, _ ->
        if (forceShowHours || hours > 0) {
            String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)
        } else {
            String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
        }
    }
}
