package com.dumitrachecristian.petapp.ui.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.dumitrachecristian.petapp.data.datasource.PetPagingRemoteDataSource
import com.dumitrachecristian.petapp.data.toPetEntity
import com.dumitrachecristian.petapp.model.AnimalDto
import com.dumitrachecristian.petapp.model.SelectedFilter
import com.dumitrachecristian.petapp.network.Result
import com.dumitrachecristian.petapp.repository.PetRepository
import com.dumitrachecristian.petapp.ui.mainscreen.Filter.Companion.CURRENT_LOCATION
import com.dumitrachecristian.petapp.ui.mainscreen.Filter.Companion.EVERYWHERE
import com.dumitrachecristian.petapp.utils.LocationService
import com.dumitrachecristian.petapp.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class Filter(
    val animalTypes: List<String>,
    val genders: List<String> = listOf(ALL, MALE, FEMALE),
    val currentLocation: List<String> = listOf(EVERYWHERE, CURRENT_LOCATION)
) {
    companion object {
        const val ALL = "All"
        const val MALE = "Male"
        const val FEMALE = "Female"
        const val CURRENT_LOCATION = "Current Location"
        const val EVERYWHERE = "Everywhere"
    }
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: PetRepository,
    private val utils: Utils,
    private val locationService: LocationService
) : ViewModel() {
    private val _animalDetails = MutableStateFlow<AnimalDto?>(null)
    val animalDetails: StateFlow<AnimalDto?> = _animalDetails

    private val _filterState = MutableStateFlow(Filter(animalTypes = listOf(Filter.ALL)))
    val filterState: StateFlow<Filter> = _filterState

    private val _permissionGranted = MutableStateFlow(false)

    private val _selectedFilter = MutableStateFlow(
        SelectedFilter(
            animalType = Filter.ALL,
            gender = Filter.ALL,
            currentLocationLabel = EVERYWHERE,
            currentLocationValue = ""
        )
    )
    val selectedFilter = _selectedFilter

    private val _updateData = MutableStateFlow<Boolean>(false)
    val updateData: StateFlow<Boolean> = _updateData

    fun setAnimalDetails(animalDto: AnimalDto) {
        _animalDetails.value = animalDto
    }

    fun setUpdateData(value: Boolean) {
        _updateData.value = value
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

    val petsFromDb = repository.getPetsFromDb()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())


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

    fun updateLocationTypeEverywhere() {
        _selectedFilter.update {
            it.copy(currentLocationLabel = EVERYWHERE)
        }
    }

    fun setPermissionGranted(permissionGranted: Boolean, item: String) {
        _permissionGranted.value = permissionGranted
        getDataCurrentLocation(item)
    }

    private fun getDataCurrentLocation(item: String) {
        viewModelScope.launch {
            locationService.getCurrentLocation()?.let { location ->
                _selectedFilter.update {
                    it.copy(
                        currentLocationLabel = CURRENT_LOCATION,
                        currentLocationValue = "${location.latitude},${location.longitude}"
                    )
                }
            }
        }
    }

    fun addRemoveFromFavorites(animal: AnimalDto) {
        viewModelScope.launch {
            if (animal.isFavorite) {
                animal.isFavorite = false
                repository.removePet(animal.id)
                _updateData.value = true
            } else {
                animal.isFavorite = true
                val animalEntity = animal.toPetEntity()
                repository.insertAnimal(animalEntity)
                _updateData.value = true
            }
        }
    }

    fun getBackgroundImage(species: String): Int {
        return utils.getBackgroundImageBasedOnSpecies(species)
    }
}