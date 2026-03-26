package com.example.spartans_supplements_sobile.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.spartans_supplements_sobile.R
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import com.example.spartans_supplements_sobile.model.dto.usuario.UsuarioRequest
import com.example.spartans_supplements_sobile.network.RetrofitClient
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Composable
fun RegisterScreenFuntion(navController: NavHostController) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    var nome by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var endereco by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }
    var dataNascimento by remember { mutableStateOf("") }
    var passwordC by remember { mutableStateOf("") }
    var passwordVisibleC by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            Spacer(modifier = Modifier.height(20.dp))

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo Spartans",
                modifier = Modifier.height(80.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()

            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(7.dp))
                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Text(
                            text = "Welcome Back",
                            fontSize = 28.sp, fontWeight = FontWeight(700),

                            )
                        Spacer(modifier = Modifier.height(7.dp))
                        Text(
                            text = "Sign in to continue shopping",
                            fontSize = 17.sp,
                            color = Color.Gray
                        )
                    }


                    LazyColumn {

                        item {
                            Spacer(modifier = Modifier.height(25.dp))

                            Text(
                                text = "Name",
                                modifier = Modifier.fillMaxWidth(),
                                fontWeight = FontWeight(600)
                            )
                            Spacer(modifier = Modifier.height(10.dp),)
                            OutlinedTextField(

                                value = nome,
                                onValueChange = { nome = it },
                                placeholder = { Text("Nome") },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp)
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(20.dp),)
                            Text(
                                text = "Email address",
                                modifier = Modifier.fillMaxWidth(),
                                fontWeight = FontWeight(600)
                            )
                            Spacer(modifier = Modifier.height(10.dp),)
                            OutlinedTextField(

                                value = email,
                                onValueChange = { email = it },
                                placeholder = { Text("name@example.com") },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp)
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(25.dp))

                            Text(
                                text = "Telefone",
                                modifier = Modifier.fillMaxWidth(),
                                fontWeight = FontWeight(600)
                            )
                            Spacer(modifier = Modifier.height(10.dp),)
                            OutlinedTextField(

                                value = telefone,
                                onValueChange = { telefone = it },
                                placeholder = { Text("47 999999-999") },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp)
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(25.dp))

                            Text(
                                text = "Endereço",
                                modifier = Modifier.fillMaxWidth(),
                                fontWeight = FontWeight(600)
                            )
                            Spacer(modifier = Modifier.height(10.dp),)
                            OutlinedTextField(

                                value = endereco,
                                onValueChange = { endereco = it },
                                placeholder = { Text("Rua Exemplo , Cidade , Estado") },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp)
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(25.dp))

                            Text(
                                text = "CPF",
                                modifier = Modifier.fillMaxWidth(),
                                fontWeight = FontWeight(600)
                            )
                            Spacer(modifier = Modifier.height(10.dp),)
                            OutlinedTextField(

                                value = cpf,
                                onValueChange = { cpf = it },
                                placeholder = { Text("123.456.789-10") },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp)
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(25.dp))

                            Text(
                                text = "Data Nascimento",
                                modifier = Modifier.fillMaxWidth(),
                                fontWeight = FontWeight(600)
                            )
                            Spacer(modifier = Modifier.height(10.dp),)
                            OutlinedTextField(

                                value = dataNascimento,
                                onValueChange = { dataNascimento = it },
                                placeholder = { Text("02/02/2000") },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp)
                            )
                        }


                        item {
                            Spacer(modifier = Modifier.height(20.dp),)
                            Text(
                                text = "Password",
                                modifier = Modifier.fillMaxWidth(),
                                fontWeight = FontWeight(600)
                            )
                            Spacer(modifier = Modifier.height(10.dp),)
                            OutlinedTextField(
                                value = password,
                                onValueChange = { password = it },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                placeholder = { Text("**********") },
                                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                trailingIcon = {
                                    val icon =
                                        if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = null,
                                        modifier = Modifier.clickable {
                                            passwordVisible = !passwordVisible
                                        }
                                    )
                                }
                            )
                        }


                        item {
                            Spacer(modifier = Modifier.height(20.dp),)

                            Text(
                                text = "Confirm Password",
                                modifier = Modifier.fillMaxWidth(),
                                fontWeight = FontWeight(600)
                            )
                            Spacer(modifier = Modifier.height(10.dp),)
                            OutlinedTextField(
                                value = passwordC,
                                onValueChange = { passwordC = it },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                placeholder = { Text("**********") },
                                visualTransformation = if (passwordVisibleC) VisualTransformation.None else PasswordVisualTransformation(),
                                trailingIcon = {
                                    val icon =
                                        if (passwordVisibleC) Icons.Default.Visibility else Icons.Default.VisibilityOff
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = null,
                                        modifier = Modifier.clickable {
                                            passwordVisibleC = !passwordVisibleC
                                        }
                                    )
                                }
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Forgot password?",
                                modifier = Modifier.align(Alignment.End),
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                        }

                        item {

                            val scope = rememberCoroutineScope()
                            Button(
                                onClick = {

                                    scope.launch {
                                        try {
                                            val response = RetrofitClient.apiService.createUser(
                                                UsuarioRequest(nome, email, password, telefone, endereco, cpf, dataNascimento)
                                            )

                                            println("Código: ${response.code()}")

                                            if (response.isSuccessful) {
                                                val user = response.body()
                                                println("Sucesso: $user")

                                                withContext(Dispatchers.Main) {
                                                    navController.navigate("home")
                                                }

                                            } else {
                                                val error = response.errorBody()?.string()
                                                println("Erro API: $error")

                                                Log.d("JSON", Gson().toJson(
                                                    UsuarioRequest(nome, email, password, telefone, endereco, cpf, dataNascimento)
                                                ))

                                            }

                                        } catch (e: Exception) {
                                            println("Erro EXCEPTION: ${e.message}")
                                            e.printStackTrace()
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Black
                                )
                            ) {
                                Text("Register", color = Color.White)
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Don't have an account? ")
                                Button(
                                    onClick = { navController.navigate("login") },
                                    modifier = Modifier
                                        .height(26.dp)
                                        .width(50.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.White
                                    ), contentPadding = PaddingValues(0.dp)
                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.TopCenter
                                    ) {
                                        Text(
                                            text = "Login",
                                            color = Color.Blue
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(36.dp))
                        }

                    }
                }
            }
        }
    }
}