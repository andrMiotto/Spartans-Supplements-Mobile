package com.example.spartans_supplements_sobile.repository

import com.example.spartans_supplements_sobile.model.dto.usuario.UsuarioRequest
import com.example.spartans_supplements_sobile.model.dto.usuario.UsuarioResponse
import com.example.spartans_supplements_sobile.network.RetrofitClient

class UsuarioRepository {

    suspend fun getUserById(id: Long): UsuarioResponse? {
        return try {
            val response = RetrofitClient.apiService.getUserById(id)
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun createUser(user: UsuarioRequest): UsuarioResponse? {
        val response = RetrofitClient.apiService.createUser(user)

        if (response.isSuccessful) {
            return response.body()
        }

        return null
    }
}
