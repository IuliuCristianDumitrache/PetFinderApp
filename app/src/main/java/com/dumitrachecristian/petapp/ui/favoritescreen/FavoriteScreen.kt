package com.dumitrachecristian.petapp.ui.favoritescreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.dumitrachecristian.petapp.R
import com.dumitrachecristian.petapp.data.toPet
import com.dumitrachecristian.petapp.ui.components.AnimalItem
import com.dumitrachecristian.petapp.ui.mainscreen.MainViewModel
import com.dumitrachecristian.petapp.ui.theme.Typography

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FavoriteScreen(viewModel: MainViewModel, navController: NavHostController) {

    val pets = viewModel.petsFromDb.collectAsStateWithLifecycle()

    Column() {
        IconButton(
            modifier = Modifier
                .padding(top = 20.dp, start = 10.dp),
            onClick = {
                navController.popBackStack()
            }) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = stringResource(R.string.back),
                tint = Color.Black
            )
        }

        if (pets.value.isEmpty()) {
            Text(text = stringResource(R.string.no_favorites_announces_yet),
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                style = Typography.titleMedium)
        } else {
            val uriHandler = LocalUriHandler.current

            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                verticalItemSpacing = 16.dp,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(pets.value.size) { position ->

                    pets.value[position].let {
                        AnimalItem(
                            modifier = Modifier,
                            animalResult = it.toPet(),
                            placeholder = painterResource(
                                id = viewModel.getBackgroundImage(it.species)
                            ),
                            onFavoriteClick = { animal ->
                                viewModel.addRemoveFromFavorites(animal)
                            },
                            onClick = { animal ->
                                uriHandler.openUri(animal.url)
                            }
                        )
                    }
                }
            }
        }
    }
}