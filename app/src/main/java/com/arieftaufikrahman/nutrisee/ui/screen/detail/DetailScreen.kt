package com.arieftaufikrahman.nutrisee.ui.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.arieftaufikrahman.nutrisee.R
import com.arieftaufikrahman.nutrisee.data.local.ItemEntity
import com.arieftaufikrahman.nutrisee.ui.common.UiState
import com.arieftaufikrahman.nutrisee.ui.component.AddButton
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(itemId: Int, navController: NavController, scaffoldState: ScaffoldState) {
    val detailViewModel = hiltViewModel<DetailViewModel>()
    detailViewModel.item.collectAsState(UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> detailViewModel.getItem(itemId)
            is UiState.Error -> Error()
            is UiState.Success -> {
                DetailContent(
                    uiState.data,
                    navController,
                    scaffoldState,
                    detailViewModel::updateFavoriteItem,
                    detailViewModel::updateDailyItem,
                )
            }
        }
    }
}

@Composable
fun DetailContent(
    item: ItemEntity,
    navController: NavController,
    scaffoldState: ScaffoldState,
    onUpdateFavoriteItem: (id: Int, isFavorite: Boolean) -> Unit,
    onAddToCart: (id: Int, isOnCart: Boolean) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val (id, name, image, calories, gram, isFavorite, isOnCart) = item

    val grams = gram.toString()
    val strCalories = calories.toString()

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp)
        ) {
            AsyncImage(
                model = image,
                contentDescription = name,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.sampleimage),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .testTag("scroll")
            )
            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )
            Text(
                text = name,
                fontWeight = Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Kalori",
                            fontWeight = Bold,
                            fontSize = 18.sp
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "$strCalories kalori",
                            fontSize = 18.sp
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Gram",
                            fontWeight = Bold,
                            fontSize = 18.sp
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "$grams g",
                            fontSize = 18.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.fillMaxWidth().height(4.dp).background(LightGray))
                Button(
                    onClick = {
                       onAddToCart(id ?: 0, !isOnCart)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .padding(top = 16.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            onAddToCart(id ?: 0, !isOnCart)
                            coroutineScope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = "$name, ${if (isOnCart) "Dihapus dari" else "ditambahkan"} daily",
                                    actionLabel = "Tutup",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                ){
                    Text(
                        text = if(!isOnCart) "Masukkan ke daftar" else "Hapus dari daftar",
                    )
                }
            }
        }
        IconButton(
            onClick = { navController.navigateUp() },
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    top = 16.dp
                )
                .align(Alignment.TopStart)
                .clip(CircleShape)
                .size(40.dp)
                .testTag("back_button")
                .background(Color.White)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = Color.Black
            )
        }
        IconButton(
            onClick = {
                if (id != null) {
                    onUpdateFavoriteItem(id, isFavorite)
                }
            },
            modifier = Modifier
                .padding(end = 16.dp, top = 16.dp)
                .align(Alignment.TopEnd)
                .clip(CircleShape)
                .size(40.dp)
                .background(Color.White)
                .testTag("add_remove_favorite")
        ) {
            Icon(
                imageVector = if (isFavorite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                tint = Color.Red,
                contentDescription = name,
                modifier = Modifier
                    .size(27.dp)
                    .clip(RoundedCornerShape(100))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onUpdateFavoriteItem(id ?: 0, !isFavorite)
                        coroutineScope.launch {
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = "$name ${if (isFavorite) "Dihapus dari" else "Ditambahkan ke"} favorite",
                                actionLabel = "Tutup",
                                duration = SnackbarDuration.Short
                            )
                        }
                    },
            )
        }
    }
}