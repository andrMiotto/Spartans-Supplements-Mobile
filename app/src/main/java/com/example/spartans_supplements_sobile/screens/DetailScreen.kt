import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
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

private val Black = Color(0xFF0D0D0D)
private val OffWhite = Color(0xFFF8F7F4)
private val LightGray = Color(0xFFE8E8E8)
private val MediumGray = Color(0xFF9E9E9E)
private val TagBg = Color(0xFFF0EDE8)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavHostController,
    id: Long,
    viewModel: ProdutoViewModel = viewModel()
) {
    var quantity by remember { mutableStateOf(1) }

    LaunchedEffect(id) {
        viewModel.findById(id)
    }

    val produto = viewModel.produto

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(LightGray),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Black,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                },
                title = {
                    Text(
                        text = stringResource(R.string.title_details),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Black
                    )
                },
                actions = {
                    IconButton(onClick = { navController.navigate("update_product/$id") }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = Black
                        )
                    }
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Outlined.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = Black
                        )
                    }
                    IconButton(onClick = { navController.navigate("cart") }) {
                        Icon(
                            imageVector = Icons.Outlined.ShoppingCart,
                            contentDescription = "Cart",
                            tint = Black
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = OffWhite)
            )
        },

        bottomBar = {
            Surface(color = OffWhite, shadowElevation = 12.dp) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Row(
                        modifier = Modifier
                            .border(1.5.dp, LightGray, RoundedCornerShape(12.dp))
                            .padding(horizontal = 12.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Text(text = "−", fontSize = 20.sp, fontWeight = FontWeight.Bold,
                            color = if (quantity > 1) Black else MediumGray,
                            modifier = Modifier.clickable { if (quantity > 1) quantity-- })
                        Text(
                            text = "$quantity",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Black
                        )
                        Text(text = "+",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Black,
                            modifier = Modifier.clickable { quantity++ })
                    }


                    Button(
                        onClick = {
                            produto?.let { p ->
                                viewModel.adicionarAoCarrinho(p.id, quantity)
                                navController.navigate("cart")
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Black),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.btn_add_to_cart),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        },

        containerColor = OffWhite
    ) { paddingValues ->
        if (produto == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Black)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFFEDEBE6),
                                    Color(0xFFF8F7F4)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = produto.imagemUrl,
                        contentDescription = produto.nome,
                        modifier = Modifier
                            .size(199.dp)
                            .align(Alignment.Center),
                        contentScale = ContentScale.Fit
                    )
                }

                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Tag(text = produto.categoria)
                        Tag(text = "${produto.peso}kg")
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = stringResource(R.string.brand_name),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red,
                        letterSpacing = 2.sp
                    )


                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = produto.nome,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Black,
                        color = Black,
                        lineHeight = 32.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = stringResource(R.string.price_brl, "%.2f".format(produto.preco)),
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Black,
                        color = Black
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(if (produto.quantidadeEstoque > 0) Color(0xFF2E7D32) else Color.Red)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = if (produto.quantidadeEstoque > 0) stringResource(
                                R.string.in_stock,
                                produto.quantidadeEstoque
                            ) else stringResource(R.string.no_stock),
                            fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    HorizontalDivider(color = LightGray, thickness = 1.dp)
                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = stringResource(R.string.title_description),
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = produto.descricao,
                        fontSize = 14.sp,
                        color = MediumGray,
                        lineHeight = 22.sp
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = stringResource(R.string.title_informations),
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Black
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        NutritionCard(
                            label = stringResource(R.string.info_calories),
                            value = "%.0f".format(produto.calorias),
                            modifier = Modifier.weight(1f)
                        )
                        NutritionCard(
                            label = stringResource(R.string.info_carbs),
                            value = "%.0f".format(produto.carboidratos),
                            modifier = Modifier.weight(1f)
                        )
                        NutritionCard(
                            label = stringResource(R.string.info_proteins),
                            value = "%.0f".format(produto.proteinas),
                            modifier = Modifier.weight(1f)
                        )

                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        NutritionCard(
                            label = "Calories",
                            value = "${"%.0f".format(produto.calorias)}",
                            modifier = Modifier.weight(1f)
                        )
                        NutritionCard(
                            label = "Carbs",
                            value = "${"%.0f".format(produto.carboidratos)}",
                            modifier = Modifier.weight(1f)
                        )
                        NutritionCard(
                            label = "Proteins",
                            value = "${"%.0f".format(produto.proteinas)}",
                            modifier = Modifier.weight(1f)
                        )


                    }
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

@Composable
private fun Tag(text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(TagBg)
            .padding(horizontal = 12.dp, vertical = 5.dp)
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF555555)
        )
    }
}

@Composable
private fun NutritionCard(label: String, value: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(TagBg)
            .padding(vertical = 14.dp),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        Text(text = value, fontSize = 20.sp, fontWeight = FontWeight.Black, color = Black)
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = label, fontSize = 11.sp, color = MediumGray, fontWeight = FontWeight.Medium)
    }
}