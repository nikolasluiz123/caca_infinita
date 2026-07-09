package br.com.schmittsolucoes.cacainfinita.data.manager

import br.com.schmittsolucoes.cacainfinita.domain.exception.BusinessException
import br.com.schmittsolucoes.cacainfinita.domain.manager.ExceptionRecorderManager
import com.google.firebase.crashlytics.FirebaseCrashlytics
import javax.inject.Inject

class CrashlyticsExceptionRecorderManager @Inject constructor(
    private val crashlytics: FirebaseCrashlytics
) : ExceptionRecorderManager {
    override fun record(throwable: Throwable) {
        if (throwable !is BusinessException) {
            crashlytics.recordException(throwable)
        }
    }
}
