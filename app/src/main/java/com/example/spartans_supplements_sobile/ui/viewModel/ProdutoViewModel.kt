package com.example.spartans_supplements_sobile.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spartans_supplements_sobile.model.dto.produto.ProdutoRequest
import com.example.spartans_supplements_sobile.model.dto.produto.ProdutoResponse
import com.example.spartans_supplements_sobile.repository.ProdutoRepository
import kotlinx.coroutines.launch

class ProdutoViewModel : ViewModel() {

    private val repository = ProdutoRepository()

    var produto: ProdutoResponse? = null
    var produtos by mutableStateOf<List<ProdutoResponse>>(emptyList())
        private set

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

    fun deletarProduto(id: Long){
        viewModelScope.launch {
            try{
                val sucesso = repository.delete(id)
                if(sucesso){
                    listarProdutos()
                }
            }catch (e:Exception){
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

}