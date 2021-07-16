package com.domore.justdo.storage.dao

import androidx.room.*
import com.domore.justdo.data.vo.CategoryColor
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface CategoryColorDao {
    @Query("SELECT * FROM CategoryColor")
    fun getColors(): Single<List<CategoryColor>>

    @Query("SELECT * FROM CategoryColor WHERE id = :id")
    fun getColorById(id: Long): Single<CategoryColor>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun retain(colors: List<CategoryColor>): Completable

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun retain(color: CategoryColor): Completable
}