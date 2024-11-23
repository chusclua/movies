package com.chus.clua.domain.usecase

import com.chus.clua.domain.IoDispatcher
import com.chus.clua.domain.repository.MoviesRepository
import dagger.Reusable
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

@Reusable
class DeleteAllFavoriteListUseCase @Inject constructor(
    private val repository: MoviesRepository,
    @IoDispatcher private val dispatcherIO: CoroutineDispatcher
) {
    suspend operator fun invoke(): Unit =
        withContext(dispatcherIO) {
            repository.deleteAll()
        }
}
