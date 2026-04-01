package com.example.spartans_supplements_sobile.model.dto.carrinho

import com.example.spartans_supplements_sobile.model.dto.produto.ProdutoRequest
import com.example.spartans_supplements_sobile.model.dto.produto.ProdutoResponse

data class ItemCarrinhoResponse (
    val id: Long,
    val produto: ProdutoResponse,
    val quantidade: Int
)