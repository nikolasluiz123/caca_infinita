package br.com.schmittsolucoes.cacainfinita.data.provider

import android.app.Activity

interface ActivityProvider {
    fun updateActivity(activity: Activity)
    fun getActivity(): Activity?
    fun clear()
}
