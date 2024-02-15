package com.dumitrachecristian.petapp.ui.mainscreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.dumitrachecristian.petapp.navigation.Screen
import com.dumitrachecristian.petapp.ui.components.AnimalItem
import com.dumitrachecristian.petapp.ui.components.ErrorItem
import com.dumitrachecristian.petapp.utils.network.ConnectionState
import com.dumitrachecristian.petapp.utils.network.connectivityState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    viewModel: MainViewModel, navController: NavHostController) {


    val pets = viewModel.petsPagingFlow.collectAsLazyPagingItems()

    val connection by connectivityState()
    val isConnected = connection == ConnectionState.Available

//    if (!isConnected) {
//        viewModel.getDataCurrentLocationDb()
//    }

    LaunchedEffect(Unit) {
        viewModel.requestToken()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if(pets.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val modifier = Modifier
                    .padding(horizontal = 16.dp)
                items(pets.itemCount) { position ->
                    pets[position]?.let {
                        AnimalItem(
                            modifier = modifier,
                            animalResult = it,
                            onFavoriteClick = {},
                            onClick = {
                                val baseRoute = Screen.PetDetailScreen.route
                                navController.navigate("$baseRoute/${pets[position]!!.id}")
                            }
                        )
                    }
                }
                item () {
                    if (pets.loadState.append is LoadState.Loading){
                        CircularProgressIndicator()
                    } else if (pets.loadState.append is LoadState.Error) {
                        ErrorItem(message = "Some error")
                    }
                }
            }
        }
    }
}