package com.chus.clua.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chus.clua.data.datasource.MovieRemoteDataSource
import com.chus.clua.data.network.model.MovieApiModel
import com.chus.clua.domain.getOrNull
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TopRatedMoviesPagingSource @Inject constructor(
    private val dataSource: MovieRemoteDataSource,
) : PagingSource<Int, MovieApiModel>() {
    override fun getRefreshKey(state: PagingState<Int, MovieApiModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieApiModel> {
        return try {
            val page = params.key ?: 1

            val apiModels: List<MovieApiModel> =
            dataSource.getTopRatedMovies(page = page).getOrNull()?.results.orEmpty()

            LoadResult.Page(
                data = apiModels,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (apiModels.isEmpty()) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}