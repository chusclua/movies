package com.chus.clua.domain.usecase

import com.chus.clua.domain.AppError
import com.chus.clua.domain.Either
import com.chus.clua.domain.getErrorOrNull
import com.chus.clua.domain.getOrNull
import com.chus.clua.domain.isLeft
import com.chus.clua.domain.isRight
import com.chus.clua.domain.models.PersonCredits
import com.chus.clua.domain.models.PersonMovieCast
import com.chus.clua.domain.models.PersonMovieCrew
import com.chus.clua.domain.repository.PersonRepository
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
class GetPersonMovieCreditsUseCaseTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var repository: PersonRepository

    private val dispatcher = UnconfinedTestDispatcher()

    private lateinit var useCase: GetPersonMovieCreditsUseCase

    private val personCredits = PersonCredits.copy(
        cast = (0..19).map {
            if (Random.nextBoolean()) return@map PersonMovieCast.copy(posterPath = null)
            PersonMovieCast
        },
        crew = (0..19).map {
            if (Random.nextBoolean()) return@map PersonMovieCrew.copy(posterPath = null)
            PersonMovieCrew
        }
    )

    @Before
    fun setUp() {
        useCase = GetPersonMovieCreditsUseCase(repository, dispatcher)
    }

    @Test
    fun `when GetPersonMovieCreditsUseCase is invoked then obtains a PersonCredits`() = runTest {
        coEvery { repository.getPersonMovieCredits(any()) } returns Either.Right(personCredits)

        val either = useCase.invoke(3084)

        assertTrue(either.isRight)
        assertNull(either.getErrorOrNull())
        assertTrue(either.getOrNull()?.cast?.none { it.posterPath.isNullOrEmpty() } == true)
        assertTrue(either.getOrNull()?.crew?.none { it.posterPath.isNullOrEmpty() } == true)
    }

    @Test
    fun `when GetPersonMovieCreditsUseCase is invoked then obtains an error`() = runTest {
        coEvery { repository.getPersonMovieCredits(any()) } returns
            Either.Left(AppError.HttpError(404, "not found"))

        val either = useCase.invoke(3084)

        assertTrue(either.isLeft)
        assertNull(either.getOrNull())
        assertThat(
            either.getErrorOrNull(),
            IsInstanceOf.instanceOf(AppError.HttpError::class.java)
        )
    }
}