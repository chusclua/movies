package com.chus.clua.domain.usecase

import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.chus.clua.domain.models.MovieList
import com.chus.clua.domain.repository.MoviesRepository
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetDiscoverMoviesUseCaseTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var repository: MoviesRepository

    private val dispatcher = UnconfinedTestDispatcher()

    private lateinit var useCase: GetDiscoverMoviesUseCase

    @Before
    fun setUp() {
        useCase = GetDiscoverMoviesUseCase(repository, dispatcher)
    }

    @Test
    fun `when GetDiscoverMoviesUseCase then obtains a list of Movie`() = runTest {
        every { repository.getDiscoverMovies() } returns flow {
            emit(PagingData.from(MovieList))
        }

        val movies = useCase.invoke().asSnapshot { }

        assert(movies.none { it.posterPath.isNullOrEmpty() || it.backdropPath.isNullOrEmpty() })
    }
}
