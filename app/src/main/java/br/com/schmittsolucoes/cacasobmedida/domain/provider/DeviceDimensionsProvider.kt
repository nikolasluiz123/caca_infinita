package br.com.schmittsolucoes.cacasobmedida.domain.provider

interface DeviceDimensionsProvider {
    fun getAvailableWidth(): Float
    fun getAvailableHeight(): Float
    fun getCellSize(): Float
    fun getHorizontalPadding(): Float
    fun getVerticalPadding(): Float
}