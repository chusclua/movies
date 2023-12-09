package com.chus.clua.presentation.person_detail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.chus.clua.data.network.api.fake.FakePersonApiImp
import com.chus.clua.domain.usecase.GetPersonDetailUseCase
import com.chus.clua.presentation.base.BaseViewModel
import com.chus.clua.presentation.model.PersonDetail
import com.chus.clua.presentation.model.PersonMovieCast
import com.chus.clua.presentation.model.PersonMovieCrew
import com.chus.clua.presentation.navigation.Screens
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(RobolectricTestRunner::class)
class PersonDetailViewModelTest: BaseViewModel() {

    @Inject
    lateinit var personDetailUseCase: GetPersonDetailUseCase

    @Inject
    lateinit var personApiImp: FakePersonApiImp

    private lateinit var viewModel: PersonDetailViewModel

    @Before
    fun before() {
        super.setUp()
        viewModel = PersonDetailViewModel(
            SavedStateHandle(mapOf(Pair(Screens.PersonDetail.paramId, 3084))),
            personDetailUseCase
        )
    }

    @After
    fun after() {
        super.tearDown()
    }

    @Test
    fun `when viewModel is initialized with an Either Right then it has a valid PersonDetailState`() =
        runTest {
            advanceUntilIdle()
            viewModel.detailState.test {
                assertEquals(
                    PersonDetailState(
                        detail = PersonDetail,
                        cast = listOf(PersonMovieCast),
                        crew = listOf(PersonMovieCrew),
                        error = false
                    ), awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when viewModel is initialized with an Either Left then it has a valid PersonDetailState`() =
        runTest {
            advanceUntilIdle()
            personApiImp.forceLeft()
            viewModel = PersonDetailViewModel(
                SavedStateHandle(mapOf(Pair(Screens.PersonDetail.paramId, 3084))),
                personDetailUseCase
            )
            advanceUntilIdle()
            viewModel.detailState.test {
                assertEquals(
                    PersonDetailState(
                        detail = null,
                        cast = emptyList(),
                        crew = emptyList(),
                        error = true
                    ), awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }
}