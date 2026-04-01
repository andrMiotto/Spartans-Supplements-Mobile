package com.example.spartans_supplements_sobile.screens


import androidx.compose.foundation.Image

import androidx.compose.foundation.background

import androidx.compose.foundation.border

import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.foundation.lazy.LazyRow

import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.shape.CircleShape

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.AddCircle

import androidx.compose.material.icons.outlined.Delete

import androidx.compose.material.icons.outlined.Home

import androidx.compose.material.icons.outlined.ShoppingCart

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier

import androidx.compose.ui.draw.clip

import androidx.compose.ui.graphics.Color

import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp

import androidx.compose.ui.unit.sp

import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.NavHostController

import coil.compose.AsyncImage

import com.example.spartans_supplements_sobile.R

import com.example.spartans_supplements_sobile.ui.viewModel.ProdutoViewModel

import kotlinx.coroutines.delay

import kotlinx.coroutines.isActive


data class Product(

    val id: Long,

    val name: String,

    val price: String,

    val imageRes: String,

    val categoria: String

)


@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun StoreHomeScreen(

    navController: NavHostController,

    viewModel: ProdutoViewModel = viewModel()

) {

    val produtosDaApi = viewModel.produtos


    var showDeleteDialog by remember { mutableStateOf(false) }

    var productToDelete by remember { mutableStateOf<Product?>(null) }



    LaunchedEffect(Unit) {

        while (isActive) {

            viewModel.listarProdutos()

            delay(5000)

        }

    }


    val products = remember(produtosDaApi) {

        produtosDaApi.map {

            Product(

                id = it.id,

                name = it.nome,

                price = "R$ ${it.preco}",

                imageRes = it.imagemUrl ?: "",

                categoria = it.categoria ?: "Outros"

            )

        }

    }


    val categorias = remember(products) {

        products.groupBy { it.categoria }

    }



    if (showDeleteDialog && productToDelete != null) {

        AlertDialog(

            onDismissRequest = { showDeleteDialog = false },

            title = { Text(" ") },

            text = {
                Text(stringResource(id = R.string.dialog_delete_text, productToDelete?.name ?: ""))
            },
            confirmButton = {

                TextButton(

                    onClick = {

                        productToDelete?.let { viewModel.deletarProduto(it.id) }

                        showDeleteDialog = false

                    }

                ) {

                    Text("Yes, delete", color = Color(0xFFE53935), fontWeight = FontWeight.Bold)

                }

            },

            dismissButton = {

                TextButton(onClick = { showDeleteDialog = false }) {

                    Text("Cancel", color = Color.Black)

                }

            },

            containerColor = Color.White,

            shape = RoundedCornerShape(16.dp)

        )

    }



    Scaffold(

        topBar = {

            CenterAlignedTopAppBar(

                title = {

                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Icon",
                        modifier = Modifier.height(40.dp)
                    )

                },

                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)

            )

        },

        bottomBar = { BottomNavigationBar(navController) }

    ) { paddingValues ->

        LazyColumn(

            modifier = Modifier

                .padding(paddingValues)

                .fillMaxSize(),

            contentPadding = PaddingValues(bottom = 24.dp)

        ) {


            item { Box(modifier = Modifier.padding(16.dp)) { HeroBanner() } }



            categorias.forEach { (categoria, produtosDaCategoria) ->

                item { CategoryHeader(titulo = categoria) }

                item {

                    LazyRow(

                        contentPadding = PaddingValues(horizontal = 16.dp),

                        horizontalArrangement = Arrangement.spacedBy(12.dp),

                        verticalAlignment = Alignment.CenterVertically

                    ) {


                        items(produtosDaCategoria) { product ->

                            ProductCard(

                                product = product,

                                modifier = Modifier.width(170.dp),

                                onClick = { navController.navigate("detail/${product.id}") },

                                onDeleteClick = {

                                    productToDelete = product

                                    showDeleteDialog = true

                                }

                            )

                        }





                        item {

                            AddProductCard(

                                modifier = Modifier.width(170.dp),

                                onClick = {

// Certifique-se de que a rota no seu NavHost seja "register_product"

                                    navController.navigate("register_product")

                                }

                            )

                        }

                    }

                }

                item { Spacer(modifier = Modifier.height(16.dp)) }

            }

        }

    }

}


@Composable

