package br.com.schmittsolucoes.cacainfinita.domain.provider

import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.GridOrientation

interface OrientationProvider {
    fun getSystemOrientation(): GridOrientation
}
