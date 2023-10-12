package com.chus.clua.data.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.chus.clua.data.network.model.MovieApiModel
import com.chus.clua.data.paging.MoviesPagingSource
import com.chus.clua.data.paging.TopRatedMoviesPagingSource
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow


interface MoviePagerDataSource {
    fun getMoviePage(): Flow<PagingData<MovieApiModel>>
    fun getTopRatedMoviePage(): Flow<PagingData<MovieApiModel>>
}

@Singleton
class MoviePagerDataSourceImp @Inject constructor(
    private val pagingSource: MoviesPagingSource,
    private val topRatedMoviesPagingSource: TopRatedMoviesPagingSource
): MoviePagerDataSource {
    override fun getMoviePage(): Flow<PagingData<MovieApiModel>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { pagingSource }
        ).flow
    }

    override fun getTopRatedMoviePage(): Flow<PagingData<MovieApiModel>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { topRatedMoviesPagingSource }
        ).flow
    }
}