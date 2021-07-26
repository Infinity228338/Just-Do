package com.domore.justdo.data.task.repository

import com.domore.justdo.data.vo.Task
import io.reactivex.rxjava3.core.Single

interface TaskRepository {
    fun getTasks(): Single<List<Task>>
    fun getTaskById(id: Long): Single<Task>
    fun getTaskByCategoryId(id: Long): Single<List<Task>>
    fun saveTask(task: Task): Single<Task>
}