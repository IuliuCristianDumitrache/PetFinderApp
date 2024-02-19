package com.dumitrachecristian.petapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface PetEntityDao {
    @Query("SELECT * FROM PET_ENTITY WHERE ID = :id")
    suspend fun getPetById(id: String): PetEntity

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(petEntity: PetEntity)

    @Query("DELETE FROM PET_ENTITY WHERE ID = :id")
    suspend fun remove(id: Int)

    @Query("SELECT * FROM PET_ENTITY")
    fun getFavoritePets(): Flow<List<PetEntity>>

    @Query("SELECT id from PET_ENTITY")
    fun getFavoritesIds(): List<Int>
}