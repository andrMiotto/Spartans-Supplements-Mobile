package com.example.spartans_supplements_sobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import com.example.login.LoginScreenFuntion
import com.example.spartans_supplements_sobile.screens.CartScreen
import com.example.spartans_supplements_sobile.screens.DetailScreen
import com.example.spartans_supplements_sobile.screens.RegisterScreenFuntion
import com.example.spartans_supplements_sobile.screens.StoreHomeScreen
import com.example.spartans_supplements_sobile.ui.theme.SpartansSupplementsSobileTheme

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

                    NavHost(navController = navController, startDestination = "home") {

                        composable("login") {
                            LoginScreenFuntion(navController)
                        }

                        composable("register") {
                            RegisterScreenFuntion(navController)
                        }

                        composable("home") {
                            StoreHomeScreen(navController)
                        }

                        composable("cart") {
                            CartScreen(navController)
                        }

                        composable("detail/{id}") { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("id")?.toLong() ?: 0L
                            DetailScreen(navController, id)
                        }
                    }
                }
            }
        }
    }
}