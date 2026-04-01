package com.example.spartans_supplements_sobile.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.spartans_supplements_sobile.ui.viewModel.ProdutoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun CheckoutScreenFunction(viewModel: ProdutoViewModel, navController: NavHostController) {

    val carrinho by viewModel.carrinho.collectAsState()
    val itens = carrinho?.itens ?: emptyList()

    var endereco by remember { mutableStateOf("") }
    var cep by remember { mutableStateOf("") }
    var metodoPagamento by remember { mutableStateOf("Card") }

    val totalProdutos = carrinho?.total ?: 0.0

    val frete = remember(totalProdutos) {
        if (totalProdutos > 0) {
            kotlin.random.Random.nextDouble(0.0, totalProdutos / 4)
        } else {
            0.0
        }
    }

    val totalFinal = totalProdutos + frete

    var showDialog by remember { mutableStateOf(false) }
    var enderecoSalvo by remember { mutableStateOf<String?>(null) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Back"
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Checkout",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Your products", fontWeight = FontWeight.Bold)

        LazyColumn(
            modifier = Modifier.height(200.dp)
        ) {
            items(itens) { item ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {


                    AsyncImage(
                        model = item.produto.imagemUrl,
                        contentDescription = item.produto.nome,
                        modifier = Modifier
                            .size(60.dp)
                            .padding(4.dp)
                    )

                    Spacer(modifier = Modifier.width(12.dp))


                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            text = item.produto.nome,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )

                        Text(
                            text = "Qty: ${item.quantidade}",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }


                    Text(
                        text = "$ %.2f".format(item.produto.preco * item.quantidade),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))


        Text("Payment method", fontWeight = FontWeight.Bold)

        Row {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = metodoPagamento == "Card",
                    onClick = { metodoPagamento = "Card" },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color(0xFF0D6B39),
                        unselectedColor = Color.LightGray
                    )
                )
                Text("Card")
            }
            Spacer(modifier = Modifier.width(20.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = metodoPagamento == "Pix",
                    onClick = { metodoPagamento = "Pix" },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color(0xFF0D6B39),
                        unselectedColor = Color.LightGray
                    )
                )
                Text("Pix")
            }
            Spacer(modifier = Modifier.width(20.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = metodoPagamento == "Invoice",
                    onClick = { metodoPagamento = "Invoice" },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color(0xFF0D6B39),
                        unselectedColor = Color.LightGray
                    )
                )
                Text("Invoice")
            }
        }


        Spacer(modifier = Modifier.height(20.dp))

        Text("Address", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { showDialog = true },
            modifier = Modifier

                .height(36.dp).width(130.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6C757D)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Add Address")
        }
        enderecoSalvo?.let {
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = it,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )
        }

        if (showDialog) {

            var logradouro by remember { mutableStateOf("") }
            var numero by remember { mutableStateOf("") }
            var bairro by remember { mutableStateOf("") }
            var cidade by remember { mutableStateOf("") }
            var estado by remember { mutableStateOf("") }
            var cep by remember { mutableStateOf("") }

            AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {

                    Button(onClick = {

                        enderecoSalvo =
                            "$logradouro, $numero - $bairro, $cidade - $estado (CEP: $cep)"

                        showDialog = false
                    },
                        modifier = Modifier

                            .height(36.dp).width(90.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D6B39)),
                        shape = RoundedCornerShape(12.dp)) {
                        Text("Save")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false },colors = ButtonDefaults.textButtonColors(
                        contentColor = Color.Gray
                    ) ) {
                        Text("Cancel")
                    }
                },
                title = {
                    Text("Add Address")
                },
                text = {

                    Column {

                        OutlinedTextField(
                            value = logradouro,
                            onValueChange = { logradouro = it },
                            label = { Text("Street") }
                        )

                        OutlinedTextField(
                            value = numero,
                            onValueChange = { numero = it },
                            label = { Text("Number") }
                        )

                        OutlinedTextField(
                            value = bairro,
                            onValueChange = { bairro = it },
                            label = { Text("Neighborhood") }
                        )

                        OutlinedTextField(
                            value = cidade,
                            onValueChange = { cidade = it },
                            label = { Text("City") }
                        )

                        OutlinedTextField(
                            value = estado,
                            onValueChange = { estado = it },
                            label = { Text("State") }
                        )

                        OutlinedTextField(
                            value = cep,
                            onValueChange = { cep = it },
                            label = { Text("ZIP Code") }
                        )
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(120.dp))


        Text("Shipping: $ %.2f".format(frete))

        Spacer(modifier = Modifier.height(6.dp))
        Text(
            "Total: $ %.2f".format(totalFinal),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(25.dp))

        Button(
            onClick = {
                println("Finalizando compra com ${itens.size} itens")
                navController.navigate("success")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D6B39)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Complete Purchase" , fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.Default.KeyboardArrowRight, null)
        }
    }
}

@Composable
fun SuccessScreen(navController: NavHostController) {


    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(2500)

        withContext(Dispatchers.Main) {
            navController.navigate("home")
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Sucesso",
                tint = Color(0xFF0D6B39),
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Payment successful!",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Redirecting to home...",
                color = Color.Gray
            )
        }
    }
}