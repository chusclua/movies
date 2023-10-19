package com.chus.clua.domain.usecase

import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.chus.clua.domain.model.Movie
import com.chus.clua.domain.models.Movie
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
import kotlin.random.Random


class GetDiscoverMoviesUseCaseTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var repository: MoviesRepository

    private lateinit var useCase: GetDiscoverMoviesUseCase

    private val MovieList: List<Movie> = (1..20).map { index ->
        val movie = Movie.copy(id = index)
        if (Random.nextBoolean()) return@map movie.copy(backdropPath = null, posterPath = null)
        movie
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        useCase = GetDiscoverMoviesUseCase(repository, UnconfinedTestDispatcher())
    }

    @Test
    fun `when GetDiscoverMoviesUseCase is invoked then obtains a list of Movie with posterPath`() = runTest {
        every { repository.getDiscoverMovies() } returns flow {
            emit(PagingData.from(MovieList))
        }

        val movies = useCase.invoke().asSnapshot {  }

        assert(movies.none { it.posterPath.isNullOrEmpty() || it.backdropPath.isNullOrEmpty() })
    }
}