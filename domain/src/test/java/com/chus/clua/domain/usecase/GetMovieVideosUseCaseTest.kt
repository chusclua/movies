package com.chus.clua.domain.usecase

import com.chus.clua.domain.AppError
import com.chus.clua.domain.Either
import com.chus.clua.domain.getErrorOrNull
import com.chus.clua.domain.getOrNull
import com.chus.clua.domain.isLeft
import com.chus.clua.domain.isRight
import com.chus.clua.domain.model.Movie
import com.chus.clua.domain.model.MovieVideo
import com.chus.clua.domain.models.Movie
import com.chus.clua.domain.models.MovieVideo
import com.chus.clua.domain.repository.MoviesRepository
import com.chus.clua.domain.models.MovieVideos
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsInstanceOf
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random

@OptIn(ExperimentalCoroutinesApi::class)
class GetMovieVideosUseCaseTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var repository: MoviesRepository

    private val dispatcher = UnconfinedTestDispatcher()

    lateinit var useCase: GetMovieVideosUseCase

    private val videos: List<MovieVideo> = (0..19).map {
        if (Random.nextBoolean()) return@map MovieVideo.copy(site = "Vimeo")
        MovieVideo
    }

    @Before
    fun setUp() {
        useCase = GetMovieVideosUseCase(repository, dispatcher)
    }

    @Test
    fun nothing() = runTest {
        coEvery { repository.getMovieVideos(any()) } returns Either.Right(MovieVideos.copy(videos = videos))

        val either = useCase.invoke(238)

        assertTrue(either.isRight)
        assertNull(either.getErrorOrNull())
        assertTrue(either.getOrNull()?.videos?.all {
            it.site.equals(
                "youtube",
                ignoreCase = true
            )
        } == true)
    }

    @Test
    fun nothing2() = runTest {
        coEvery { repository.getMovieVideos(any()) } returns
            Either.Left(AppError.HttpError(404, "not found"))

        val either = useCase.invoke(238)

        assertTrue(either.isLeft)
        assertNull(either.getOrNull())
        assertThat(either.getErrorOrNull(), IsInstanceOf.instanceOf(AppError.HttpError::class.java))
    }
}