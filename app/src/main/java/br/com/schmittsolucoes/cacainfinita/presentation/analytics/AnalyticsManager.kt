package br.com.schmittsolucoes.cacainfinita.presentation.analytics

interface AnalyticsManager {
    fun logButtonClick(buttonName: String, buttonAction: String)
    fun logCardClick(cardName: String, cardAction: String)
    fun logNavigation(origin: String, destiny: String)
}
