package com.domore.justdo.storage.dao

import androidx.room.*
import com.domore.justdo.data.vo.CategoryIcon
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface CategoryIconDao {
    @Query("SELECT * FROM CategoryIcon")
    fun getIcons(): Single<List<CategoryIcon>>

    @Query("SELECT * FROM CategoryIcon WHERE id = :id")
    fun getIconById(id: Long): Single<CategoryIcon>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun retain(colors: List<CategoryIcon>): Completable

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun retain(color: CategoryIcon): Completable
}