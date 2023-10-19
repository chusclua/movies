package com.chus.clua.data.repository

import com.chus.clua.data.datasource.PersonRemoteDataSource
import com.chus.clua.data.models.person.PersonCredits
import com.chus.clua.data.models.person.PersonMovieCreditsApi
import com.chus.clua.data.models.person.PersonDataDetail
import com.chus.clua.data.models.person.PersonDetailApiModel
import com.chus.clua.domain.AppError
import com.chus.clua.domain.Either
import com.chus.clua.domain.getErrorOrNull
import com.chus.clua.domain.getOrNull
import com.chus.clua.domain.isLeft
import com.chus.clua.domain.isRight
import com.chus.clua.domain.repository.PersonRepository
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsInstanceOf
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class PersonRepositoryImpTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var dataSource: PersonRemoteDataSource

    private lateinit var repository: PersonRepository

    @Before
    fun setUp() {
        repository = PersonRepositoryImp(dataSource)
    }

    @Test
    fun `when repository getPersonDataDetail then obtains a PersonDataDetail`() = runTest {
        coEvery { dataSource.getPersonDataDetail(any()) } returns
            Either.Right(PersonDetailApiModel)

        val either = repository.getPersonDataDetail(3084)

        assertTrue(either.isRight)
        assertNull(either.getErrorOrNull())
        assertEquals(PersonDataDetail, either.getOrNull())
    }

    @Test
    fun `when repository getPersonDataDetail then obtains an error`() = runTest {
        coEvery { dataSource.getPersonDataDetail(any()) } returns
            Either.Left(AppError.HttpError(404, "not found"))

        val either = repository.getPersonDataDetail(3084)

        assertTrue(either.isLeft)
        assertNull(either.getOrNull())
        assertThat(either.getErrorOrNull(), IsInstanceOf.instanceOf(AppError.HttpError::class.java))
    }

    @Test
    fun `when repository getPersonMovieCredits then obtains a PersonCredits`() = runTest {
        coEvery { dataSource.getPersonMovieCredits(any()) } returns
            Either.Right(PersonMovieCreditsApi)

        val either = repository.getPersonMovieCredits(3084)

        assertTrue(either.isRight)
        assertNull(either.getErrorOrNull())
        assertEquals(PersonCredits, either.getOrNull())
    }

    @Test
    fun `when repository getPersonMovieCredits then obtains an error`() = runTest {
        coEvery { dataSource.getPersonMovieCredits(any()) } returns
            Either.Left(AppError.HttpError(404, "not found"))

        val either = repository.getPersonMovieCredits(3084)

        assertTrue(either.isLeft)
        assertNull(either.getOrNull())
        assertThat(either.getErrorOrNull(), IsInstanceOf.instanceOf(AppError.HttpError::class.java))
    }
}