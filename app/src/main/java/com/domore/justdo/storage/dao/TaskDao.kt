package com.domore.justdo.storage.dao

import androidx.room.*
import com.domore.justdo.data.vo.Task
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface TaskDao {
    @Query("SELECT * FROM Task")
    fun getTasks(): Single<List<Task>>

    @Query("SELECT * FROM Task WHERE id = :id")
    fun getTaskById(id: Long): Single<Task>

    @Query("SELECT * FROM Task WHERE categoryId = :id")
    fun getTaskByCategoryId(id: Long): Single<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun retain(tasks: List<Task>): Completable

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun retain(task: Task): Completable
}