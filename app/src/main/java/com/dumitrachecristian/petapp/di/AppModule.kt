package com.dumitrachecristian.petapp.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.dumitrachecristian.petapp.data.AppDatabase
import com.dumitrachecristian.petapp.data.PetEntityDao
import com.dumitrachecristian.petapp.data.TokenManager
import com.dumitrachecristian.petapp.network.ApiService
import com.dumitrachecristian.petapp.network.ApiServiceProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return getSharedPreferences(context)
    }
    @Provides
    fun provideApiService(tokenManager: TokenManager): ApiService {
        return ApiServiceProvider.getClient(tokenManager)
    }

    @Provides
    @Singleton
    fun provideDatabase(
        app: Application,
    ) = Room.databaseBuilder(app, AppDatabase::class.java, "PET_DB")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(application: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(application)
    }

    @Provides
    fun provideModelDao(appDatabase: AppDatabase): PetEntityDao {
        return appDatabase.petEntityDao()
    }

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return EncryptedSharedPreferences.create(
            "AppPreferences",
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}