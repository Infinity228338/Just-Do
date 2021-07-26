package com.domore.justdo.di.category

import com.domore.justdo.ui.categories.CategoriesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CategoryUiModule {
    @ContributesAndroidInjector
    abstract fun bindCategoriesFragment(): CategoriesFragment
}