package com.domore.justdo.ui.task.addTask

import dagger.assisted.AssistedFactory

@AssistedFactory
interface AddTaskPresenterFactory {
    fun create(): AddTaskPresenter
}