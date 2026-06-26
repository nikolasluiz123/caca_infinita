package br.com.schmittsolucoes.cacasobmedida.data.provider

import br.com.schmittsolucoes.cacasobmedida.domain.provider.DeviceDimensionsProvider

import javax.inject.Inject

class AndroidDimensionsProvider @Inject constructor(): DeviceDimensionsProvider {

    override fun getAvailableWidth(): Float {
        TODO("Not yet implemented")
    }

    override fun getAvailableHeight(): Float {
        TODO("Not yet implemented")
    }

    override fun getCellSize(): Float {
        TODO("Not yet implemented")
    }

    override fun getHorizontalPadding(): Float {
        TODO("Not yet implemented")
    }

    override fun getVerticalPadding(): Float {
        TODO("Not yet implemented")
    }
}