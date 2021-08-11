package com.domore.justdo.ui.task.tasklist

import com.domore.justdo.data.task.repository.TaskRepository
import com.domore.justdo.data.vo.Task
import com.domore.justdo.schedulers.Schedulers
import com.domore.justdo.ui.task.TaskItemView
import com.github.terrakok.cicerone.Router
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter

class TasksPresenter @AssistedInject constructor(
    private val taskRepository: TaskRepository,
    private val schedulers: Schedulers,
    private val router: Router
) : MvpPresenter<TaskListView>() {

    private var disposables = CompositeDisposable()

    inner class TaskListPresenterImpl : TaskListPresenter {
        val tasks = mutableListOf<Task>()

        override var itemClickListener: ((TaskItemView) -> Unit)? = null

        override fun bindView(view: TaskItemView) {
            view.bind(tasks[view.pos])
        }

        override fun getCount(): Int = tasks.size
    }

    val taskListPresenter = TaskListPresenterImpl()


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()
//        taskListPresenter.itemClickListener = {
//            it.itemClicked(taskListPresenter.tasks[it.pos])
//        }
    }

    private fun loadData() {
        disposables.add(
            taskRepository
                .getTasks()
                .subscribeOn(schedulers.background())
                .observeOn(schedulers.main())
                .subscribe(::onTasksLoaded)
        )
    }

    private fun onTasksLoaded(tasks: List<Task>) {
        taskListPresenter.tasks.clear()
        taskListPresenter.tasks.addAll(tasks)
        if (tasks.isNotEmpty())
            viewState.hidetext()
        viewState.updateList()
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}