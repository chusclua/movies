package com.chus.clua.data.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.chus.clua.data.network.model.MovieResponseModel
import com.chus.clua.data.paging.MoviesPagingSource
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow


interface PagerDataSource {
    fun pager(): Flow<PagingData<MovieResponseModel>>
}

@Singleton
class PagerDataSourceImp @Inject constructor(
    private val pagingSource: MoviesPagingSource
): PagerDataSource {
    override fun pager(): Flow<PagingData<MovieResponseModel>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { pagingSource }
        ).flow
    }

}