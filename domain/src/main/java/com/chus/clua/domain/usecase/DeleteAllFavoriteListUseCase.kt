package com.chus.clua.domain.usecase

import com.chus.clua.domain.IoDispatcher
import com.chus.clua.domain.repository.MoviesRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DeleteAllFavoriteListUseCase @Inject constructor(
    private val repository: MoviesRepository,
    @IoDispatcher private val dispatcherIO: CoroutineDispatcher
) {
    suspend operator fun invoke(): Unit =
        withContext(dispatcherIO) {
            repository.deleteAll()
        }
}