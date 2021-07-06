package com.domore.justdo.data.vo

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Mode::class,
        parentColumns = ["id"],
        childColumns = ["modeId"]
    ), ForeignKey(
        entity = Category::class,
        parentColumns = ["id"],
        childColumns = ["categoryId"]
    )]
)
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val modeId: Long,
    val categoryId: Long
)
