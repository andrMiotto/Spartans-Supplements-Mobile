package com.example.spartans_supplements_sobile.network

import com.example.spartans_supplements_sobile.model.dto.produto.ProdutoRequest
import com.example.spartans_supplements_sobile.model.dto.produto.ProdutoResponse
import com.example.spartans_supplements_sobile.model.dto.usuario.LoginRequest
import com.example.spartans_supplements_sobile.model.dto.usuario.UsuarioRequest
import com.example.spartans_supplements_sobile.model.dto.usuario.UsuarioResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Body

interface ApiService {
    @GET("user/list/{id}")
    suspend fun getUserById(
        @Path("id") id: Long
    ): Response<UsuarioResponse>

    @POST("user/register")
    suspend fun createUser(
        @Body user: UsuarioRequest
    ): Response<UsuarioResponse>

    @POST("user/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<Boolean>

    @PUT("user/update/{id}")
    suspend fun updateUser(
        @Path("id") id: Long,
        @Body user: UsuarioRequest
    ): Response<UsuarioResponse>

    @DELETE("user/delete/{id}")
    suspend fun deleteUser(
        @Path("id") id: Long
    ): Response<String>

    // ================= PRODUTO =================

    @POST("products/register")
    suspend fun createProduct(
        @Body product: ProdutoRequest
    ): Response<ProdutoResponse>

    @GET("products/list")
    suspend fun listProducts(): Response<List<ProdutoResponse>>

    @GET("products/list/{id}")
    suspend fun getProductById(
        @Path("id") id: Long
    ): Response<ProdutoResponse>

    @PUT("products/update/{id}")
    suspend fun updateProduct(
        @Path("id") id: Long,
        @Body product: ProdutoRequest
    ): Response<ProdutoResponse>

    @DELETE("products/delete/{id}")
    suspend fun deleteProduct(
        @Path("id") id: Long
    ): Response<Unit>


}