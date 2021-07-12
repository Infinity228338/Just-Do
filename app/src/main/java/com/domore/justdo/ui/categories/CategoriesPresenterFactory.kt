package com.domore.justdo.ui.categories

import dagger.assisted.AssistedFactory

@AssistedFactory

interface CategoriesPresenterFactory {

    fun create(): CategoriesPresenter

}