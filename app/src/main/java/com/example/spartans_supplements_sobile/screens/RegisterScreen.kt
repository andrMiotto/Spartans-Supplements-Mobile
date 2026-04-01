package com.example.spartans_supplements_sobile.screens

import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.spartans_supplements_sobile.R
import kotlinx.coroutines.launch
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
                contentDescription = stringResource(id = R.string.cd_logo_spartans),
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
                            text = stringResource(id = R.string.auth_welcome_back),
                            fontSize = 28.sp, fontWeight = FontWeight(700),
                        )
                        Spacer(modifier = Modifier.height(7.dp))
                        Text(
                            text = stringResource(id = R.string.auth_sign_in_subtitle),
                            fontSize = 17.sp,
                            color = Color.Gray
                        )
                    }


                    LazyColumn {

                        item {
                            Spacer(modifier = Modifier.height(25.dp))

                            Text(
                                text = stringResource(id = R.string.label_name),
                                modifier = Modifier.fillMaxWidth(),
                                fontWeight = FontWeight(600)
                            )
                            Spacer(modifier = Modifier.height(10.dp),)
                            OutlinedTextField(

                                value = nome,
                                onValueChange = { nome = it },
                                placeholder = { Text(stringResource(id = R.string.placeholder_name)) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp)
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(20.dp),)
                            Text(
                                text = stringResource(id = R.string.label_email),
                                modifier = Modifier.fillMaxWidth(),
                                fontWeight = FontWeight(600)
                            )
                            Spacer(modifier = Modifier.height(10.dp),)
                            OutlinedTextField(

                                value = email,
                                onValueChange = { email = it },
                                placeholder = { Text(stringResource(id = R.string.placeholder_email)) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp)
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(25.dp))

                            Text(
                                text = stringResource(id = R.string.label_phone),
                                modifier = Modifier.fillMaxWidth(),
                                fontWeight = FontWeight(600)
                            )
                            Spacer(modifier = Modifier.height(10.dp),)
                            OutlinedTextField(

                                value = telefone,
                                onValueChange = { telefone = it },
                                placeholder = { Text(stringResource(id = R.string.placeholder_phone)) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp)
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(25.dp))

                            Text(
                                text = stringResource(id = R.string.label_address),
                                modifier = Modifier.fillMaxWidth(),
                                fontWeight = FontWeight(600)
                            )
                            Spacer(modifier = Modifier.height(10.dp),)
                            OutlinedTextField(

                                value = endereco,
                                onValueChange = { endereco = it },
                                placeholder = { Text(stringResource(id = R.string.placeholder_address)) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp)
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(25.dp))

                            Text(
                                text = stringResource(id = R.string.label_cpf),
                                modifier = Modifier.fillMaxWidth(),
                                fontWeight = FontWeight(600)
                            )
                            Spacer(modifier = Modifier.height(10.dp),)
                            OutlinedTextField(

                                value = cpf,
                                onValueChange = { cpf = it },
                                placeholder = { Text(stringResource(id = R.string.placeholder_cpf)) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp)
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(25.dp))

                            Text(
                                text = stringResource(id = R.string.label_birthdate),
                                modifier = Modifier.fillMaxWidth(),
                                fontWeight = FontWeight(600)
                            )
                            Spacer(modifier = Modifier.height(10.dp),)
                            OutlinedTextField(

                                value = dataNascimento,
                                onValueChange = { dataNascimento = it },
                                placeholder = { Text(stringResource(id = R.string.placeholder_birthdate)) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp)
                            )
                        }


                        item {
                            Spacer(modifier = Modifier.height(20.dp),)
                            Text(
                                text = stringResource(id = R.string.label_password),
                                modifier = Modifier.fillMaxWidth(),
                                fontWeight = FontWeight(600)
                            )
                            Spacer(modifier = Modifier.height(10.dp),)
                            OutlinedTextField(
                                value = password,
                                onValueChange = { password = it },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                placeholder = { Text(stringResource(id = R.string.placeholder_password)) },
                                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                trailingIcon = {
                                    val icon =
                                        if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = stringResource(id = R.string.cd_toggle_password),
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
                                text = stringResource(id = R.string.label_confirm_password),
                                modifier = Modifier.fillMaxWidth(),
                                fontWeight = FontWeight(600)
                            )
                            Spacer(modifier = Modifier.height(10.dp),)
                            OutlinedTextField(
                                value = passwordC,
                                onValueChange = { passwordC = it },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                placeholder = { Text(stringResource(id = R.string.placeholder_password)) },
                                visualTransformation = if (passwordVisibleC) VisualTransformation.None else PasswordVisualTransformation(),
                                trailingIcon = {
                                    val icon =
                                        if (passwordVisibleC) Icons.Default.Visibility else Icons.Default.VisibilityOff
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = stringResource(id = R.string.cd_toggle_password),
                                        modifier = Modifier.clickable {
                                            passwordVisibleC = !passwordVisibleC
                                        }
                                    )
                                }
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = stringResource(id = R.string.forgot_password),
                                modifier = Modifier.align(Alignment.End),
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                        }

                        item {

                            val scope = rememberCoroutineScope()
                            val localContext = LocalContext.current
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
                                                    Toast.makeText(localContext, localContext.getString(R.string.toast_register_success), Toast.LENGTH_SHORT).show()
                                                    navController.navigate("home")
                                                }

                                            } else {
                                                val error = response.errorBody()?.string()
                                                println("Erro API: $error")

                                                withContext(Dispatchers.Main) {
                                                    Toast.makeText(localContext, localContext.getString(R.string.toast_register_error), Toast.LENGTH_SHORT).show()
                                                }

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
                                Text(stringResource(id = R.string.btn_register), color = Color.White)
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(stringResource(id = R.string.auth_no_account))
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
                                            text = stringResource(id = R.string.btn_login),
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