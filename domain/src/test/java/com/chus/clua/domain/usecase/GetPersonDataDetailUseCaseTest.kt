package com.chus.clua.domain.usecase

import com.chus.clua.domain.AppError
import com.chus.clua.domain.Either
import com.chus.clua.domain.getErrorOrNull
import com.chus.clua.domain.getOrNull
import com.chus.clua.domain.isLeft
import com.chus.clua.domain.isRight
import com.chus.clua.domain.models.PersonDataDetail
import com.chus.clua.domain.repository.PersonRepository
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
class GetPersonDataDetailUseCaseTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var repository: PersonRepository

    private val dispatcher = UnconfinedTestDispatcher()

    private lateinit var useCase: GetPersonDataDetailUseCase

    @Before
    fun setUp() {
        useCase = GetPersonDataDetailUseCase(repository, dispatcher)
    }

    @Test
    fun `when GetPersonDataDetailUseCase is invoked then obtains a PersonDataDetail`() = runTest {
        coEvery { repository.getPersonDataDetail(any()) } returns Either.Right(PersonDataDetail)

        val either = useCase.invoke(3084)

        assertTrue(either.isRight)
        assertNull(either.getErrorOrNull())
        assertEquals(PersonDataDetail, either.getOrNull())
    }

    @Test
    fun `when GetPersonDataDetailUseCase is invoked and profilePath of PersonDataDetail is null then obtains an error`() = runTest {
        coEvery { repository.getPersonDataDetail(any()) } returns
            Either.Right(PersonDataDetail.copy(profilePath = null))

        val either = useCase.invoke(3084)

        assertTrue(either.isLeft)
        assertNull(either.getOrNull())
        assertThat(
            either.getErrorOrNull(),
            IsInstanceOf.instanceOf(AppError.InsufficientData::class.java)
        )
    }

    @Test
    fun `when GetPersonDataDetailUseCase is invoked then obtains an error`() = runTest {
        coEvery { repository.getPersonDataDetail(any()) } returns
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