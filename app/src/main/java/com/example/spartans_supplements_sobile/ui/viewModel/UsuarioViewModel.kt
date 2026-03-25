package com.example.spartans_supplements_sobile.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spartans_supplements_sobile.model.dto.usuario.UsuarioResponse
import com.example.spartans_supplements_sobile.repository.UsuarioRepository
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private val repository = UsuarioRepository()

    var user by mutableStateOf<UsuarioResponse?>(null)
        private set

    fun buscarUsuario(id: Long) {
        viewModelScope.launch {
            user = repository.getUserById(id)
        }
    }
}
