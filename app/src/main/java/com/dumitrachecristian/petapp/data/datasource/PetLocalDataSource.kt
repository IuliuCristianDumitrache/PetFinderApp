package com.dumitrachecristian.petapp.data.datasource

import com.dumitrachecristian.petapp.data.PetEntity
import com.dumitrachecristian.petapp.data.PetEntityDao
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

    suspend fun remove(id: Int) {
        petEntityDao.remove(id)
    }

    fun getFavoritePets(): Flow<List<PetEntity>> {
        return petEntityDao.getFavoritePets()
    }

    fun getFavoritesIds(): List<Int> {
        return petEntityDao.getFavoritesIds()
    }
}