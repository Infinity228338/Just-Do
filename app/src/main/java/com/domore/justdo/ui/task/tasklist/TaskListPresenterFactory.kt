package com.domore.justdo.ui.task.tasklist

import dagger.assisted.AssistedFactory

@AssistedFactory
interface TaskListPresenterFactory {
    fun create(): TasksPresenter
}