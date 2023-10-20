package com.chus.clua.domain.usecase

import com.chus.clua.domain.AppError
import com.chus.clua.domain.Either
import com.chus.clua.domain.getErrorOrNull
import com.chus.clua.domain.getOrNull
import com.chus.clua.domain.isLeft
import com.chus.clua.domain.isRight
import com.chus.clua.domain.models.MovieDataDetail
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

@OptIn(ExperimentalCoroutinesApi::class)
class GetMovieDataUseCaseTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var repository: MoviesRepository

    private val dispatcher = UnconfinedTestDispatcher()

    private lateinit var useCase: GetMovieDataUseCase

    @Before
    fun setUp() {
        useCase = GetMovieDataUseCase(repository, dispatcher)
    }

    @Test
    fun `when GetMovieDataUseCase is invoked then obtains a MovieDataDetail`() = runTest {
        coEvery { repository.getMovieDetail(any()) } returns Either.Right(MovieDataDetail)

        val either = useCase.invoke(238)

        assert(either.isRight)
        assertNull(either.getErrorOrNull())
        assertEquals(MovieDataDetail, either.getOrNull())
    }

    @Test
    fun `when GetMovieDataUseCase is invoked and repository returns a MovieDataDetail with null backdropPath then obtains an error`() =
        runTest {
            coEvery { repository.getMovieDetail(any()) } returns
                Either.Right(MovieDataDetail.copy(backdropPath = null))

            val either = useCase.invoke(238)

            assert(either.isLeft)
            assertNull(either.getOrNull())
            assertThat(
                either.getErrorOrNull(),
                IsInstanceOf.instanceOf(AppError.InsufficientData::class.java)
            )
        }

    @Test
    fun `when GetMovieDataUseCase is invoked then obtains an error`() = runTest {
        coEvery { repository.getMovieDetail(any()) } returns
            Either.Left(AppError.HttpError(404, "not found"))

        val either = useCase.invoke(238)

        assert(either.isLeft)
        assertNull(either.getOrNull())
        assertThat(either.getErrorOrNull(), IsInstanceOf.instanceOf(AppError.HttpError::class.java))
    }
}