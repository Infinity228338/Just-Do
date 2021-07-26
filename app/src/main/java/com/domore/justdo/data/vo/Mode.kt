package com.domore.justdo.data.vo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Mode(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: ModeType
)

enum class ModeType {
    INTERVAL,
    TIMER,
    PRECISE_TIME
}

enum class TimeTypes {
    INTERVAL_START,
    INTERVAL_END,
    TIMER,
    PRECISE_TIME
}
