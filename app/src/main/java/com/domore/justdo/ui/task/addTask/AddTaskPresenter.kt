package com.domore.justdo.ui.task.addTask

import com.domore.justdo.data.task.repository.TaskRepository
import com.domore.justdo.schedulers.Schedulers
import com.github.terrakok.cicerone.Router
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import java.util.*

class AddTaskPresenter @AssistedInject constructor(
    private val taskRepository: TaskRepository,
    private val schedulers: Schedulers,
    private val router: Router
) : MvpPresenter<AddTaskView>() {

    private var disposables = CompositeDisposable()
    private var cardTaskModeExpanded = false
    private var intervalTimeExpanded = false


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
    }

    fun cardTaskModeClicked() {
        cardTaskModeExpanded = !cardTaskModeExpanded
        viewState.showOrHideModes(cardTaskModeExpanded)
        viewState.showOrHideIntervalTime(intervalTimeExpanded && cardTaskModeExpanded)
    }

    fun modeClicked() {
        intervalTimeExpanded = !intervalTimeExpanded
        viewState.showOrHideIntervalTime(intervalTimeExpanded && cardTaskModeExpanded)
    }

    fun dateClicked() {
        viewState.showDatePicker()
    }

    fun setDate(dateAndTime: Calendar) {
        dateAndTime.time
    }

    fun timeStartClicked() {
        TODO("Not yet implemented")
    }

    fun timeEndClicked() {
        TODO("Not yet implemented")
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