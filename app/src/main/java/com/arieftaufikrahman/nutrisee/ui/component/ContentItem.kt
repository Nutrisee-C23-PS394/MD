package com.arieftaufikrahman.nutrisee.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.arieftaufikrahman.nutrisee.R
import com.arieftaufikrahman.nutrisee.data.local.ItemEntity
import com.arieftaufikrahman.nutrisee.ui.navigation.Screen
import kotlinx.coroutines.launch

@Composable
fun AvailableContent(
    listItem: List<ItemEntity>,
    navController: NavController,
    scaffoldState: ScaffoldState,
    onUpdateFavoriteTourism: (id: Int, isFavorite: Boolean) -> Unit,
) {
    LazyColumn {
        items(listItem, key = { it.name }) { menu ->
            ContentItem(menu, navController, scaffoldState, onUpdateFavoriteTourism)
        }
    }
}

@Composable
fun ContentItem(
    item: ItemEntity,
    navController: NavController,
    scaffoldState: ScaffoldState,
    onUpdateFavoriteTourism: (id: Int, isFavorite: Boolean) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val (id, name, image, calories, gram, isFavorite, isOnCart) = item

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
            .border(1.dp, Color.LightGray.copy(0.5f), MaterialTheme.shapes.small)
            .clickable { navController.navigate(Screen.DetailFood.createRoute(id ?: 0)) },
    ) {
        Column {
            Box {
                AsyncImage(
                    model = image,
                    contentDescription = name,
                    contentScale = ContentScale.FillWidth,
                    placeholder = painterResource(R.drawable.sampleimage),
                    modifier = Modifier.fillMaxWidth()
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(bottomEnd = 8.dp))
                        .background(MaterialTheme.colors.primaryVariant)
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = calories.toString(),
                            style = MaterialTheme.typography.body2,
                            color = Color.White
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.h6
                    )

                    Icon(
                        imageVector = if (isFavorite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                        tint = if (isFavorite) Color.Red else MaterialTheme.colors.onSurface,
                        contentDescription = name,
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(100))
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                onUpdateFavoriteTourism(id ?: 0, !isFavorite)
                                coroutineScope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar(
                                        message = "$name ${if (isFavorite) "removed from" else "added as"} favorite ",
                                        actionLabel = "Dismiss",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            },
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "ID: ${id ?: 0}",
                        style = MaterialTheme.typography.body2
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Calories: $calories",
                        style = MaterialTheme.typography.body2
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Gram: $gram",
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    }
}