package com.domore.justdo.data.vo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
//    foreignKeys = [ForeignKey(
//        entity = CategoryColor::class,
//        parentColumns = ["id"],
//        childColumns = ["backgroundColorId"]
//    ), ForeignKey(
//        entity = CategoryIcon::class,
//        parentColumns = ["id"],
//        childColumns = ["iconId"]
//    )]
)
data class Category(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val nameRes: Int,
    val name: String?,
    val backgroundColorResId: Int,
    val iconResId: Int
)
