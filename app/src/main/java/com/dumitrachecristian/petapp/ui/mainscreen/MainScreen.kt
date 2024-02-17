package com.dumitrachecristian.petapp.ui.mainscreen

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.dumitrachecristian.petapp.R
import com.dumitrachecristian.petapp.model.AnimalDto
import com.dumitrachecristian.petapp.navigation.Screen
import com.dumitrachecristian.petapp.ui.components.AnimalItem
import com.dumitrachecristian.petapp.ui.components.ErrorItem
import com.dumitrachecristian.petapp.utils.network.ConnectionState
import com.dumitrachecristian.petapp.utils.network.connectivityState

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    viewModel: MainViewModel, navController: NavHostController) {

    LaunchedEffect(Unit) {
        viewModel.getFilters()
    }

    val pets = viewModel.petsPagingFlow.collectAsLazyPagingItems()
    val filters = viewModel.filterState.collectAsState()

    val connection by connectivityState()
    val isConnected = connection == ConnectionState.Available

//    if (!isConnected) {
//        viewModel.getDataCurrentLocationDb()
//    }

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(50.dp))
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            val selectedType = viewModel.selectedFilter.value.animalType
            items(filters.value.animalTypes.size) { index ->
                val item = filters.value.animalTypes[index]
                FilterChip(
                    modifier = Modifier.padding(horizontal = 6.dp),
                    selected = (item == selectedType),
                    onClick = {
                        viewModel.updateFilterType(item)
                    },
                    label = {
                        Text(text = item)
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            val selectedType = viewModel.selectedFilter.value.gender
            items(filters.value.genders.size) { index ->
                val item = filters.value.genders[index]
                FilterChip(
                    modifier = Modifier.padding(horizontal = 6.dp),
                    selected = (item == selectedType),
                    onClick = {
                        viewModel.updateFilterGender(item)
                    },
                    label = {
                        Text(text = item)
                    }
                )
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            if (pets.loadState.refresh is LoadState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    verticalItemSpacing = 16.dp,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    val modifier = Modifier
                    items(pets.itemCount) { position ->
                        pets[position]?.let {
                            AnimalItem(
                                modifier = modifier,
                                animalResult = it,
                                placeholder = painterResource(id = if (it.species.contains("Cat"))
                                { R.drawable.catplaceholder } else { R.drawable.petplaceholder}),
                                onFavoriteClick = {},
                                onClick = { animal ->
                                    viewModel.setAnimalDetails(animal)
                                    val baseRoute = Screen.PetDetailScreen.route
                                    navController.navigate("$baseRoute/${animal.id}")
                                }
                            )
                        }
                    }
                    item() {
                        if (pets.loadState.append is LoadState.Loading) {
                            CircularProgressIndicator()
                        } else if (pets.loadState.append is LoadState.Error) {
                            ErrorItem(message = "Some error")
                        }
                    }
                }
            }
        }
    }
}