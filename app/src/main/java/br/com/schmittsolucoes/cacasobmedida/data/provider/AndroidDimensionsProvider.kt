package br.com.schmittsolucoes.cacasobmedida.data.provider

import android.content.Context
import br.com.schmittsolucoes.cacasobmedida.domain.provider.DeviceDimensionsProvider
import dagger.hilt.android.qualifiers.ApplicationContext

import javax.inject.Inject

class AndroidDimensionsProvider @Inject constructor(
    @param:ApplicationContext private val context: Context
): DeviceDimensionsProvider {

    override fun getAvailableWidth(): Float {
        return context.resources.configuration.screenWidthDp.toFloat()
    }

    override fun getAvailableHeight(): Float {
        return context.resources.configuration.screenHeightDp.toFloat()
    }

    override fun getCellSize(): Float = 50f

    override fun getPaddingStart(): Float = 8f

    override fun getPaddingEnd(): Float = 8f

    override fun getPaddingTop(): Float = 64f

    override fun getPaddingBottom(): Float = 80f
}