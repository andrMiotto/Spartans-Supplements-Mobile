package com.example.spartans_supplements_sobile.model.dto.usuario

import com.google.gson.annotations.SerializedName

data class UsuarioRequest(
    @SerializedName("nome") val nome: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("telefone") val telefone: String,
    @SerializedName("endereco") val endereco: String,
    @SerializedName("cpf") val cpf: String,
    @SerializedName("dataNascimento") val dataNascimento: String

)