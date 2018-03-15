package com.bestsellers.bestSellers.genre

import com.bestsellers.bestSellers.BaseTest
import com.bestsellers.bookDetails.BookGenresContract
import com.bestsellers.bookGenre.BookGenresPresenter
import com.bestsellers.data.BestSellersRepository
import com.bestsellers.data.model.BookGenres
import com.bestsellers.data.model.Genre
import com.bestsellers.data.remote.BestSellersService
import io.reactivex.Observable.just
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


/**
 * Created by rafaela.araujo on 15/03/18.
 */

class BookGenrePresenterTest: BaseTest() {

    @Mock private lateinit var view: BookGenresContract.View
    private lateinit var presenter: BookGenresContract.Presenter

    @Before
    fun setup() {
        initMock()
        presenter = BookGenresPresenter(view, repository)
    }

    @Test
    fun createPresenter_setsThePresenterToView() {
        verify(view).presenter = presenter
    }

    @Test
    fun getGenreList_AndUpdateView() {
        `when`(service.getGenreList()).thenReturn(just(getMockGenreList()))

       presenter.apply {
            requestGenreList()
        }

        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).showGenreList(getMockGenreList().results)
        verify(view, never()).showErrorMessage()
    }


    @Test
    fun getEmptyGenreList_ShowMessageError() {
        `when`(service.getGenreList()).thenReturn(just(BookGenres(ArrayList())))

       presenter.apply {
            requestGenreList()
        }

        verify(view).showLoading()
        verify(view).hideLoading()
        verify(view).showErrorMessage()
        verify(view, never()).showGenreList(ArrayList())
    }

    private fun getMockGenreList(): BookGenres {
        return BookGenres(listOf(Genre("", "", "")))
    }

}