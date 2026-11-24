package com.example.taller_rick_morty_1.View

import com.example.taller_rick_morty_1.Models.RickModels

sealed interface UiState {
    data object Loading : UiState
    data class Success(val items: List<RickModels>) : UiState
    data class Error(val message: String) : UiState
}

sealed interface DetalleUiState{
    data object Loading: DetalleUiState

    data class Success(
        val personaje: RickModels
    ) : DetalleUiState

    data class Error(
        val message: String
    ): DetalleUiState
}
