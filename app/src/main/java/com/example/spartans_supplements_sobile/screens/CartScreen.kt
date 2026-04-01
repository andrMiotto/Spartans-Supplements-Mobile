package com.example.spartans_supplements_sobile.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.spartans_supplements_sobile.ui.viewModel.ProdutoViewModel

@Composable
fun CartScreen(navController: NavHostController, viewModel: ProdutoViewModel) {

    val cartItems by viewModel.cartItems.collectAsState()

    Scaffold(
        containerColor = Color(0xFFF7F8FA),
        bottomBar = { AppBottomNavigationBar(navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 30.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "Cart",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1C1E)
                )
                Spacer(modifier = Modifier.width(8.dp))
                val totalItens = cartItems.sumOf { it.quantity }
                Text(
                    text = "($totalItens items)",
                    color = Color.Gray,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            if (cartItems.isEmpty()) {

                Box(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.ShoppingCart, null, modifier = Modifier.size(64.dp), tint = Color.LightGray)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Your cart is empty", fontSize = 18.sp, color = Color.Gray)
                        TextButton(onClick = { navController.navigate("home") }) {
                            Text("Keep buying", color = Color(0xFF0D6B39))
                        }
                    }
                }
            } else {

                LazyColumn(
                    modifier = Modifier.weight(1f).padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 20.dp)
                ) {
                    items(cartItems) { item ->
                        CartItemCard(
                            name = item.name,

                            priceTotal = item.price * item.quantity,
                            imageUrl = item.imageUrl ?: "",
                            quantity = item.quantity,

                            onIncrease = { viewModel.alterarQuantidade(item.id, true) },
                            onDecrease = { viewModel.alterarQuantidade(item.id, false) },
                            onRemove = { viewModel.removerDoCarrinho(item.id) }
                        )
                    }
                }
            }


            val totalFinanceiro = cartItems.sumOf { it.price * it.quantity }
            CartSummarySection(totalFinanceiro.toString(), navController)
        }
    }
}

@Composable
fun CartItemCard(
    name: String,
    priceTotal: Double,
    imageUrl: String,
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onRemove: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        shadowElevation = 1.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(80.dp),
                color = Color(0xFFF2F2F2),
                shape = RoundedCornerShape(12.dp)
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = name,
                    modifier = Modifier.fillMaxSize().padding(8.dp)
                )
            }

            Column(
                modifier = Modifier.padding(start = 16.dp).weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(name, fontWeight = FontWeight.Bold, fontSize = 16.sp, maxLines = 1)
                    IconButton(onClick = onRemove, modifier = Modifier.size(24.dp)) {
                        Icon(Icons.Default.Delete, "Remove", tint = Color.LightGray)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "R$ ${"%.2f".format(priceTotal)}",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp
                    )


                    Surface(
                        color = Color(0xFFF5F5F5),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {

                            Text(
                                text = "−",
                                fontWeight = FontWeight.Bold,
                                color = if (quantity > 1) Color.Black else Color.Gray,
                                modifier = Modifier.clickable { onDecrease() }
                            )

                            Text(text = quantity.toString(), fontWeight = FontWeight.Bold)


                            Text(
                                text = "+",
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                modifier = Modifier.clickable { onIncrease() }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CartSummarySection(totalPrice: String, navController: NavHostController) {

    val formattedTotal = "%.2f".format(totalPrice.toDoubleOrNull() ?: 0.0)

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        shadowElevation = 12.dp
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            SummaryRow("Subtotal", "R$ $formattedTotal")
            SummaryRow("Shipping", "Free")

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp), color = Color(0xFFEEEEEE))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Total", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text("$ $formattedTotal", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    navController.navigate("checkout")
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D6B39)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Proceed to Checkout", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Default.KeyboardArrowRight, null)
                }
            }
        }
    }
}

@Composable
fun SummaryRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Color.Gray, fontSize = 16.sp)
        Text(value, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = Color.Gray)
    }
}

@Composable
fun AppBottomNavigationBar(navController: NavHostController) {
    NavigationBar(containerColor = Color.White, tonalElevation = 8.dp) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, "Home") },
            label = { Text("Home") },
            selected = false,
            onClick = { navController.navigate("home") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.ShoppingCart, "Cart") },
            label = { Text("Cart") },
            selected = true,
            onClick = {}
        )
    }
}