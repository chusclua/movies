package com.chus.clua.domain.usecase

import com.chus.clua.domain.AppError
import com.chus.clua.domain.Either
import com.chus.clua.domain.getErrorOrNull
import com.chus.clua.domain.getOrNull
import com.chus.clua.domain.isLeft
import com.chus.clua.domain.isRight
import com.chus.clua.domain.models.PersonCredits
import com.chus.clua.domain.models.PersonDataDetail
import com.chus.clua.domain.models.PersonDetail
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
class GetPersonDetailUseCaseTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var personDataDetailUseCase: GetPersonDataDetailUseCase

    @MockK
    lateinit var personMovieCreditsUseCase: GetPersonMovieCreditsUseCase

    private val dispatcher = UnconfinedTestDispatcher()

    private lateinit var useCase: GetPersonDetailUseCase

    @Before
    fun setUp() {
        useCase = GetPersonDetailUseCase(
            personDataDetailUseCase,
            personMovieCreditsUseCase,
            dispatcher
        )
        coEvery { personMovieCreditsUseCase.invoke(any()) } returns Either.Right(PersonCredits)
    }

    @Test
    fun `when GetPersonDetailUseCase is invoked then obtains a PersonDetail`() = runTest {
        coEvery { personDataDetailUseCase.invoke(any()) } returns Either.Right(PersonDataDetail)

        val either = useCase.invoke(3084)

        assertTrue(either.isRight)
        assertNull(either.getErrorOrNull())
        assertEquals(PersonDetail, either.getOrNull())
    }

    @Test
    fun `when GetPersonDetailUseCase is invoked and MovieDataDetail is null then obtains an error`() =
        runTest {
            coEvery { personDataDetailUseCase.invoke(any()) } returns
                Either.Left(AppError.HttpError(404, "not found"))

            val either = useCase.invoke(3084)

            assertTrue(either.isLeft)
            assertNull(either.getOrNull())
            assertThat(
                either.getErrorOrNull(),
                IsInstanceOf.instanceOf(AppError.InsufficientData::class.java)
            )
        }
}