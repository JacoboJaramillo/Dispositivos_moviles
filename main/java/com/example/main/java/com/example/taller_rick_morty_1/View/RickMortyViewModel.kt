package com.example.taller_rick_morty_1.View

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.taller_rick_morty_1.Repository.RetrofitInstance
import com.example.taller_rick_morty_1.Repository.RickRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.log

class RickMortyViewModel(private val repository: RickRepository = RickRepository(RetrofitInstance.api)
): ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading) /*permite modificar el valor de UiState pero unicamente en esta clase*/
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()     /* Expone el valor de _uiState, unicamente lectura, no deja cambiar el valor*/
    init {
        cargarPersonajes()
    }

    fun cargarPersonajes() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = repository.obtenerPersonajes().fold(
                onSuccess = { UiState.Success(it)},
                onFailure = { UiState.Error("No se pudo cargar. Reintenta")}
            )
            }
        }
    }
