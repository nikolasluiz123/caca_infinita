package br.com.schmittsolucoes.cacainfinita.data.analytics

import android.os.Bundle
import br.com.schmittsolucoes.cacainfinita.presentation.analytics.AnalyticsEvents
import br.com.schmittsolucoes.cacainfinita.presentation.analytics.AnalyticsManager
import br.com.schmittsolucoes.cacainfinita.presentation.analytics.AnalyticsParams
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject

class FirebaseAnalyticsManager @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics
) : AnalyticsManager {

    override fun logButtonClick(buttonName: String, buttonAction: String) {
        val params = Bundle().apply {
            putString(AnalyticsParams.BUTTON_NAME, buttonName)
            putString(AnalyticsParams.BUTTON_ACTION, buttonAction)
        }
        firebaseAnalytics.logEvent(AnalyticsEvents.BUTTON_CLICK, params)
    }

    override fun logCardClick(cardName: String, cardAction: String) {
        val params = Bundle().apply {
            putString(AnalyticsParams.CARD_NAME, cardName)
            putString(AnalyticsParams.CARD_ACTION, cardAction)
        }
        firebaseAnalytics.logEvent(AnalyticsEvents.CARD_CLICK, params)
    }

    override fun logNavigation(origin: String, destiny: String) {
        val params = Bundle().apply {
            putString(AnalyticsParams.ORIGIN, origin)
            putString(AnalyticsParams.DESTINY, destiny)
        }
        firebaseAnalytics.logEvent(AnalyticsEvents.NAVIGATION, params)
    }
}
