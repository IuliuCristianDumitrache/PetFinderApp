package com.dumitrachecristian.petapp.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dumitrachecristian.petapp.model.AnimalDto
import com.dumitrachecristian.petapp.model.SelectedFilter
import com.dumitrachecristian.petapp.network.Result
import com.dumitrachecristian.petapp.repository.PetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class PetPagingRemoteDataSource(
    private val repository: PetRepository,
    private val filter: SelectedFilter,
): PagingSource<Int, AnimalDto>() {
    override fun getRefreshKey(state: PagingState<Int, AnimalDto>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AnimalDto> {
        return try {
            val page = params.key ?: 1
            val result = repository.getPets(
                page = page,
                limit = params.loadSize,
                filter
            )
            when (result) {
                is Result.Success -> {
                    result.data?.let { response ->
                        val list = response.pets

                        val listFavorite = withContext(Dispatchers.IO) {
                            repository.getFavoritesIds()
                        }
                        list.map {
                            it.isFavorite = listFavorite.contains(it.id)
                            return@map it
                        }

                        return LoadResult.Page(
                            data = list,
                            prevKey = null,
                            nextKey = if (response.pets.isNotEmpty()) response.pagination.currentPage + 1 else null
                        )
                    }
                }

                is Result.Error -> {
                }

                is Result.Loading -> {
                }
            }
            LoadResult.Error(Throwable())
        } catch(e: Exception) {
            LoadResult.Error(e)
        }
    }
}