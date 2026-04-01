package com.example.spartans_supplements_sobile

import DetailScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.login.LoginScreenFuntion
import com.example.spartans_supplements_sobile.screens.CartScreen
import com.example.spartans_supplements_sobile.screens.CheckoutScreenFunction
import com.example.spartans_supplements_sobile.screens.RegisterProductScreen
import com.example.spartans_supplements_sobile.screens.RegisterScreenFuntion
import com.example.spartans_supplements_sobile.screens.StoreHomeScreen
import com.example.spartans_supplements_sobile.screens.SuccessScreen
import com.example.spartans_supplements_sobile.screens.UpdateProductScreen
import com.example.spartans_supplements_sobile.ui.theme.SpartansSupplementsSobileTheme
import com.example.spartans_supplements_sobile.ui.viewModel.ProdutoViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpartansSupplementsSobileTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val produtoViewModel: ProdutoViewModel = viewModel()

                    NavHost(navController = navController, startDestination = "home") {
                        composable("login") {
                            LoginScreenFuntion(navController)
                        }
                        composable("register") {
                            RegisterScreenFuntion(navController)
                        }
                        composable("home") {
                            StoreHomeScreen(navController, produtoViewModel)
                        }
                        composable("cart") {
                            CartScreen(navController, produtoViewModel)
                        }
                        composable("register_product") {
                            RegisterProductScreen(navController, produtoViewModel)
                        }
                        composable("detail/{id}") { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("id")?.toLong() ?: 0L
                            DetailScreen(navController, id, produtoViewModel)
                        }
                        composable("checkout") {
                            CheckoutScreenFunction(produtoViewModel, navController)
                        }
                        composable("success") {
                            SuccessScreen(navController)
                        }
                        composable(
                            route = "update_product/{id}",
                            arguments = listOf(navArgument("id") { type = NavType.LongType })
                        ) { backStackEntry ->
                            val id = backStackEntry.arguments?.getLong("id") ?: 0L
                            UpdateProductScreen(navController, id, produtoViewModel)
                        }
                    }
                }
            }
        }
    }
}