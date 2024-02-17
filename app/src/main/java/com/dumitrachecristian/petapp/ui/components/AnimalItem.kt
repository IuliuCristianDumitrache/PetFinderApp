package com.dumitrachecristian.petapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dumitrachecristian.petapp.R
import com.dumitrachecristian.petapp.model.AnimalDto
import com.dumitrachecristian.petapp.ui.theme.BlackTransparent
import com.dumitrachecristian.petapp.ui.theme.Typography
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
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(White)) {
            AnimalItemImage(animalResult, placeholder)
            Text(
                text = "${animalResult.name} - ${animalResult.species}",
                modifier = Modifier.align(Alignment.Start)
                    .padding(6.dp),
                color = Black,
                style = Typography.bodySmall
            )
        }
    }
}

@Composable
fun AnimalItemImage(animalDto: AnimalDto, placeholder: Painter) {
    val photo = if (animalDto.photos.isNotEmpty()) { animalDto.photos[0].medium } else { null }
    AsyncImage(
        modifier = Modifier
            .fillMaxWidth(),
        placeholder = placeholder,
        fallback = placeholder,
        model = photo,
        contentDescription = "",
        contentScale = ContentScale.FillWidth
    )
}