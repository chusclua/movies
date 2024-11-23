package com.chus.clua.domain.usecase

import com.chus.clua.domain.AppError
import com.chus.clua.domain.Either
import com.chus.clua.domain.getErrorOrNull
import com.chus.clua.domain.getOrNull
import com.chus.clua.domain.isLeft
import com.chus.clua.domain.isRight
import com.chus.clua.domain.models.MovieList
import com.chus.clua.domain.repository.MoviesRepository
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsInstanceOf
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchMoviesUseCaseTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var repository: MoviesRepository

    private val dispatcher = UnconfinedTestDispatcher()

    private lateinit var useCase: SearchMoviesUseCase

    @Before
    fun setUp() {
        useCase = SearchMoviesUseCase(repository, dispatcher)
    }

    @Test
    fun `when SearchMoviesUseCase is invoked then obtains a valid MovieList`() = runTest {
        coEvery { repository.searchMovies(any()) } returns Either.Right(MovieList)

        val either = useCase.invoke("query")

        assertTrue(either.isRight)
        assertNull(either.getErrorOrNull())
        assert(
            either.getOrNull()?.none {
                it.posterPath.isNullOrEmpty() ||
                    it.backdropPath.isNullOrEmpty()
            } == true
        )
    }

    @Test
    fun `when SearchMoviesUseCase is invoked then obtains an error`() = runTest {
        coEvery { repository.searchMovies(any()) } returns
            Either.Left(AppError.HttpError(404, "not found"))

        val either = useCase.invoke("query")

        assertTrue(either.isLeft)
        assertNull(either.getOrNull())
        assertThat(either.getErrorOrNull(), IsInstanceOf.instanceOf(AppError.HttpError::class.java))
    }
}
