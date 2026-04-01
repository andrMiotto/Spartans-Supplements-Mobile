package com.example.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.example.spartans_supplements_sobile.R
import com.example.spartans_supplements_sobile.model.dto.usuario.LoginRequest
import com.example.spartans_supplements_sobile.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenFuntion(navController: NavHostController) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

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
            Spacer(modifier = Modifier.height(80.dp))

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(id = R.string.cd_logo_spartans),
                modifier = Modifier.height(80.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

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
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = stringResource(id = R.string.login_welcome_back),
                            fontSize = 28.sp, fontWeight = FontWeight(700)
                        )
                        Spacer(modifier = Modifier.height(7.dp))
                        Text(
                            text = stringResource(id = R.string.login_subtitle),
                            fontSize = 17.sp,
                            color = Color.Gray
                        )
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    Text(
                        text = stringResource(id = R.string.login_email_label),
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = FontWeight(600)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = { Text(stringResource(id = R.string.login_email_placeholder)) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    Text(
                        text = stringResource(id = R.string.login_password_label),
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = FontWeight(600)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        placeholder = { Text(stringResource(id = R.string.login_password_placeholder)) },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val icon = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                            Icon(
                                imageVector = icon,
                                contentDescription = stringResource(id = R.string.cd_toggle_password),
                                modifier = Modifier.clickable { passwordVisible = !passwordVisible }
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = stringResource(id = R.string.login_forgot_password),
                        modifier = Modifier.align(Alignment.End),
                        fontSize = 12.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    val context = LocalContext.current
                    val scope = rememberCoroutineScope()

                    Button(
                        onClick = {
                            scope.launch {
                                try {
                                    val response = withContext(Dispatchers.IO) {
                                        RetrofitClient.apiService.login(LoginRequest(email, password))
                                    }

                                    if (response.isSuccessful && response.body() == true) {
                                        // Busca todos os usuários e filtra pelo email
                                        val usuariosResponse = withContext(Dispatchers.IO) {
                                            RetrofitClient.apiService.listUsers()
                                        }

                                        if (usuariosResponse.isSuccessful) {
                                            val usuario = usuariosResponse.body()?.find { it.email == email }
                                            if (usuario != null) {
                                                // Salva o ID no SharedPreferences
                                                val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                                                prefs.edit().putLong("usuario_id", usuario.id).apply()
                                                println("LOG: Usuário logado com ID: ${usuario.id}")
                                            }
                                        }

                                        withContext(Dispatchers.Main) {
                                            Toast.makeText(context, context.getString(R.string.login_toast_success), Toast.LENGTH_SHORT).show()
                                        }
                                        navController.navigate("home")

                                    } else {
                                        Log.e("LOGIN", "Email ou senha incorretos")
                                        withContext(Dispatchers.Main) {
                                            Toast.makeText(context, context.getString(R.string.login_toast_error), Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                } catch (e: Exception) {
                                    Log.e("LOGIN", "Erro: ${e.message}")
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                    ) {
                        Text(stringResource(id = R.string.btn_sign_in), color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(id = R.string.login_no_account))
                        Button(
                            onClick = { navController.navigate("register") },
                            modifier = Modifier.height(27.dp).width(65.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.TopCenter
                            ) {
                                Text(text = stringResource(id = R.string.btn_register), color = Color.Blue)
                            }
                        }
                    }
                }
            }
        }
    }
}