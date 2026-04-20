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
import com.rodrigo.eventmaster.viewmodel.CategoryViewModel
import com.rodrigo.eventmaster.viewmodel.EventViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: CategoryViewModel = viewModel()
            val eventViewModel: EventViewModel = viewModel()
            val navController = rememberNavController()
            EventMasterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    NavHost(
                        navController = navController,
                        startDestination = "home"
                    ) {
                        composable("home") {
                            HomeScreen(navController, viewModel, eventViewModel)
                        }

                        composable("addCategory") {
                            AddCategoryScreen(navController, viewModel)
                        }
                        composable("addEvent") {
                            AddEventScreen(navController, eventViewModel)
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

    val categorias = categoryViewModel.categorias
    val eventos = eventViewModel.eventos

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Text(text = "EventMaster")

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Categorías")

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { navController.navigate("addCategory") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Nueva Categoría")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { navController.navigate("addEvent") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Nuevo Evento")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(categorias) { categoria ->
                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(categoria)
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Eventos")

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(eventos) { evento ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(text = evento.titulo)

                        Text(text = evento.descripcion)

                        Text(text = evento.categoria)

                    }
                }
            }
        }
    }
}

@Composable
fun AddCategoryScreen(navController: NavController, viewModel: CategoryViewModel) {

    var nombreCategoria by remember { mutableStateOf("") }

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
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.agregarCategoria(nombreCategoria)
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar")
        }
    }
}