fun CategoryHeader(titulo: String) {

    Row(

        modifier = Modifier

            .fillMaxWidth()

            .padding(horizontal = 16.dp, vertical = 12.dp),

        verticalAlignment = Alignment.CenterVertically,

        horizontalArrangement = Arrangement.SpaceBetween

    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {

            Box(

                modifier = Modifier

                    .width(4.dp)

                    .height(20.dp)

                    .clip(RoundedCornerShape(2.dp))

                    .background(Color.Black)

            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(

                text = titulo,

                fontSize = 18.sp,

                fontWeight = FontWeight.ExtraBold,

                color = Color.Black

            )

        }

        Text(

            text = stringResource(R.string.see_all),
            fontSize = 13.sp,

            fontWeight = FontWeight.Medium,

            color = Color.Gray

        )

    }

}


@Composable

fun HeroBanner(modifier: Modifier = Modifier) {

    Box(

        modifier = modifier

            .fillMaxWidth()

            .height(220.dp)

            .clip(RoundedCornerShape(16.dp))

    ) {

        Image(

            painter = painterResource(id = R.drawable.black_square),

            contentDescription = "Banner",

            contentScale = ContentScale.Crop,

            modifier = Modifier.fillMaxSize()

        )

        Column(

            modifier = Modifier

                .fillMaxSize()

                .padding(20.dp),

            verticalArrangement = Arrangement.SpaceBetween

        ) {

            Column {

                Text(

                    text = stringResource(R.string.hero_title),

                    color = Color.White,

                    fontSize = 22.sp,

                    fontWeight = FontWeight.ExtraBold,

                    lineHeight = 28.sp

                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(

                    text = stringResource(R.string.hero_subtitle),
                    color = Color.White.copy(alpha = 0.8f),

                    fontSize = 12.sp

                )

            }

            Button(

                onClick = { },

                colors = ButtonDefaults.buttonColors(containerColor = Color.White),

                shape = RoundedCornerShape(8.dp)

            ) {

                Text(stringResource(R.string.btn_shop_now), color = Color.Black, fontWeight = FontWeight.Bold)

            }

        }

    }

}


@Composable

fun ProductCard(

    product: Product,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}

) {

    Card(

        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = modifier
            .border(1.dp, Color(0xFFE8E8E8), RoundedCornerShape(12.dp))
            .clickable { onClick() }
    ) {

        Column(modifier = Modifier.padding(10.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFF5F5F5))
            ) {


                AsyncImage(

                    model = product.imageRes,

                    contentDescription = product.name,

                    modifier = Modifier

                        .size(110.dp)

                        .align(Alignment.Center),

                    contentScale = ContentScale.Fit

                )



                Box(

                    modifier = Modifier

                        .align(Alignment.TopEnd)

                        .padding(6.dp)

                        .size(26.dp)

                        .background(Color.White.copy(alpha = 0.9f), CircleShape)

                        .clickable { onDeleteClick() },

                    contentAlignment = Alignment.Center

                ) {

                    Icon(

                        imageVector = Icons.Outlined.Delete,

                        contentDescription = "Delete",

                        tint = Color(0xFFE53935),

                        modifier = Modifier.size(18.dp)

                    )

                }

            }



            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = product.name,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                modifier = Modifier.height(34.dp)
            )

            Text(text = product.price, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold)



            Spacer(modifier = Modifier.height(10.dp))

            Button(

                onClick = { onClick() },

                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),

                shape = RoundedCornerShape(8.dp),

                modifier = Modifier.fillMaxWidth()

            ) {

                Text(stringResource(R.string.btn_add_to_cart), fontSize = 12.sp, fontWeight = FontWeight.Bold)

            }

        }

    }

}


@Composable
fun AddProductCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier
            .height(280.dp)
            .border(2.dp, Color.LightGray, RoundedCornerShape(12.dp))
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.AddCircle,
                contentDescription = "Adicionar Produto",
                tint = Color.Black,
                modifier = Modifier.size(80.dp)
            )
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar(containerColor = Color.White) {
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.Home, null) },
            label = { Text(stringResource(R.string.nav_home)) },
            selected = true,
            onClick = {})
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.ShoppingCart, null) },
            label = { Text(stringResource(R.string.nav_cart)) },
            selected = false,
            onClick = { navController.navigate("cart") })
    }
}