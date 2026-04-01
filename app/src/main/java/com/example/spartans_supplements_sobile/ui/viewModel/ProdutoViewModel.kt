package com.example.spartans_supplements_sobile.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spartans_supplements_sobile.model.dto.carrinho.CarrinhoResponse
import com.example.spartans_supplements_sobile.model.dto.produto.ProdutoRequest
import com.example.spartans_supplements_sobile.model.dto.produto.ProdutoResponse
import com.example.spartans_supplements_sobile.network.RetrofitClient
import com.example.spartans_supplements_sobile.repository.ProdutoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProdutoViewModel : ViewModel() {

    private val repository = ProdutoRepository()
    private val api = RetrofitClient.apiService

    var produto: ProdutoResponse? = null
    var produtos by mutableStateOf<List<ProdutoResponse>>(emptyList())
        private set

    private val _carrinho = MutableStateFlow<CarrinhoResponse?>(null)
    val carrinho = _carrinho.asStateFlow()

    private val USUARIO_ID = 1L

    init {
        listarProdutos()
        carregarOuCriarCarrinho()
    }

    // ================= FUNÇÕES DO CARRINHO (API) =================

    fun carregarOuCriarCarrinho() {
        viewModelScope.launch {
            try {
                val response = api.createCart(USUARIO_ID)
                if (response.isSuccessful) {
                    _carrinho.value = response.body()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun adicionarAoCarrinho(produtoId: Long, quantidade: Int = 1) {
        viewModelScope.launch {
            try {
                    if (_carrinho.value == null) {
                    val res = api.createCart(USUARIO_ID)
                    if (res.isSuccessful) _carrinho.value = res.body()
                }

                _carrinho.value?.id?.let { idCarrinho ->
                    val response = api.addProduct(idCarrinho, produtoId, quantidade)
                    if (response.isSuccessful) {
                        _carrinho.value = response.body()
                        println("Sucesso: Produto adicionado!")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun alterarQuantidade(idProduto: Long, aumentar: Boolean) {
        val qtdParaMudar = if (aumentar) 1 else -1
        adicionarAoCarrinho(idProduto, qtdParaMudar)
    }

    fun removerDoCarrinho(itemId: Long) {
        val idCarrinho = _carrinho.value?.id ?: return
        viewModelScope.launch {
            try {
                val response = api.removeProduct(idCarrinho, itemId)
                if (response.isSuccessful) {
                    _carrinho.value = response.body()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // ================= FUNÇÕES DE CRUD DE PRODUTOS =================

    fun listarProdutos() {
        viewModelScope.launch {
            produtos = repository.list() ?: emptyList()
        }
    }

    fun findById(id: Long) {
        viewModelScope.launch {
            produto = repository.getById(id)
        }
    }

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
}