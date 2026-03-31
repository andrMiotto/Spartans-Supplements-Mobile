package com.example.spartans_supplements_sobile.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spartans_supplements_sobile.data.CartItem
import com.example.spartans_supplements_sobile.model.dto.produto.ProdutoRequest
import com.example.spartans_supplements_sobile.model.dto.produto.ProdutoResponse
import com.example.spartans_supplements_sobile.repository.ProdutoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProdutoViewModel : ViewModel() {

    private val repository = ProdutoRepository()

    // --- ESTADO DOS PRODUTOS (API) ---
    var produto: ProdutoResponse? = null
    var produtos by mutableStateOf<List<ProdutoResponse>>(emptyList())
        private set

    // --- ESTADO DO CARRINHO (LOCAL) ---
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems = _cartItems.asStateFlow()

    // --- FUNÇÕES DO CARRINHO ---

    fun adicionarAoCarrinho(produto: ProdutoResponse) {
        _cartItems.update { listaAtual ->
            val itemExistente = listaAtual.find { it.id == produto.id }

            if (itemExistente != null) {
                // Se já existe, aumenta a quantidade
                listaAtual.map {
                    if (it.id == produto.id) it.copy(quantity = it.quantity + 1) else it
                }
            } else {
                // Se é novo, converte ProdutoResponse para CartItem
                val novoItem = CartItem(
                    id = produto.id,
                    name = produto.nome,
                    description = produto.descricao ?: "",
                    price = produto.preco,
                    quantity = 1,
                    imageUrl = produto.imagemUrl ?: "",
                    caloria = produto.calorias ?: 0.0,
                    proteina = produto.proteinas ?: 0.0,
                    carboidratos = produto.carboidratos ?: 0.0
                )
                listaAtual + novoItem
            }
        }
    }

    fun removerDoCarrinho(productId: Long) {
        _cartItems.update { lista ->
            lista.filter { it.id != productId }
        }
    }

    fun alterarQuantidade(productId: Long, aumentar: Boolean) {
        _cartItems.update { lista ->
            lista.map {
                if (it.id == productId) {
                    val novaQtd = if (aumentar) it.quantity + 1 else it.quantity - 1
                    it.copy(quantity = if (novaQtd > 0) novaQtd else 1)
                } else it
            }
        }
    }

    // --- FUNÇÕES DE CRUD (API) ---

    fun createProduct(produtoRequest: ProdutoRequest, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = repository.createProduct(produtoRequest)
                if (response != null) {
                    listarProdutos()
                    onSuccess()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun listarProdutos() {
        viewModelScope.launch {
            produtos = repository.list() ?: emptyList()
        }
    }

    fun deletarProduto(id: Long) {
        viewModelScope.launch {
            try {
                val sucesso = repository.delete(id)
                if (sucesso) {
                    listarProdutos()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun findById(id: Long) {
        viewModelScope.launch {
            produto = repository.getById(id)
        }
    }

    fun atualizarProduto(id: Long, produtoRequest: ProdutoRequest, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val sucesso = repository.update(id, produtoRequest)
                if (sucesso) {
                    listarProdutos()
                    onSuccess()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun adicionarAoCarrinho(produto: ProdutoResponse, qtdAAcrescentar: Int = 1) {
        _cartItems.update { listaAtual ->
            val itemExistente = listaAtual.find { it.id == produto.id }

            if (itemExistente != null) {

                listaAtual.map {
                    if (it.id == produto.id) {
                        it.copy(quantity = it.quantity + qtdAAcrescentar)
                    } else it
                }
            } else {

                val novoItem = CartItem(
                    id = produto.id,
                    name = produto.nome,
                    description = produto.descricao ?: "",
                    price = produto.preco,
                    quantity = qtdAAcrescentar,
                    imageUrl = produto.imagemUrl ?: "",
                    caloria = produto.calorias ?: 0.0,
                    proteina = produto.proteinas ?: 0.0,
                    carboidratos = produto.carboidratos ?: 0.0
                )
                listaAtual + novoItem
            }
        }
    }

}