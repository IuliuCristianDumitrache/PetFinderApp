package com.dumitrachecristian.petapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dumitrachecristian.petapp.R
import com.dumitrachecristian.petapp.model.AnimalDto
import com.dumitrachecristian.petapp.ui.theme.BlackTransparent
import com.dumitrachecristian.petapp.ui.theme.VeryLightGray

@Composable
fun AnimalItem(
    modifier: Modifier,
    animalResult: AnimalDto,
    onFavoriteClick: (AnimalDto) -> Unit,
    onClick: (AnimalDto) -> Unit,
    placeholder: Painter
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = VeryLightGray),
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .clickable {
                onClick.invoke(animalResult)
            }
            .height(200.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            val photo = if (animalResult.photos.isNotEmpty()) { animalResult.photos[0].medium } else { null }
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = placeholder,
                fallback = placeholder,
                model = photo,
                contentDescription = "",
                contentScale = ContentScale.FillWidth
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BlackTransparent),
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = animalResult.name,
                    modifier = Modifier.align(Alignment.Start),
                    color = Color.White
                )
            }
        }
    }
}

/*
@ExperimentalCoilApi
@Composable
fun AnimalItem(animal: Animal) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (animal.photos.isNotEmpty()) {
            Image(
                painter = rememberImagePainter(animal.photos.first().full),
                contentDescription = "Animal Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = animal.name,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Type: ${animal.type}",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Breed: ${animal.breeds.primary}",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Age: ${animal.age}",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Gender: ${animal.gender}",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Size: ${animal.size}",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Description: ${animal.description}",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Contact: ${animal.contact.email}, ${animal.contact.phone}",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
 */