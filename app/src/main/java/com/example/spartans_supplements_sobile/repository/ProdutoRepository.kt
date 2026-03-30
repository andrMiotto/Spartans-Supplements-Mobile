package com.example.spartans_supplements_sobile.repository

import com.example.spartans_supplements_sobile.model.dto.produto.ProdutoRequest
import com.example.spartans_supplements_sobile.model.dto.produto.ProdutoResponse
import com.example.spartans_supplements_sobile.model.dto.usuario.UsuarioRequest
import com.example.spartans_supplements_sobile.model.dto.usuario.UsuarioResponse
import com.example.spartans_supplements_sobile.network.RetrofitClient

class ProdutoRepository {

    suspend fun list(): List<ProdutoResponse>? {
        val response = RetrofitClient.apiService.listProducts()
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun getById(id: Long): ProdutoResponse? {
        val response = RetrofitClient.apiService.getProductById(id)
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun create(produto: ProdutoRequest): ProdutoResponse? {
        val response = RetrofitClient.apiService.createProduct(produto)
        return if (response.isSuccessful) response.body() else null
    }
    suspend fun delete(id: Long): Boolean {
        val response = RetrofitClient.apiService.deleteProduct(id)
        return response.isSuccessful
    }
    suspend fun update(id: Long, produto: ProdutoRequest): Boolean {
        return try {
            val response = RetrofitClient.apiService.updateProduct(id, produto)
            response.isSuccessful
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }


}