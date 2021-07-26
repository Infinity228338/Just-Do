package com.domore.justdo.data.vo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryColor(@PrimaryKey(autoGenerate = true) val id: Long, val colorRes: Int)
