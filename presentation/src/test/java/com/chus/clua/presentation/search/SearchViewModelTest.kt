package com.chus.clua.presentation.search

import app.cash.turbine.test
import com.chus.clua.data.network.api.fake.FakeMovieApiImp
import com.chus.clua.domain.usecase.SearchMoviesUseCase
import com.chus.clua.presentation.base.BaseViewModel
import com.chus.clua.presentation.model.Movies
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import javax.inject.Inject
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(RobolectricTestRunner::class)
class SearchViewModelTest : BaseViewModel() {

    @Inject
    lateinit var searchMoviesUseCase: SearchMoviesUseCase

    @Inject
    lateinit var fakeMovieApiImp: FakeMovieApiImp

    private lateinit var viewModel: SearchViewModel

    @Before
    fun before() {
        super.setUp()
        viewModel = SearchViewModel(searchMoviesUseCase)
    }

    @After
    fun after() {
        super.tearDown()
        
    }

    @Test
    fun `when viewModel is initialized then it has a valid SearchState`() =
        runTest {
            viewModel.searchState.test {
                assertEquals(
                    SearchState(movies = emptyList(), empty = true, error = false), awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when viewModel search gets an Either Right then it has a valid SearchState`() =
        runTest {

            viewModel.searchState.test {

                viewModel.search("padrino")

                assertEquals(
                    SearchState(movies = emptyList(), empty = true, error = false), awaitItem()
                )

                assertEquals(
                    SearchState(movies = Movies, empty = false, error = false), awaitItem()
                )

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when viewModel search minor of 3 characters then previous job is canceled`() =
        runTest {
            viewModel.searchState.test {

                viewModel.search("padrino")
                viewModel.search("pa")

                assertEquals(
                    SearchState(movies = emptyList(), empty = true, error = false), awaitItem()
                )

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when viewModel search gets an Either Left then it has a valid SearchState`() =
        runTest {

            fakeMovieApiImp.forceLeft()
            viewModel = SearchViewModel(searchMoviesUseCase)

            viewModel.searchState.test {

                viewModel.search("padrino")

                assertEquals(
                    SearchState(movies = emptyList(), empty = true, error = false), awaitItem()
                )

                assertEquals(
                    SearchState(movies = emptyList(), empty = true, error = true), awaitItem()
                )

                cancelAndIgnoreRemainingEvents()
            }
        }
}