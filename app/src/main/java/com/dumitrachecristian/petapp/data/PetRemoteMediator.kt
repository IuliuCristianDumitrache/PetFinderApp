//package com.dumitrachecristian.petapp.data
//
//import android.os.Build
//import androidx.annotation.RequiresExtension
//import androidx.paging.ExperimentalPagingApi
//import androidx.paging.LoadType
//import androidx.paging.PagingState
//import androidx.paging.RemoteMediator
//import androidx.room.withTransaction
//import com.dumitrachecristian.petapp.network.ApiService
//import com.dumitrachecristian.petapp.network.Result
//import com.dumitrachecristian.petapp.repository.PetRepository
//import retrofit2.HttpException
//import java.io.IOException
//
//@OptIn(ExperimentalPagingApi::class)
//class PetRemoteMediator (
//    private val petDb: AppDatabase,
//    private val sessionManager: SessionManager,
//    private val repository: PetRepository
//): RemoteMediator<Int, PetEntity>() {
//
//    override suspend fun load(
//        loadType: LoadType,
//        state: PagingState<Int, PetEntity>
//    ): MediatorResult {
//        state.anchorPosition?.let { position ->
//            val page = state.closestPageToPosition(position)
//            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
//        }
//        return try {
//            val loadKey = when(loadType) {
//                LoadType.REFRESH -> 1
//                LoadType.PREPEND -> {
//                    return MediatorResult.Success(endOfPaginationReached = true)
//                }
//                LoadType.APPEND -> {
//                    val lastItem = state.lastItemOrNull()
//                    if (lastItem == null) {
//                        1
//                    } else {
//                        2
//                    }
//                }
//            }
//
//            val result = repository.getPets(
//                "Bearer ${sessionManager.getToken()}", page = loadKey,
//                limit = state.config.pageSize
//            )
//            when(result) {
//                is Result.Success -> {
//                    result.data?.let { response ->
//                        petDb.withTransaction {
//                            if (loadType == LoadType.REFRESH) {
//                                petDb.petEntityDao().clearAll()
//                            }
//                            val petEntities = response.pets.map { it.toPetEntity() }
//                            repository.upsertAll(petEntities)
//                        }
//                    }
//                }
//                is Result.Error -> {
//
//                }
//                is Result.Loading -> {
//
//                }
//            }
//            MediatorResult.Success(
//                endOfPaginationReached = result.data?.pets?.isEmpty() ?: true
//            )
//        } catch(e: IOException) {
//            MediatorResult.Error(e)
//        } catch (e: HttpException) {
//            MediatorResult.Error(e)
//        }
//    }
//
//}