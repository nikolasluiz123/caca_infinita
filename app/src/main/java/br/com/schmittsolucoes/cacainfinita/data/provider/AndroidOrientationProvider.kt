package br.com.schmittsolucoes.cacainfinita.data.provider

import android.content.Context
import android.content.res.Configuration
import br.com.schmittsolucoes.cacainfinita.domain.model.enumeration.GridOrientation
import br.com.schmittsolucoes.cacainfinita.domain.provider.OrientationProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AndroidOrientationProvider @Inject constructor(
    @param:ApplicationContext private val context: Context
) : OrientationProvider {
    override fun getSystemOrientation(): GridOrientation {
        return if (context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            GridOrientation.LANDSCAPE
        } else {
            GridOrientation.PORTRAIT
        }
    }
}
