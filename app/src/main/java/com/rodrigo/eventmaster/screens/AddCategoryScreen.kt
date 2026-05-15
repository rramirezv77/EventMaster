package com.rodrigo.eventmaster.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.rodrigo.eventmaster.R
import com.rodrigo.eventmaster.viewmodel.CategoryViewModel

@Composable
fun AddCategoryScreen(
    navController: NavController,
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val categories by viewModel.categories.collectAsStateWithLifecycle()
    var categoryName by rememberSaveable { mutableStateOf("") }
    var error by rememberSaveable { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.new_category),
            style = MaterialTheme.typography.headlineSmall
        )

        OutlinedTextField(
            value = categoryName,
            onValueChange = {
                categoryName = it
                error = null
            },
            label = { Text(stringResource(R.string.category_name)) },
            isError = error != null,
            supportingText = { error?.let { Text(it) } },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val normalizedName = categoryName.trim()
                error = when {
                    normalizedName.isBlank() -> context.getString(R.string.category_required_name)
                    categories.any { it.name.equals(normalizedName, ignoreCase = true) } ->
                        context.getString(R.string.category_duplicate)
                    else -> null
                }

                if (error == null) {
                    viewModel.addCategory(normalizedName)
                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.save))
        }

        TextButton(onClick = { navController.popBackStack() }) {
            Text(stringResource(R.string.back))
        }
    }
}
