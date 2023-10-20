package com.chus.clua.domain.usecase

import com.chus.clua.domain.AppError
import com.chus.clua.domain.Either
import com.chus.clua.domain.getErrorOrNull
import com.chus.clua.domain.getOrNull
import com.chus.clua.domain.isLeft
import com.chus.clua.domain.isRight
import com.chus.clua.domain.models.MovieCast
import com.chus.clua.domain.models.MovieCredits
import com.chus.clua.domain.models.MovieCrew
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
import kotlin.random.Random

@OptIn(ExperimentalCoroutinesApi::class)
class GetMovieCreditsUseCaseTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var repository: MoviesRepository

    private val dispatcher = UnconfinedTestDispatcher()

    private lateinit var useCase: GetMovieCreditsUseCase

    private val movieCredits = MovieCredits.copy(
        cast = (0..19).map {
            if (Random.nextBoolean()) return@map MovieCast.copy(profilePath = null)
            MovieCast
        },
        crew = (0..19).map { index ->
            if (index == 10) return@map MovieCrew
            if (Random.nextBoolean()) return@map MovieCrew.copy(profilePath = null)
            MovieCrew.copy(job = "Actor")
        }
    )

    @Before
    fun setUp() {
        useCase = GetMovieCreditsUseCase(repository, dispatcher)
    }

    @Test
    fun `when GetMovieCreditsUseCase is invoked then obtains a MovieCredits with valid cast and crew`() =
        runTest {
            coEvery { repository.getMovieCredits(any()) } returns Either.Right(movieCredits)

            val either = useCase.invoke(238)

            assert(either.isRight)
            assertNull(either.getErrorOrNull())
            assertTrue(either.getOrNull()?.cast?.none { it.profilePath.isNullOrEmpty() } == true)
            assertTrue(either.getOrNull()?.crew?.none { it.profilePath.isNullOrEmpty() } == true)
            assertTrue(either.getOrNull()?.crew?.first()?.job.equals("director", ignoreCase = true))
        }

    @Test
    fun `when GetMovieCreditsUseCase is invoked then obtains an Either Left`() = runTest {
        coEvery { repository.getMovieCredits(any()) } returns
            Either.Left(AppError.HttpError(404, "not found"))

        val either = useCase.invoke(238)

        assert(either.isLeft)
        assertNull(either.getOrNull())
        assertThat(either.getErrorOrNull(), IsInstanceOf.instanceOf(AppError.HttpError::class.java))
    }
}