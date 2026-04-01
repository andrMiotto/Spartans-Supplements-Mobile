package com.example.spartans_supplements_sobile.model.dto.carrinho

data class CarrinhoResponse(
    val id: Long,
    val total: Double,
    val itens: List<ItemCarrinhoResponse>
)