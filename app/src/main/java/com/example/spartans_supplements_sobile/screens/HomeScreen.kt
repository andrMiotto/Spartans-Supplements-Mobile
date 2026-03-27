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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.spartans_supplements_sobile.R
import com.example.spartans_supplements_sobile.ui.viewModel.ProdutoViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

data class Product(
    val id: Long,
    val name: String,
    val price: String,
    val imageRes: Int,
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
                imageRes = R.drawable.whey_spartans,
                categoria = it.categoria ?: "Outros"
            )
        }
    }

    val categorias = remember(products) {
        products.groupBy { it.categoria }
    }

    // DIÁLOGO DE CONFIRMAÇÃO
    if (showDeleteDialog && productToDelete != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Excluir Produto") },
            text = { Text("Tem certeza que deseja deletar '${productToDelete?.name}'? Esta ação não pode ser desfeita.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        productToDelete?.let { viewModel.deletarProduto(it.id) }
                        showDeleteDialog = false
                    }
                ) {
                    Text("Sim, Deletar", color = Color(0xFFE53935), fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancelar", color = Color.Black)
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
                    Image(painter = painterResource(id = R.drawable.logo), contentDescription = "Logo", modifier = Modifier.height(40.dp))
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues).fillMaxSize(),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item { Box(modifier = Modifier.padding(16.dp)) { HeroBanner() } }

            categorias.forEach { (categoria, produtosDaCategoria) ->
                item { CategoryHeader(titulo = categoria) }
                item {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(produtosDaCategoria) { product ->
                            ProductCard(
                                product = product,
                                modifier = Modifier.width(170.dp),
                                onDeleteClick = {
                                    productToDelete = product
                                    showDeleteDialog = true
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
fun ProductCard(
    product: Product,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier.border(1.dp, Color(0xFFE8E8E8), RoundedCornerShape(12.dp))
    ) {
        Column(modifier = Modifier.padding(10.dp)) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFF5F5F5))
            ) {

                Image(
                    painter = painterResource(id = product.imageRes),
                    contentDescription = product.name,
                    modifier = Modifier
                        .size(110.dp)
                        .align(Alignment.Center)
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
                        modifier = Modifier.size(18.dp) // Mantido o tamanho original do ícone
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(text = product.name, fontSize = 13.sp, fontWeight = FontWeight.Medium, maxLines = 2, modifier = Modifier.height(34.dp))
            Text(text = product.price, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold)

            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add to cart", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}


@Composable
fun CategoryHeader(titulo: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.width(4.dp).height(20.dp).background(Color.Black))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = titulo, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
        }
    }
}

@Composable
fun HeroBanner() {
    Box(modifier = Modifier.fillMaxWidth().height(220.dp).clip(RoundedCornerShape(16.dp))) {
        Image(painter = painterResource(id = R.drawable.black_square), contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
        Column(modifier = Modifier.padding(20.dp)) {
            Text("Start simple\nwith everyday\nsupplements", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = { }, colors = ButtonDefaults.buttonColors(containerColor = Color.White)) {
                Text("Shop now", color = Color.Black)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar(containerColor = Color.White) {
        NavigationBarItem(icon = { Icon(Icons.Outlined.Home, null) }, label = { Text("Home") }, selected = true, onClick = {})
        NavigationBarItem(icon = { Icon(Icons.Outlined.ShoppingCart, null) }, label = { Text("Cart") }, selected = false, onClick = { navController.navigate("cart") })
    }
}