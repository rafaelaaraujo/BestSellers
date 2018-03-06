package com.bestsellers.bestSellers

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import com.bestsellers.R
import com.bestsellers.bookDetails.BookDetailsActivity
import com.bestsellers.common.BaseActivity
import com.bestsellers.data.BestSellersData
import com.bestsellers.model.Book
import com.bestsellers.util.*
import com.yarolegovich.discretescrollview.DiscreteScrollView
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import kotlinx.android.synthetic.main.activity_best_sellers.*
import kotlinx.android.synthetic.main.book_card_options.*

/**
 * Created by Rafaela
 * on 03/11/2017.
 */
class BestSellersActivity : BaseActivity(), BestSellersContract.View, DiscreteScrollView.OnItemChangedListener<BestSellersAdapter.ViewHolder> {

    override lateinit var presenter: BestSellersContract.Presenter
    private var booksList = ArrayList<Book>()
    private val MAX_SCALE = 1.05f
    private val MIN_SCALE = 0.8f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_best_sellers)
        presenter = BestSellersPresenter(this, BestSellersData(context = this))
        presenter.requestBestSellers(intent.extras.getString(GENRE_NAME))

        configureView(intent.extras.getString(DISPLAY_NAME))
    }

    private fun configureView(listName: String) {
        configureActionBar(listName)
        configureBestSellersList()
        reviewButton.setOnClickListener { showBookDetails() }
        fabbuyButton.setOnClickListener { openUrlInBrowser(getCurrentBook().amazon_product_url) }
        favoriteButton.setOnClickListener { favoriteBook(getCurrentBook()) }
    }

    private fun favoriteBook(currentBook: Book) {
        favoriteButton.startBounceAnimation()
        presenter.changeBookStatus(currentBook, favoriteButton.isChecked)
    }

    private fun configureBestSellersList() {
        bestSellersList.setItemTransformer(getScaleTransformation())
        bestSellersList.adapter = BestSellersAdapter(booksList)
        bestSellersList.addOnItemChangedListener(this)
    }

    private fun getScaleTransformation() =
            ScaleTransformer.Builder().apply {
                setMaxScale(MAX_SCALE)
                setMinScale(MIN_SCALE)
            }.build()

    private fun showBookDetails() =
            launchActivity<BookDetailsActivity> { putExtra(BOOK, getCurrentBook()) }


    override fun showErrorMessage() {
        hideLoading()
        errorMessage.visibility = VISIBLE
    }

    override fun showLoading() {
        progressBestSellers.visibility = VISIBLE
    }

    override fun hideLoading() {
        progressBestSellers.visibility = GONE
    }

    override fun showBestSellers(bestSeller: List<Book>) {
        booksList.addAll(bestSeller)
        bestSellersList.adapter.notifyDataSetChanged()
        cardOptions.visibility = VISIBLE
    }

    private fun getCurrentBook() = booksList[bestSellersList.currentItem]

    override fun onCurrentItemChanged(viewHolder: BestSellersAdapter.ViewHolder?, position: Int) {
        if (position != -1)
            presenter.verifyIsFavoriteBook(booksList[position])
    }

    override fun changeFavoriteButton(favorite: Boolean) {
        favoriteButton.isChecked = favorite
    }

    override fun showFavoritedBookMessage() {
        showSnackBar(window.decorView,getString(R.string.favorite_message))
    }

    override fun showUnfavoritedBookMessage() {
        showSnackBar(window.decorView,getString(R.string.unfavorable_message))
    }



}