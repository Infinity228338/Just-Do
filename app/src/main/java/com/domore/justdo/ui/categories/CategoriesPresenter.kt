package com.domore.justdo.ui.categories

import com.domore.justdo.R
import com.domore.justdo.data.category.repository.CategoryRepository
import com.domore.justdo.data.vo.Category
import com.domore.justdo.schedulers.Schedulers
import com.domore.justdo.ui.JustDoScreens
import com.domore.justdo.ui.JustDoScreensImpl
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
                itemView.pos == categoryListPresenter.categories.size - 1
            ) {
                showDialog()
            } else {
                val category = categoryListPresenter.categories[itemView.pos]
                router.sendResult("kek_lol", category.id)
                router.exit()
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
                .subscribe(::onFetchSuccess)
        )
    }

    private fun onFetchSuccess(list: List<Category>) {
        categoryListPresenter.categories.clear()
        categoryListPresenter.categories.addAll(list)
        categoryListPresenter.categories.add(getAddCategoryItem())
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

    fun addCategory(name: String, colorRes: Int, drawRes: Int) {

        categoryRepository.addCategory(name, colorRes, drawRes)
            .subscribeOn(schedulers.background())
            .observeOn(schedulers.main())
            .subscribe(::onCategoryCreated)
    }

    private fun onCategoryCreated(categrory: Category) {
        categoryListPresenter.categories.let {
            it[it.lastIndex] = categrory
            viewState.updateItem(it.lastIndex)
            it.add(getAddCategoryItem())
            viewState.addItemToList(it.lastIndex)
        }
    }

    companion object AddCategoryItem {
        fun getAddCategoryItem() = Category(
            0,
            R.string.add,
            null,
            0,
            R.drawable.predef_icon_add
        )
    }
}
