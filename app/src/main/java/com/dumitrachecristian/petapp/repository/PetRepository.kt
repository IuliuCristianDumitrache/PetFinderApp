package com.dumitrachecristian.petapp.repository

import com.dumitrachecristian.petapp.data.PetEntity
import com.dumitrachecristian.petapp.data.datasource.PetLocalDataSource
import com.dumitrachecristian.petapp.data.datasource.PetRemoteDataSource
import com.dumitrachecristian.petapp.model.PetModelResponse
import com.dumitrachecristian.petapp.model.SelectedFilter
import com.dumitrachecristian.petapp.model.TypeResponse
import com.dumitrachecristian.petapp.network.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PetRepository @Inject constructor(
    private val petLocalDataSource: PetLocalDataSource,
    private val petRemoteDataSource: PetRemoteDataSource
) {

    suspend fun getPets(page: Int, limit: Int, filter: SelectedFilter): Result<PetModelResponse> {
        return petRemoteDataSource.getPets(page, limit, filter)
    }

    suspend fun getFilters(): Result<TypeResponse> {
        return petRemoteDataSource.getFilters()
    }

    suspend fun insertAnimal(petEntity: PetEntity) {
        petLocalDataSource.insert(petEntity)
    }

    fun getPetsFromDb(): Flow<List<PetEntity>> {
        return petLocalDataSource.getFavoritePets()
    }

    suspend fun removePet(id: Int) {
        petLocalDataSource.remove(id)
    }

    suspend fun getFavoritesIds(): List<Int> {
        return petLocalDataSource.getFavoritesIds()
    }
}