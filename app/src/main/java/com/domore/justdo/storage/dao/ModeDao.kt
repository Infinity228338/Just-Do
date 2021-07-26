package com.domore.justdo.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.domore.justdo.data.vo.Mode
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface ModeDao {
    @Query("SELECT * FROM Mode")
    fun getModes(): Single<List<Mode>>

    @Query("SELECT * FROM Mode WHERE id = :id")
    fun getModesById(id: Long): Single<Mode>

    @Query("SELECT * FROM Mode WHERE name = :name")
    fun getModeByName(name: String): Single<Mode>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun retain(modes: List<Mode>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun retain(mode: Mode): Single<Long>
}