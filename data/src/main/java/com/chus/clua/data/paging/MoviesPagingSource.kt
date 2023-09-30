package com.chus.clua.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chus.clua.data.datasource.RemoteDataSource
import com.chus.clua.data.network.model.MovieResponseModel
import com.chus.clua.domain.onRight

class MoviesPagingSource(
    private val dataSource: RemoteDataSource,
): PagingSource<Int, MovieResponseModel>() {
    override fun getRefreshKey(state: PagingState<Int, MovieResponseModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponseModel> {
        return try {
            val page = params.key ?: 1

            val articles: MutableList<MovieResponseModel> = mutableListOf()
            dataSource.getDiscoverMovies(page = page).onRight { data ->
                articles.addAll(data.results)
            }

            LoadResult.Page(
                data = articles,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (articles.isEmpty()) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}