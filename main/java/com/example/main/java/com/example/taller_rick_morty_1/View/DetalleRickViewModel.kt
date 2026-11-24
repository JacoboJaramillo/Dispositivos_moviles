package com.example.taller_rick_morty_1.View

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taller_rick_morty_1.Models.RickModels
import com.example.taller_rick_morty_1.Repository.RetrofitInstance
import com.example.taller_rick_morty_1.Repository.RickRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetalleRickViewModel: ViewModel() {
    private val repository = RickRepository(RetrofitInstance.api)

    private val _uiState = MutableStateFlow<DetalleUiState>(DetalleUiState.Loading) /*Siempre es importante detallar el tipo, para indicarle que puede almacenar cualquier estado de DetalleuiState */
    val uiState1: StateFlow<DetalleUiState> = _uiState.asStateFlow()

    fun cargarDetallePersonaje(id: Int){
        viewModelScope.launch {
            _uiState.value = DetalleUiState.Loading
            _uiState.value = repository.obtenerPersonaje(id).fold(
                onSuccess = { DetalleUiState.Success(it)},
                onFailure = { DetalleUiState.Error("ERROR")}
            )
                }
        }
    }
