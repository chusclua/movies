package com.chus.clua.domain.usecase

import com.chus.clua.domain.AppError
import com.chus.clua.domain.Either
import com.chus.clua.domain.getErrorOrNull
import com.chus.clua.domain.getOrNull
import com.chus.clua.domain.isLeft
import com.chus.clua.domain.isRight
import com.chus.clua.domain.models.MovieCredits
import com.chus.clua.domain.models.MovieDataDetail
import com.chus.clua.domain.models.MovieDetail
import com.chus.clua.domain.models.MovieVideos
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
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class GetMovieDetailUseCaseTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var isFavoriteUseCase: IsFavoriteUseCase

    @MockK
    lateinit var movieDataUseCase: GetMovieDataUseCase

    @MockK
    lateinit var movieCreditsUseCase: GetMovieCreditsUseCase

    @MockK
    lateinit var movieVideosUseCase: GetMovieVideosUseCase

    private val dispatcher = UnconfinedTestDispatcher()

    private lateinit var useCase: GetMovieDetailUseCase

    @Before
    fun setUp() {
        useCase = GetMovieDetailUseCase(
            isFavoriteUseCase,
            movieDataUseCase,
            movieCreditsUseCase,
            movieVideosUseCase,
            dispatcher
        )
        coEvery { isFavoriteUseCase.invoke(any()) } returns true
        coEvery { movieCreditsUseCase.invoke(any()) } returns Either.Right(MovieCredits)
        coEvery { movieVideosUseCase.invoke(any()) } returns Either.Right(MovieVideos)
    }

    @Test
    fun nothing() = runTest {
        coEvery { movieDataUseCase.invoke(any()) } returns Either.Right(MovieDataDetail)

        val either = useCase.invoke(238)

        assertTrue(either.isRight)
        assertNull(either.getErrorOrNull())
        assertEquals(MovieDetail, either.getOrNull())
    }

    @Test
    fun nothing2() = runTest {
        coEvery { movieDataUseCase.invoke(any()) } returns
            Either.Left(AppError.HttpError(404, "not found"))

        val either = useCase.invoke(238)

        assertTrue(either.isLeft)
        assertNull(either.getOrNull())
        assertThat(
            either.getErrorOrNull(),
            IsInstanceOf.instanceOf(AppError.InsufficientData::class.java)
        )
    }
}