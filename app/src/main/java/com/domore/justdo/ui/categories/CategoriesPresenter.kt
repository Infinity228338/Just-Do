package com.domore.justdo.ui.categories

import com.domore.justdo.data.category.repository.CategoryRepository
import com.domore.justdo.data.vo.Category
import com.domore.justdo.schedulers.Schedulers
import com.domore.justdo.ui.JustDoScreens
import com.domore.justdo.ui.JustDoScreensImpl
import com.domore.justdo.ui.categories.list.CategoriesAddItemView
import com.domore.justdo.ui.categories.list.CategoriesItemView
import com.domore.justdo.ui.categories.list.CategoriesListPresenter
import com.github.terrakok.cicerone.Router
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter

class CategoriesPresenter @AssistedInject constructor(
    private val categoryRepository: CategoryRepository,
    private val schedulers: Schedulers,
    private val router: Router
) : MvpPresenter<CategoriesView>() {
    class CategoriesListPresenterImpl : CategoriesListPresenter {
        val categories = mutableListOf<Category>()
        override var itemClickListener: ((CategoriesItemView) -> Unit)? = null

        override fun getCount() = categories.size

        override fun bindView(view: CategoriesItemView) {
            if (view is CategoriesAddItemView)
                view.bind(Category(0, "add", 0, 0))
            else
                view.bind(categories[view.pos])
        }

    }

    private val screens: JustDoScreens = JustDoScreensImpl
    val categoryListPresenter = CategoriesListPresenterImpl()
    private var disposables = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()

        categoryListPresenter.itemClickListener = { itemView ->
            if (categoryListPresenter.categories.size == 0 ||
                itemView.pos == categoryListPresenter.categories.size
            ) {
                showDialog()
            } else {
                val category = categoryListPresenter.categories[itemView.pos]
                router.navigateTo(screens.addTaskScreen(category.id))
            }
        }
    }

    private fun showDialog() {
        viewState.showDialog()
    }

    private fun loadData() {
        disposables.add(
            categoryRepository
                .getCategories()
                .observeOn(schedulers.main())
                .subscribeOn(schedulers.background())
                .subscribe(
                    ::onFetchSuccess,
                    ::onFetchError
                )
        )
    }

    private fun onFetchSuccess(list: List<Category>) {
        categoryListPresenter.categories.clear()
        categoryListPresenter.categories.addAll(list)
        viewState.updateList()
    }

    private fun onFetchError(throwable: Throwable?) {

    }


}
