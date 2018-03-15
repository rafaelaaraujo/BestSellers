package com.bestsellers.favorite

import com.bestsellers.data.BestSellersRepository
import com.bestsellers.data.model.Book

/**
 * Created by rafaela.araujo on 27/02/18.
 */
class FavoritePresenter(
        private val favoriteView: FavoriteContract.View,
        private val repository: BestSellersRepository) : FavoriteContract.Presenter {

    override fun getFavoriteBooks() {
        favoriteView.showLoading()
        val list = repository.getFavoriteBooks()
        if (list != null && list.isNotEmpty()) {
            favoriteView.hideLoading()
            favoriteView.showFavoriteBooks(list)
        } else {
            favoriteView.showErrorMessage()
        }
    }

    override fun removeFavoriteBook(book: Book) {
        repository.removeFavoriteBook(book)
    }
}