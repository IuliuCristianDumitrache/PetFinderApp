package com.dumitrachecristian.petapp.ui.mainscreen

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.dumitrachecristian.petapp.data.PetEntity
import com.dumitrachecristian.petapp.data.SessionManager
import com.dumitrachecristian.petapp.data.datasource.PetPagingRemoteDataSource
import com.dumitrachecristian.petapp.data.datasource.PetRemoteDataSource
import com.dumitrachecristian.petapp.data.toPet
import com.dumitrachecristian.petapp.model.Animal
import com.dumitrachecristian.petapp.model.AnimalDto
import com.dumitrachecristian.petapp.repository.PetRepository
import com.dumitrachecristian.petapp.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.dumitrachecristian.petapp.network.Result
import kotlinx.coroutines.flow.map

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: PetRepository,
    private val utils: Utils,
    private val sessionManager: SessionManager
): ViewModel() {
    private var _searchResult = mutableStateListOf<AnimalDto>()
    val searchResult: List<AnimalDto> = _searchResult


    val petsPagingFlow = Pager(
        PagingConfig(pageSize = 10)
    ) {
        PetPagingRemoteDataSource(repository, sessionManager)
    }.flow.cachedIn(viewModelScope)

    fun requestToken() {
        viewModelScope.launch {
            when(val result = repository.requestToken()) {
                is Result.Success -> {
                    result.data?.let { response ->
                        sessionManager.setToken(response.accessToken)
                    }
                }
                is Result.Error -> {

                }

                is Result.Loading -> {

                }
            }
        }
    }
}