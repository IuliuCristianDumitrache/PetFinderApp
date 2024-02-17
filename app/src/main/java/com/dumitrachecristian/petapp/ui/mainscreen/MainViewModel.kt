package com.dumitrachecristian.petapp.ui.mainscreen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.dumitrachecristian.petapp.data.datasource.PetPagingRemoteDataSource
import com.dumitrachecristian.petapp.model.AnimalDto
import com.dumitrachecristian.petapp.model.SelectedFilter
import com.dumitrachecristian.petapp.network.Result
import com.dumitrachecristian.petapp.repository.PetRepository
import com.dumitrachecristian.petapp.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class Filter(
    val animalTypes: List<String>,
    val genders: List<String> = listOf(ALL, MALE, FEMALE)
) {
    companion object {
        const val ALL = "All"
        const val MALE = "Male"
        const val FEMALE = "Female"
    }
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: PetRepository,
    private val utils: Utils,
): ViewModel() {
    private var _searchResult = mutableStateListOf<AnimalDto>()
    val searchResult: List<AnimalDto> = _searchResult

    private val _animalDetails = MutableStateFlow<AnimalDto?>(null)
    val animalDetails: StateFlow<AnimalDto?> = _animalDetails

    private val _filterState = MutableStateFlow(Filter(animalTypes = listOf(Filter.ALL)))
    val filterState: StateFlow<Filter> = _filterState

    private val _selectedFilter = MutableStateFlow(SelectedFilter(animalType = Filter.ALL, gender = Filter.ALL))
    val selectedFilter = _selectedFilter

    fun setAnimalDetails(animalDto: AnimalDto) {
        _animalDetails.value = animalDto
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val petsPagingFlow = _selectedFilter.flatMapLatest {
        Pager(
            PagingConfig(pageSize = 20, initialLoadSize = 30)
        ) {
            PetPagingRemoteDataSource(repository, _selectedFilter.value)
        }
            .flow
            .cachedIn(viewModelScope)
    }


    fun getFilters() {
        viewModelScope.launch {
            when (val result = repository.getFilters()) {
                is Result.Success -> {
                    val types = arrayListOf(Filter.ALL)
                    result.data?.types?.forEach { type ->
                        types.add(type.name)
                    }

                    _filterState.update {
                        it.copy(animalTypes = types)
                    }
                }

                is Result.Error -> {
                }

                is Result.Loading -> {
                }
            }
        }
    }

    fun updateFilterType(animalType: String) {
        _selectedFilter.update {
            it.copy(animalType = animalType)
        }
    }

    fun updateFilterGender(gender: String) {
        _selectedFilter.update {
            it.copy(gender = gender)
        }
    }
}