package br.com.schmittsolucoes.cacainfinita.domain.provider

interface DeviceDimensionsProvider {
    fun getAvailableWidth(): Float
    fun getAvailableHeight(): Float
    fun getCellSize(): Float
    fun getPaddingStart(): Float
    fun getPaddingEnd(): Float
    fun getPaddingTop(): Float
    fun getPaddingBottom(): Float
}