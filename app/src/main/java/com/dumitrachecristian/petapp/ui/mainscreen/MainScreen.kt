package com.dumitrachecristian.petapp.ui.mainscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dumitrachecristian.petapp.R
import com.dumitrachecristian.petapp.model.AnimalDto
import com.dumitrachecristian.petapp.navigation.Screen
import com.dumitrachecristian.petapp.ui.components.AnimalItem
import com.dumitrachecristian.petapp.ui.components.ErrorItem
import com.dumitrachecristian.petapp.ui.mainscreen.Filter.Companion.CURRENT_LOCATION
import com.dumitrachecristian.petapp.utils.Utils
import com.dumitrachecristian.petapp.utils.network.ConnectionState
import com.dumitrachecristian.petapp.utils.network.connectivityState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState


@OptIn(
    ExperimentalPermissionsApi::class
)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    viewModel: MainViewModel, navController: NavHostController
) {
    LaunchedEffect(Unit) {
        viewModel.getFilters()
    }

    val showRationalDialog = remember { mutableStateOf(false) }

    val permissionStates = rememberMultiplePermissionsState(Utils.getLocationPermissions()) {
        if (it.all { it.value }) {
            viewModel.setPermissionGranted(true, CURRENT_LOCATION)
        } else {
            showRationalDialog.value = true
        }
    }

    val pets = viewModel.petsPagingFlow.collectAsLazyPagingItems()
    val filters = viewModel.filterState.collectAsState()

    val connection by connectivityState()
    val isConnected = connection == ConnectionState.Available

    if (!isConnected) {
        // TODO do something if there is not internet
    }

    if (viewModel.updateData.collectAsState().value) {
        viewModel.petsPagingFlow.collectAsLazyPagingItems().refresh()
        viewModel.setUpdateData(false)
    }

    if (showRationalDialog.value) {
        showPermissionDialog()
    }

    val selectedFilters = viewModel.selectedFilter.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(50.dp))
            LazyRow(modifier = Modifier.fillMaxWidth()) {
                val selectedType = selectedFilters.value.animalType
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
                val selectedType = selectedFilters.value.gender
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

            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(modifier = Modifier.fillMaxWidth()) {
                val selectedType = selectedFilters.value.currentLocationLabel
                items(filters.value.currentLocation.size) { index ->
                    val item = filters.value.currentLocation[index]
                    FilterChip(
                        modifier = Modifier.padding(horizontal = 6.dp),
                        selected = (item == selectedType),
                        onClick = {
                            if (item == CURRENT_LOCATION) {
                                if (!permissionStates.allPermissionsGranted) {
                                    if (permissionStates.shouldShowRationale) {
                                        showRationalDialog.value = true
                                    } else {
                                        permissionStates.launchMultiplePermissionRequest()
                                    }
                                } else {
                                    viewModel.setPermissionGranted(true, item)
                                }
                            } else {
                                viewModel.updateLocationTypeEverywhere()
                            }
                        },
                        label = {
                            Text(text = item)
                        }
                    )
                }
            }


            AnimalListItem(pets, viewModel, navController)
        }
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp),
            onClick = {
                navController.navigate(Screen.FavoriteScreen.route)
            },
        ) {
            Icon(Icons.Filled.Favorite, "Floating action button favorite.")
        }
    }
}

@Composable
fun AnimalListItem(
    pets: LazyPagingItems<AnimalDto>,
    viewModel: MainViewModel,
    navController: NavHostController
) {
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
                            placeholder = painterResource(
                                id = viewModel.getBackgroundImage(it.species)
                            ),
                            onFavoriteClick = { animal ->
                                viewModel.addRemoveFromFavorites(animal)
                            },
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
                        ErrorItem(message = stringResource(R.string.error))
                    }
                }
            }
        }
    }
}

@Composable
fun showPermissionDialog() {
    val openDialog = remember { mutableStateOf(true)  }

    val context = LocalContext.current
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = stringResource(R.string.missing_permission))
            },
            text = {
                Text(stringResource(R.string.missing_location_permission))
            },
            confirmButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                        context.startActivity(
                                Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", context.packageName, null)
                            )
                        )
                    }) {
                    Text(stringResource(R.string.go_to_settings))
                }
            },
            dismissButton = {
                Button(

                    onClick = {
                        openDialog.value = false
                    }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
}