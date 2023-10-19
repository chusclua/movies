package com.chus.clua.domain.usecase

import com.chus.clua.domain.AppError
import com.chus.clua.domain.Either
import com.chus.clua.domain.getErrorOrNull
import com.chus.clua.domain.getOrNull
import com.chus.clua.domain.isLeft
import com.chus.clua.domain.isRight
import com.chus.clua.domain.models.MovieDetail
import com.chus.clua.domain.repository.MoviesRepository
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsInstanceOf
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class GetMovieDataUseCaseTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var repository: MoviesRepository

    private lateinit var useCase: GetMovieDataUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        useCase = GetMovieDataUseCase(repository, UnconfinedTestDispatcher())
    }

    @Test
    fun nothing() = runTest {
        coEvery { repository.getMovieDetail(any()) } returns Either.Right(MovieDetail)

        val either = useCase.invoke(238)

        assert(either.isRight)
        assertNull(either.getErrorOrNull())
        assertEquals(MovieDetail, either.getOrNull())
    }

    @Test
    fun nothing2() = runTest {
        coEvery { repository.getMovieDetail(any()) } returns
            Either.Right(MovieDetail.copy(backdropPath = null))

        val either = useCase.invoke(238)

        assert(either.isLeft)
        assertNull(either.getOrNull())
        assertThat(
            either.getErrorOrNull(),
            IsInstanceOf.instanceOf(AppError.InsufficientData::class.java)
        )
    }

    @Test
    fun nothing3() = runTest {
        coEvery { repository.getMovieDetail(any()) } returns
            Either.Left(AppError.HttpError(404, "not found"))

        val either = useCase.invoke(238)

        assert(either.isLeft)
        assertNull(either.getOrNull())
        assertThat(either.getErrorOrNull(), IsInstanceOf.instanceOf(AppError.HttpError::class.java))
    }
}