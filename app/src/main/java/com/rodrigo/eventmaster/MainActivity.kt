package com.rodrigo.eventmaster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rodrigo.eventmaster.ui.theme.EventMasterTheme
import androidx.navigation.compose.*
import androidx.navigation.NavController
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rodrigo.eventmaster.screens.AddEventScreen
import com.rodrigo.eventmaster.screens.EventDetailScreen
import com.rodrigo.eventmaster.viewmodel.CategoryViewModel
import com.rodrigo.eventmaster.viewmodel.EventViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: CategoryViewModel = viewModel()
            val eventViewModel: EventViewModel = viewModel()
            val categoryViewModel: CategoryViewModel = viewModel()
            val navController = rememberNavController()
            EventMasterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("home") {
                            HomeScreen(navController, viewModel, eventViewModel)
                        }

                        composable("addCategory") {
                            AddCategoryScreen(navController, viewModel)
                        }
                        composable("addEvent") {
                            AddEventScreen(navController, eventViewModel, categoryViewModel)
                        }

                        composable(
                            "eventDetail/{titulo}/{descripcion}/{categoria}"
                        ) { backStackEntry ->

                            val titulo = backStackEntry.arguments?.getString("titulo") ?: ""
                            val descripcion = backStackEntry.arguments?.getString("descripcion") ?: ""
                            val categoria = backStackEntry.arguments?.getString("categoria") ?: ""

                            EventDetailScreen(titulo, descripcion, categoria)
                        }

                    }

                }
                }
            }
        }
    }



@Composable
fun HomeScreen(
    navController: NavController,
    categoryViewModel: CategoryViewModel,
    eventViewModel: EventViewModel
) {
    var categoriaSeleccionada by remember { mutableStateOf<String?>(null) }
    val categorias = categoryViewModel.categorias
    val eventos = eventViewModel.eventos.filter { evento -> categoriaSeleccionada == null || evento.categoria == categoriaSeleccionada }


    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Text(
            text = "EventMaster",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Ingresar nuevo contenido",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { navController.navigate("addCategory") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Nueva Categoría")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { navController.navigate("addEvent") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Nuevo Evento")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Categorías",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(categorias) { categoria ->
                Button(
                    onClick = { categoriaSeleccionada = categoria },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(categoria)
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Eventos",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (categoriaSeleccionada != null) {
            Text("Filtrando por: $categoriaSeleccionada")

            Button(
                onClick = { categoriaSeleccionada = null }
            ) {
                Text("Mostrar todos")
            }
        }

        LazyColumn {
            items(eventos) { evento ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .clickable {
                            navController.navigate(
                                "eventDetail/${evento.titulo}/${evento.descripcion}/${evento.categoria}"
                            )
                        },
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = evento.titulo,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = evento.descripcion,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Categoría: ${evento.categoria}",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AddCategoryScreen(navController: NavController, viewModel: CategoryViewModel) {

    var nombreCategoria by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Text("Nueva Categoría")

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = nombreCategoria,
            onValueChange = {
                nombreCategoria = it
            },
            label = { Text("Nombre de la categoría") },
            modifier = Modifier.fillMaxWidth()
        )
        if (error.isNotEmpty()) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (nombreCategoria.isBlank()) {
                    error = "El nombre no puede estar vacío"
                } else {
                    viewModel.agregarCategoria(nombreCategoria)
                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar")
        }
    }
}
