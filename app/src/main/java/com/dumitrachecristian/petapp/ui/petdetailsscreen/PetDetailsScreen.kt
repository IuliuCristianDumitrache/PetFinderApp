package com.dumitrachecristian.petapp.ui.petdetailsscreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dumitrachecristian.petapp.R
import com.dumitrachecristian.petapp.ui.components.AnimalItemImage
import com.dumitrachecristian.petapp.ui.mainscreen.MainViewModel
import com.dumitrachecristian.petapp.ui.theme.Blue
import com.dumitrachecristian.petapp.ui.theme.BlueTransparent
import com.dumitrachecristian.petapp.ui.theme.Brown
import com.dumitrachecristian.petapp.ui.theme.BrownTransparent
import com.dumitrachecristian.petapp.ui.theme.Green
import com.dumitrachecristian.petapp.ui.theme.GreenTransparent
import com.dumitrachecristian.petapp.ui.theme.Putty
import com.dumitrachecristian.petapp.ui.theme.PuttyTransparent
import com.dumitrachecristian.petapp.ui.theme.VeryLightGray
import com.dumitrachecristian.petapp.utils.openEmail
import com.dumitrachecristian.petapp.utils.openGoogleMaps
import com.dumitrachecristian.petapp.utils.openPhone

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PetDetailsScreen(viewModel: MainViewModel, navController: NavHostController, id: String) {
    val animalDto by viewModel.animalDetails.collectAsState()
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.verticalScroll(scrollState)) {
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
        animalDto?.let { animal ->
            val placeholder = painterResource(id = R.drawable.dogplaceholder)
            AnimalItemImage(animal, placeholder)

            Column(modifier = Modifier.padding(8.dp)) {
                Spacer(modifier = Modifier.height(16.dp))

                Header(animal.name)
                SubHeader(animal.status)
                Spacer(modifier = Modifier.height(8.dp))

                Tags(animal.tags)

                Section(containerColor = Putty, title = animal.type, description = animal.breeds.primary)

                Row {
                    Section(modifier = Modifier.weight(0.33f), containerColor = Blue, title = stringResource(
                        R.string.age
                    ), description = animal.age)
                    Section(modifier = Modifier.weight(0.33f), containerColor = Brown, title = stringResource(
                        R.string.gender
                    ), description = animal.gender)
                    Section(modifier = Modifier.weight(0.33f), containerColor = Green, title = stringResource(
                        R.string.size
                    ), description = animal.size)
                }
                Section(title = "", description = animal.description)

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(R.string.contact),
                    style = MaterialTheme.typography.headlineSmall,
                )
                animal.contact?.address?.address1?.let { address ->
                    ClickableLink(address) {
                        context.openGoogleMaps(address)
                    }
                }
                animal.contact?.email?.let { email ->
                    ClickableLink(email) {
                        context.openEmail(email)
                    }
                }
                animal.contact?.phone?.let { phone ->
                    ClickableLink(phone) {
                        context.openPhone(phone)
                    }
                }
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Composable
fun ClickableLink(text: String, onClick: () -> Unit) {
    val annotatedString = buildAnnotatedString {
        append(text)
        addStyle(
            style = SpanStyle(color = Color.Blue, textDecoration = TextDecoration.Underline),
            start = 0,
            end = length
        )
    }
    ClickableText(
        modifier = Modifier.padding(vertical = 8.dp),
        text = annotatedString,
        style = MaterialTheme.typography.bodyLarge
    ) {
        onClick.invoke()
    }
}

@Composable
fun Header(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineMedium,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun SubHeader(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineSmall,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Tags(tags: List<String>?) {
    tags?.let {
        FlowRow(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
        ) {
            tags.forEach { hashTag ->
                Text(
                    text = hashTag,
                    modifier = Modifier
                        .background(
                            color = listOf(
                                BlueTransparent,
                                BrownTransparent,
                                PuttyTransparent,
                                GreenTransparent
                            ).random(),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(8.dp),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun Section(modifier: Modifier = Modifier, containerColor: Color = VeryLightGray, title: String, description: String?) {
    description?.let {
        Card(
            colors = CardDefaults.cardColors(containerColor = containerColor),
            shape = RoundedCornerShape(10.dp),
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}