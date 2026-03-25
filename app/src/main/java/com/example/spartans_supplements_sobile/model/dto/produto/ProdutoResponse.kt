package com.example.spartans_supplements_sobile.model.dto.produto

data class ProdutoResponse(
    val id: Long,
    val nome: String,
    val preco: Double,
    val descricao: String,
    val peso: Double,
    val categoria: String,
    val imagemUrl: String,
    val quantidadeEstoque: Int

)