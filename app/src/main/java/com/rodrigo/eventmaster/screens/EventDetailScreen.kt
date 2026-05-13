package com.rodrigo.eventmaster.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EventDetailScreen(
    titulo: String,
    descripcion: String,
    categoria: String
) {

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Text(text = "Detalle del Evento")

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Título: $titulo")

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Descripción: $descripcion")

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Categoría: $categoria")
    }
}
