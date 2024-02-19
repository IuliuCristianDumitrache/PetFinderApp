package com.dumitrachecristian.petapp.data.datasource

import com.dumitrachecristian.petapp.model.PetModelResponse
import com.dumitrachecristian.petapp.model.SelectedFilter
import com.dumitrachecristian.petapp.model.TypeResponse
import com.dumitrachecristian.petapp.network.ApiService
import com.dumitrachecristian.petapp.network.Result
import com.dumitrachecristian.petapp.ui.mainscreen.Filter
import javax.inject.Inject

class PetRemoteDataSource @Inject constructor(
    private val apiService: ApiService,
): BaseRemoteDataSource {

    suspend fun getPets(page: Int, limit: Int, filter: SelectedFilter): Result<PetModelResponse> {
        return safeApiResult {
            apiService.getPets(
                page,
                limit,
                type = if (filter.animalType != Filter.ALL) filter.animalType else null,
                gender = if (filter.gender != Filter.ALL) filter.gender else null,
                location = if (filter.currentLocationLabel != Filter.EVERYWHERE) filter.currentLocationValue else null,
                distance = if (filter.currentLocationLabel != Filter.EVERYWHERE) 500 else null
            )
        }
    }

    suspend fun getFilters(): Result<TypeResponse> {
        return safeApiResult { apiService.getFilters() }
    }
}