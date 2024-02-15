package com.dumitrachecristian.petapp.data.datasource

import androidx.paging.PagingSource
import com.dumitrachecristian.petapp.data.PetEntity
import com.dumitrachecristian.petapp.data.PetEntityDao
import com.dumitrachecristian.petapp.model.Animal
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PetLocalDataSource @Inject constructor(
    private val petEntityDao: PetEntityDao
) {
    suspend fun insert(petEntity: PetEntity) {
        petEntityDao.insert(petEntity)
    }

    suspend fun remove(id: String) {
        petEntityDao.remove(id)
    }

    fun getFavoritePets(): Flow<List<PetEntity>> {
        return petEntityDao.getFavoritePets()
    }

    suspend fun pagingSource(): PagingSource<Int, PetEntity> {
        return petEntityDao.pagingSource()
    }

    suspend fun upsertAll(pets: List<PetEntity>) {
        return petEntityDao.upsertAll(pets)
    }
    fun clearAll() {
        return petEntityDao.clearAll()
    }
}