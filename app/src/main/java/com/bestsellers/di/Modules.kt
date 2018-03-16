package com.bestsellers.di

import com.bestsellers.bestSellers.BestSellersActivity
import com.bestsellers.bestSellers.BestSellersContract
import com.bestsellers.bestSellers.BestSellersPresenter
import com.bestsellers.bookGenre.BookGenresFragment
import com.bestsellers.bookGenre.BookGenresPresenter
import com.bestsellers.bookdetails.BookDetailsActivity
import com.bestsellers.bookdetails.BookDetailsContract
import com.bestsellers.bookdetails.BookDetailsPresenter
import com.bestsellers.bookdetails.BookGenresContract
import com.bestsellers.data.BestSellersRepository
import com.bestsellers.favorite.FavoriteContract
import com.bestsellers.favorite.FavoriteFragment
import com.bestsellers.favorite.FavoritePresenter
import org.koin.dsl.module.applicationContext

/**
 * Koin main module
 */
val module = applicationContext {

    factory { BookGenresFragment() }
    factory { BookGenresPresenter(get()) as BookGenresContract.Presenter }

    factory { FavoriteFragment() }
    factory { FavoritePresenter(get()) as FavoriteContract.Presenter }

    factory { BestSellersActivity() }
    factory { BestSellersPresenter(get()) as BestSellersContract.Presenter }

    factory { BookDetailsActivity() }
    factory { BookDetailsPresenter(get()) as BookDetailsContract.Presenter }

    bean { BestSellersRepository() }
}

/**
 * module list
 */
val appModules = listOf(module)
