package com.dumitrachecristian.petapp.repository

import androidx.paging.PagingSource
import com.dumitrachecristian.petapp.data.PetEntity
import com.dumitrachecristian.petapp.data.datasource.PetLocalDataSource
import com.dumitrachecristian.petapp.data.datasource.PetRemoteDataSource
import com.dumitrachecristian.petapp.model.AccessTokenResponse
import com.dumitrachecristian.petapp.model.Animal
import com.dumitrachecristian.petapp.model.PetModelResponse
import com.dumitrachecristian.petapp.network.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PetRepository @Inject constructor(
    private val petLocalDataSource: PetLocalDataSource,
    private val petRemoteDataSource: PetRemoteDataSource
) {

    suspend fun requestToken(): Result<AccessTokenResponse> {
        return petRemoteDataSource.requestToken()
    }
    suspend fun getPets(token: String, page: Int, limit: Int): Result<PetModelResponse> {
        return petRemoteDataSource.getPets(token, page, limit)
    }

    suspend fun insertAnimal(petEntity: PetEntity) {
        petLocalDataSource.insert(petEntity)
    }

    suspend fun getPetsFromDb(): Flow<List<PetEntity>> {
        return petLocalDataSource.getFavoritePets()
    }

    suspend fun removePet(id: String) {
        petLocalDataSource.remove(id)
    }

    suspend fun paging(): PagingSource<Int, PetEntity> {
        return petLocalDataSource.pagingSource()
    }

    suspend fun upsertAll(pets: List<PetEntity>) {
        return petLocalDataSource.upsertAll(pets)
    }
    fun clearAll() {
        return petLocalDataSource.clearAll()
    }
}