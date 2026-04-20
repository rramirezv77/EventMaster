package com.rodrigo.eventmaster.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class CategoryViewModel : ViewModel() {

    var categorias = mutableStateListOf(
        "Música",
        "Tecnología",
        "Deportes"
    )

    fun agregarCategoria(nombre: String) {
        categorias.add(nombre)
    }
}
