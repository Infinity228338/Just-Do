package com.domore.justdo.data.task.datasource.local

import com.domore.justdo.data.vo.Task
import com.domore.justdo.storage.JustDoDatabase
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LocalTaskDataSourceImpl
@Inject constructor(
    justDoDb: JustDoDatabase
) : LocalTaskDataSource {
    private val taskDao = justDoDb.taskDao()

    override fun getTasks(): Single<List<Task>> = taskDao.getTasks()

    override fun getTaskById(id: Long): Single<Task> = taskDao.getTaskById(id)

    override fun getTaskByCategoryId(id: Long): Single<List<Task>> = taskDao.getTaskByCategoryId(id)

    override fun saveTask(task: Task): Single<Task> =
        taskDao
            .retain(task)
            .flatMap {
                getTaskById(id = it)
            }

    override fun update(task: Task): Completable = taskDao.update(task)

}