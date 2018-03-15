package com.bestsellers.bookGenre

import com.bestsellers.bookDetails.BookGenresContract
import com.bestsellers.data.BestSellersRepository


/**
 * Created by Rafaela
 * on 03/11/2017.
 */
class BookGenresPresenter(
        val view: BookGenresContract.View,
        private val source: BestSellersRepository = BestSellersRepository()) : BookGenresContract.Presenter {

    init {
        view.presenter = this
    }

    override fun requestGenreList() {
        view.showLoading()

        source.getBestSellersGenre({
            view.hideLoading()
            val genres = it.results
            if (genres.isNotEmpty()) {
                view.showGenreList(it.results)
            } else {
                view.showErrorMessage()
            }
        }, {
            view.showErrorMessage()
        })

    }
}