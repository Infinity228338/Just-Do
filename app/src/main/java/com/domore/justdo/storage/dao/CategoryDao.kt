package com.domore.justdo.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.domore.justdo.data.vo.Category
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface CategoryDao {
    @Query("SELECT * FROM Category")
    fun getCategories(): Single<List<Category>>

    @Query("SELECT * FROM Category WHERE id = :id")
    fun getCategoriesById(id: Long): Single<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun retain(categories: List<Category>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun retain(category: Category): Single<Long>
}