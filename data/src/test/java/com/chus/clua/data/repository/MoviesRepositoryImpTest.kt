package com.chus.clua.data.repository

import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.chus.clua.data.datasource.MovieCacheDataSource
import com.chus.clua.data.datasource.MoviePagerDataSource
import com.chus.clua.data.datasource.MovieRemoteDataSource
import com.chus.clua.data.db.MoviesDao
import com.chus.clua.data.models.movie.Movie
import com.chus.clua.data.models.movie.MovieApiList
import com.chus.clua.data.models.movie.MovieCredits
import com.chus.clua.data.models.movie.MovieCreditsApi
import com.chus.clua.data.models.movie.MovieDetail
import com.chus.clua.data.models.movie.MovieDetailApi
import com.chus.clua.data.models.movie.MovieEntities
import com.chus.clua.data.models.movie.MovieVideos
import com.chus.clua.data.models.movie.MovieVideosApi
import com.chus.clua.data.models.movie.MoviesApi
import com.chus.clua.domain.AppError
import com.chus.clua.domain.Either
import com.chus.clua.domain.getErrorOrNull
import com.chus.clua.domain.getOrNull
import com.chus.clua.domain.isLeft
import com.chus.clua.domain.isRight
import com.chus.clua.domain.repository.MoviesRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.just
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsInstanceOf.instanceOf
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MoviesRepositoryImpTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var moviePagerDataSource: MoviePagerDataSource

    @MockK
    lateinit var movieRemoteDataSource: MovieRemoteDataSource

    @MockK
    lateinit var movieCacheDataSource: MovieCacheDataSource

    @MockK
    lateinit var moviesDao: MoviesDao

    private lateinit var repository: MoviesRepository

    @Before
    fun setUp() {
        repository = MoviesRepositoryImp(
            moviePagerDataSource,
            movieRemoteDataSource,
            movieCacheDataSource,
            moviesDao
        )
    }

    @Test
    fun `when repository getDiscoverMovies then it has a valid PagingData`() = runTest {
        every { movieCacheDataSource.addMovie(any()) } just Runs
        every { moviePagerDataSource.getMoviePage() } returns flow {
            emit(PagingData.from(MovieApiList))
        }

        val movies = repository.getDiscoverMovies().asSnapshot { }

        assertEquals(20, movies.size)
        assertEquals(Movie.copy(id = 1), movies.first())
    }

    @Test
    fun `when repository getTopRatedMovies then it has a valid PagingData`() = runTest {
        every { movieCacheDataSource.addMovie(any()) } just Runs
        every { moviePagerDataSource.getTopRatedMoviePage() } returns flow {
            emit(PagingData.from(MovieApiList))
        }

        val movies = repository.getTopRatedMovies().asSnapshot { }

        assertEquals(20, movies.size)
        assertEquals(Movie.copy(id = 1), movies.first())
    }

    @Test
    fun `when repository searchMovies then it has a valid list of Movie`() = runTest {
        every { movieCacheDataSource.addMovie(any()) } just Runs
        coEvery { movieRemoteDataSource.searchMovies(any()) } returns Either.Right(MoviesApi)

        val either = repository.searchMovies("godfather")

        assertTrue(either.isRight)
        assertNull(either.getErrorOrNull())
        assertEquals(20, either.getOrNull()?.size)
        assertEquals(Movie.copy(id = 1), either.getOrNull()?.first())
    }

    @Test
    fun `when repository searchMovies then it has an error`() = runTest {
        every { movieCacheDataSource.addMovie(any()) } just Runs
        coEvery { movieRemoteDataSource.searchMovies(any()) } returns
            Either.Left(
                AppError.HttpError(500, "Internal server error")
            )

        val either = repository.searchMovies("godfather")

        assertTrue(either.isLeft)
        assertNull(either.getOrNull())
        assertThat(either.getErrorOrNull(), instanceOf(AppError.HttpError::class.java))
    }

    @Test
    fun `when repository getMovieDetail then it has a MovieDataDetail`() = runTest {
        coEvery { movieRemoteDataSource.getMovieDetail(any()) } returns
            Either.Right(MovieDetailApi)

        val either = repository.getMovieDetail(238)

        assertTrue(either.isRight)
        assertNull(either.getErrorOrNull())
        assertEquals(MovieDetail, either.getOrNull())
    }

    @Test
    fun `when repository getMovieDetail then it has an error`() = runTest {
        coEvery { movieRemoteDataSource.getMovieDetail(any()) } returns
            Either.Left(
                AppError.HttpError(500, "Internal server error")
            )

        val either = repository.getMovieDetail(238)

        assertTrue(either.isLeft)
        assertNull(either.getOrNull())
        assertThat(either.getErrorOrNull(), instanceOf(AppError.HttpError::class.java))
    }

    @Test
    fun `when repository getMovieCredits then it has a MovieCredits`() = runTest {
        coEvery { movieRemoteDataSource.getMovieCredits(any()) } returns
            Either.Right(MovieCreditsApi)

        val either = repository.getMovieCredits(238)

        assertTrue(either.isRight)
        assertNull(either.getErrorOrNull())
        assertEquals(MovieCredits, either.getOrNull())
    }

    @Test
    fun `when repository getMovieCredits then it has an error`() = runTest {
        coEvery { movieRemoteDataSource.getMovieCredits(any()) } returns
            Either.Left(
                AppError.HttpError(500, "Internal server error")
            )

        val either = repository.getMovieCredits(238)

        assertTrue(either.isLeft)
        assertNull(either.getOrNull())
        assertThat(either.getErrorOrNull(), instanceOf(AppError.HttpError::class.java))
    }

    @Test
    fun `when repository getMovieVideos then it has a MovieVideos`() = runTest {
        coEvery { movieRemoteDataSource.getMovieVideos(any()) } returns
            Either.Right(MovieVideosApi)

        val either = repository.getMovieVideos(238)

        assertTrue(either.isRight)
        assertNull(either.getErrorOrNull())
        assertEquals(MovieVideos, either.getOrNull())
    }

    @Test
    fun `when repository getMovieVideos then it has an error`() = runTest {
        coEvery { movieRemoteDataSource.getMovieVideos(any()) } returns
            Either.Left(
                AppError.HttpError(500, "Internal server error")
            )

        val either = repository.getMovieVideos(238)

        assertTrue(either.isLeft)
        assertNull(either.getOrNull())
        assertThat(either.getErrorOrNull(), instanceOf(AppError.HttpError::class.java))
    }

    @Test
    fun `when repository getFavorites then it has a valid List of Movie`() = runTest {
        every { moviesDao.getAll() } returns flow {
            emit(MovieEntities)
        }

        val movies = repository.getFavorites().first()

        assertEquals(20, movies.size)
        assertEquals(Movie, movies.first())
    }

}