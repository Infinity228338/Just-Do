package com.domore.justdo.data.task.datasource.local

import com.domore.justdo.data.vo.Task
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface LocalTaskDataSource {
    fun getTasks(): Single<List<Task>>
    fun getTaskById(id: Long): Single<Task>
    fun getTaskByCategoryId(id: Long): Single<List<Task>>
    fun saveTask(task: Task): Single<Task>
    fun update(task: Task): Completable
}