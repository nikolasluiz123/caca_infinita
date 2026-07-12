package br.com.schmittsolucoes.cacainfinita.data.provider

import android.app.Activity
import java.lang.ref.WeakReference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AndroidActivityProvider @Inject constructor() : ActivityProvider {
    private var activityReference: WeakReference<Activity>? = null

    override fun updateActivity(activity: Activity) {
        activityReference = WeakReference(activity)
    }

    override fun getActivity(): Activity? {
        return activityReference?.get()
    }

    override fun clear() {
        activityReference = null
    }
}
