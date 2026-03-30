package com.example.spartans_supplements_sobile.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.spartans_supplements_sobile.ui.viewModel.ProdutoViewModel
import com.example.spartans_supplements_sobile.model.dto.produto.ProdutoRequest

private val Black = Color(0xFF0D0D0D)
private val OffWhite = Color(0xFFF8F7F4)
private val LightGray = Color(0xFFE8E8E8)
private val MediumGray = Color(0xFF9E9E9E)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateProductScreen(
    navController: NavHostController,
    id: Long,
    viewModel: ProdutoViewModel = viewModel()
) {
    var nome by remember { mutableStateOf("") }
    var preco by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var imagemUrl by remember { mutableStateOf("") }
    var estoque by remember { mutableStateOf("") }
    var calorias by remember { mutableStateOf("") }
    var proteinas by remember { mutableStateOf("") }
    var carboidratos by remember { mutableStateOf("") }

    LaunchedEffect(id) {
        viewModel.findById(id)
    }

    val produtoCarregado = viewModel.produto
    LaunchedEffect(produtoCarregado) {
        produtoCarregado?.let {
            nome = it.nome
            preco = it.preco.toString()
            descricao = it.descricao
            peso = it.peso.toString()
            categoria = it.categoria
            imagemUrl = it.imagemUrl ?: ""
            estoque = it.quantidadeEstoque.toString()
            calorias = it.calorias.toString()
            proteinas = it.proteinas.toString()
            carboidratos = it.carboidratos.toString()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Box(modifier = Modifier.size(38.dp).clip(CircleShape).background(LightGray), contentAlignment = Alignment.Center) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Voltar", tint = Black)
                        }
                    }
                },
                title = { Text("Editar Produto", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Black) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = OffWhite)
            )
        },
        bottomBar = {
            Surface(color = OffWhite, shadowElevation = 12.dp) {
                Button(
                    onClick = {

                        fun String.toSafeDouble() = this.replace(",", ".").trim().toDoubleOrNull() ?: 0.0


                        val produtoEditado = ProdutoRequest(
                            nome = nome,
                            preco = preco.toSafeDouble(),
                            descricao = descricao,
                            peso = peso.toSafeDouble(),
                            categoria = categoria,
                            imagemUrl = imagemUrl,
                            quantidadeEstoque = estoque.toIntOrNull() ?: 0,
                            calorias = calorias.toSafeDouble(),
                            proteinas = proteinas.toSafeDouble(),
                            carboidratos = carboidratos.toSafeDouble()
                        )


                        viewModel.atualizarProduto(id, produtoEditado) {

                            navController.popBackStack()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Black),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().padding(20.dp).height(52.dp)
                ) {
                    Icon(Icons.Default.Done, contentDescription = null, modifier = Modifier.size(20.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Salvar Alterações", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        },
        containerColor = OffWhite
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(horizontal = 20.dp).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            CustomTextField(value = nome, onValueChange = { nome = it }, label = "Nome do Produto")

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                CustomTextField(value = preco, onValueChange = { preco = it }, label = "Preço (R$)", modifier = Modifier.weight(1f), keyboardType = KeyboardType.Decimal)
                CustomTextField(value = estoque, onValueChange = { estoque = it }, label = "Estoque", modifier = Modifier.weight(1f), keyboardType = KeyboardType.Number)
            }

            CustomTextField(value = categoria, onValueChange = { categoria = it }, label = "Categoria")
            CustomTextField(value = descricao, onValueChange = { descricao = it }, label = "Descrição", singleLine = false, minLines = 3)
            CustomTextField(value = imagemUrl, onValueChange = { imagemUrl = it }, label = "URL da Imagem")

            HorizontalDivider(color = LightGray, modifier = Modifier.padding(vertical = 8.dp))
            Text("Especificações Técnicas", fontWeight = FontWeight.Bold, color = Black)

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                CustomTextField(value = peso, onValueChange = { peso = it }, label = "Peso (kg)", modifier = Modifier.weight(1f), keyboardType = KeyboardType.Decimal)
                CustomTextField(value = calorias, onValueChange = { calorias = it }, label = "Calorias", modifier = Modifier.weight(1f), keyboardType = KeyboardType.Decimal)
            }

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                CustomTextField(value = proteinas, onValueChange = { proteinas = it }, label = "Proteínas (g)", modifier = Modifier.weight(1f), keyboardType = KeyboardType.Decimal)
                CustomTextField(value = carboidratos, onValueChange = { carboidratos = it }, label = "Carbos (g)", modifier = Modifier.weight(1f), keyboardType = KeyboardType.Decimal)
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    minLines: Int = 1
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, fontSize = 14.sp) },
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Black,
            unfocusedBorderColor = LightGray,
            focusedLabelColor = Black,
            unfocusedLabelColor = MediumGray,
            cursorColor = Black
        ),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = singleLine,
        minLines = minLines
    )
}