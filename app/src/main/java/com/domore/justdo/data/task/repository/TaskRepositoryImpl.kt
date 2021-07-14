package com.domore.justdo.data.task.repository

import com.domore.justdo.data.task.datasource.local.LocalTaskDataSource
import com.domore.justdo.data.vo.Task
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class TaskRepositoryImpl
@Inject constructor(
    private val localTaskDataSource: LocalTaskDataSource
) : TaskRepository {
    override fun getTasks(): Single<List<Task>> = localTaskDataSource.getTasks()

    override fun getTaskById(id: Long): Single<Task> = localTaskDataSource.getTaskById(id)

    override fun getTaskByCategoryId(id: Long): Single<List<Task>> =
        localTaskDataSource.getTaskByCategoryId(id)

}