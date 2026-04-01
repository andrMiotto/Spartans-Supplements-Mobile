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

    // Certifique-se que o usuário com ID 1 existe no banco da sua API na nuvem
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
                    println("LOG: Carrinho carregado/criado com sucesso!")
                } else {
                    println("LOG ERRO: Falha ao carregar carrinho. Código: ${response.code()}")
                }
            } catch (e: Exception) {
                println("LOG EXCEPTION: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    fun adicionarAoCarrinho(produtoId: Long, quantidade: Int = 1) {
        viewModelScope.launch {
            try {
                // 1. Garantir que temos um carrinho antes de prosseguir
                var carrinhoAtual = _carrinho.value

                if (carrinhoAtual == null) {
                    println("LOG: Carrinho nulo, tentando criar para o usuário $USUARIO_ID...")
                    val res = api.createCart(USUARIO_ID)
                    if (res.isSuccessful) {
                        carrinhoAtual = res.body()
                        _carrinho.value = carrinhoAtual
                    } else {
                        println("LOG ERRO: Não foi possível criar carrinho: ${res.code()}")
                        return@launch
                    }
                }

                // 2. Com o carrinho garantido, adicionamos o produto
                carrinhoAtual?.id?.let { idCarrinho ->
                    println("LOG: Adicionando produto $produtoId ao carrinho $idCarrinho...")
                    val response = api.addProduct(idCarrinho, produtoId, quantidade)

                    if (response.isSuccessful) {
                        _carrinho.value = response.body() // Atualiza o StateFlow com o novo total e itens
                        println("LOG SUCESSO: Produto adicionado com sucesso!")
                    } else {
                        val erroMsg = response.errorBody()?.string()
                        println("LOG ERRO API: $erroMsg")
                    }
                }
            } catch (e: Exception) {
                println("LOG EXCEPTION: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    fun alterarQuantidade(idProduto: Long, aumentar: Boolean) {
        // Se aumentar for true, envia 1. Se for false, envia -1.
        val qtdParaMudar = if (aumentar) 1 else -1
        adicionarAoCarrinho(idProduto, qtdParaMudar)
    }

    fun removerDoCarrinho(itemId: Long) {
        val idCarrinho = _carrinho.value?.id ?: run {
            println("LOG ERRO: Tentativa de remover item sem carrinho ativo.")
            return
        }

        viewModelScope.launch {
            try {
                val response = api.removeProduct(idCarrinho, itemId)
                if (response.isSuccessful) {
                    _carrinho.value = response.body()
                    println("LOG: Item $itemId removido com sucesso.")
                } else {
                    println("LOG ERRO: Falha ao remover item: ${response.code()}")
                }
            } catch (e: Exception) {
                println("LOG EXCEPTION: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    // ================= FUNÇÕES DE CRUD DE PRODUTOS =================

    fun listarProdutos() {
        viewModelScope.launch {
            try {
                val lista = repository.list()
                produtos = lista ?: emptyList()
            } catch (e: Exception) {
                println("LOG EXCEPTION Listar: ${e.message}")
            }
        }
    }

    fun findById(id: Long) {
        viewModelScope.launch {
            try {
                produto = repository.getById(id)
            } catch (e: Exception) {
                println("LOG EXCEPTION FindById: ${e.message}")
            }
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