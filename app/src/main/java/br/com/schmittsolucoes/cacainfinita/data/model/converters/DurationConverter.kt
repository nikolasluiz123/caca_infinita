package br.com.schmittsolucoes.cacainfinita.data.model.converters

import androidx.room.TypeConverter
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

class DurationConverter {
    @TypeConverter
    fun fromLong(value: Long?): Duration? {
        return value?.milliseconds
    }

    @TypeConverter
    fun toLong(duration: Duration?): Long? {
        return duration?.inWholeMilliseconds
    }
}
