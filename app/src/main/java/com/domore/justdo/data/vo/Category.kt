package com.domore.justdo.data.vo

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = CategoryColor::class,
        parentColumns = ["id"],
        childColumns = ["backgroundColorId"]
    )]
)
data class Category(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val backgroundColorId: Long,
    val iconId: Int
)
