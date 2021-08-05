package com.domore.justdo.data.vo

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    foreignKeys = [ForeignKey(
        entity = Category::class,
        parentColumns = ["id"],
        childColumns = ["categoryId"]
    )]
)
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Long,
    var name: String?,
    var mode: String?,
    var categoryId: Long?,
    var iconResId: Int?,
    var date: Date?,
    var timeStart: Date?,
    var timeEnd: Date?,
    var timerTime: String?
) {
    constructor() : this(
        0,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    )
}

