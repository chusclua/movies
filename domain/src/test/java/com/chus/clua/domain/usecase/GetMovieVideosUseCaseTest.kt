package com.chus.clua.domain.usecase

import com.chus.clua.domain.AppError
import com.chus.clua.domain.Either
import com.chus.clua.domain.getErrorOrNull
import com.chus.clua.domain.getOrNull
import com.chus.clua.domain.isLeft
import com.chus.clua.domain.isRight
import com.chus.clua.domain.model.MovieVideo
import com.chus.clua.domain.models.MovieVideo
import com.chus.clua.domain.models.MovieVideos
import com.chus.clua.domain.repository.MoviesRepository
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlin.random.Random
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

@OptIn(ExperimentalCoroutinesApi::class)
class GetMovieVideosUseCaseTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var repository: MoviesRepository

    private val dispatcher = UnconfinedTestDispatcher()

    private lateinit var useCase: GetMovieVideosUseCase

    private val videos: List<MovieVideo> = (0..19).map {
        if (Random.nextBoolean()) return@map MovieVideo.copy(site = "Vimeo")
        MovieVideo
    }

    @Before
    fun setUp() {
        useCase = GetMovieVideosUseCase(repository, dispatcher)
    }

    @Test
    fun `when GetMovieVideosUseCase is invoked then obtains a Youtube MovieVideos`() = runTest {
        coEvery { repository.getMovieVideos(any()) } returns
            Either.Right(MovieVideos.copy(videos = videos))

        val either = useCase.invoke(238)

        assertTrue(either.isRight)
        assertNull(either.getErrorOrNull())
        assertTrue(
            either.getOrNull()?.videos?.all { video ->
                video.site.equals("youtube", ignoreCase = true)
            } == true
        )
    }

    @Test
    fun `when GetMovieVideosUseCase is invoked then obtains an error`() = runTest {
        coEvery { repository.getMovieVideos(any()) } returns
            Either.Left(AppError.HttpError(404, "not found"))

        val either = useCase.invoke(238)

        assertTrue(either.isLeft)
        assertNull(either.getOrNull())
        assertThat(either.getErrorOrNull(), IsInstanceOf.instanceOf(AppError.HttpError::class.java))
    }
}
