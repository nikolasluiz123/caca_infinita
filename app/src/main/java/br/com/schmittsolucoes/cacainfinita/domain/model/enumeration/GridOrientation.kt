package br.com.schmittsolucoes.cacainfinita.domain.model.enumeration

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
enum class GridOrientation {
    PORTRAIT,
    LANDSCAPE
}
