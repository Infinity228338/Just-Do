package com.domore.justdo.storage

import androidx.room.TypeConverter
import com.domore.justdo.data.vo.ModeType
import java.util.*

class TypeConverters {

    @TypeConverter
    fun toMode(value: String) = enumValueOf<ModeType>(value)

    @TypeConverter
    fun fromMode(value: ModeType) = value.name

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}