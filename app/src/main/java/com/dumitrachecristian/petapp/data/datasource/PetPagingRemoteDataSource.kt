package com.dumitrachecristian.petapp.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dumitrachecristian.petapp.data.SessionManager
import com.dumitrachecristian.petapp.model.AnimalDto
import com.dumitrachecristian.petapp.repository.PetRepository
import com.dumitrachecristian.petapp.network.Result


class PetPagingRemoteDataSource(
    private val repository: PetRepository,
    private val sessionManager: SessionManager
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
                "Bearer ${sessionManager.getToken()}", page = page,
                limit = 10
            )
            when (result) {
                is Result.Success -> {
                    result.data?.let { response ->
                        return LoadResult.Page(
                            data = response.pets,
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