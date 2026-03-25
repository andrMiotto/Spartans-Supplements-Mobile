package com.example.spartans_supplements_sobile.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavHostController
import com.example.spartans_supplements_sobile.R

data class Product(val name: String, val price: String, val imageRes: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreHomeScreen(navController: NavHostController) {
    val products = listOf(
        Product("Whey Protein Isolate", "$34.99", R.drawable.whey_spartans),
        Product("Creatine Monohydrate", "$19.99", R.drawable.creatine_monohydrate),
        Product("Pre-Workout Energy", "$29.99", R.drawable.pre_workout),
        Product("BCAA Amino Acids", "$24.99", R.drawable.bcaa_amino)
    )

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo Spartans",
                    modifier = Modifier.height(40.dp)
                )
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color.White
            )
        )
    }, bottomBar = {
        BottomNavigationBar(navController)
    }, containerColor = Color(0xFFF9F9F9)
    ) { paddingValues ->

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item(span = { GridItemSpan(maxLineSpan) }) {
                HeroBanner()
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    text = stringResource(id = R.string.featured_suplements),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            items(products) { product ->
                ProductCard(product)
            }
        }
    }
}

@Composable
fun HeroBanner() {
    Box(
        modifier = Modifier
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
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column {
                Text(
                    text = "Start simple\nwith everyday\nsupplements",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 26.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Whey, creatine and essentials\nfor your first routine.",
                    color = Color.White,
                    fontSize = 12.sp
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { /* TODO */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Shop now", color = Color.Black, fontWeight = FontWeight.Bold)
                }

            }
        }
    }
}

@Composable
fun ProductCard(product: Product) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(12.dp))
    ) {
        Column(
            modifier = Modifier.padding(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFF5F5F5)), contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = product.imageRes),
                    contentDescription = product.name,
                    modifier = Modifier.size(300.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = product.name,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                modifier = Modifier.height(36.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = product.price, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { /* TODO */ },
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
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar(
        containerColor = Color.White, tonalElevation = 8.dp
    ) {
        NavigationBarItem(icon = { Icon(Icons.Outlined.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = true,
            onClick = { /* TODO */ },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.Black, indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(icon = { Icon(Icons.Outlined.ShoppingCart, contentDescription = "Cart") },
            label = { Text("Cart") },
            selected = false,
            onClick = { navController.navigate("cart")},
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = Color.Gray, unselectedTextColor = Color.Gray
            )
        )
    }
}