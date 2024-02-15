package com.dumitrachecristian.petapp.data

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.dumitrachecristian.petapp.model.Animal
import kotlinx.coroutines.flow.Flow

@Dao
interface PetEntityDao {
    @Query("SELECT * FROM PET_ENTITY WHERE ID = :id")
    suspend fun getPetById(id: String): PetEntity

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(petEntity: PetEntity)

    @Query("DELETE FROM PET_ENTITY WHERE ID = :id")
    suspend fun remove(id: String)

    @Query("SELECT * FROM PET_ENTITY")
    fun getFavoritePets(): Flow<List<PetEntity>>

    @Query("SELECT * FROM PET_ENTITY")
    suspend fun getFavoritePetsSuspend(): List<PetEntity>

    @Upsert
    suspend fun upsertAll(pets: List<PetEntity>)

    @Query("SELECT * FROM PET_ENTITY")
    fun pagingSource(): PagingSource<Int, PetEntity>

    @Query("DELETE FROM PET_ENTITY")
    fun clearAll()
}