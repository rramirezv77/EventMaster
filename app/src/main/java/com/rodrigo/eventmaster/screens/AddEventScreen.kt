package com.rodrigo.eventmaster.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import com.rodrigo.eventmaster.EventMasterRoutes
import com.rodrigo.eventmaster.R
import com.rodrigo.eventmaster.screens.components.EmptyState
import com.rodrigo.eventmaster.viewmodel.CategoryViewModel
import com.rodrigo.eventmaster.viewmodel.EventViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventScreen(
    navController: NavController,
    eventViewModel: EventViewModel = hiltViewModel(),
    categoryViewModel: CategoryViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val categories by categoryViewModel.categories.collectAsStateWithLifecycle()
    var selectedCategoryId by rememberSaveable { mutableStateOf<Long?>(null) }
    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var error by rememberSaveable { mutableStateOf<String?>(null) }
    var expanded by rememberSaveable { mutableStateOf(false) }
    val selectedCategory = categories.firstOrNull { it.id == selectedCategoryId }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.new_event),
            style = MaterialTheme.typography.headlineSmall
        )

        if (categories.isEmpty()) {
            EmptyState(text = stringResource(R.string.create_category_first))
            Button(
                onClick = { navController.navigate(EventMasterRoutes.ADD_CATEGORY) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.new_category))
            }
            TextButton(onClick = { navController.popBackStack() }) {
                Text(stringResource(R.string.back))
            }
        } else {
            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                    error = null
                },
                label = { Text(stringResource(R.string.event_title)) },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = {
                    description = it
                    error = null
                },
                label = { Text(stringResource(R.string.event_description)) },
                minLines = 3,
                modifier = Modifier.fillMaxWidth()
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it }
            ) {
                OutlinedTextField(
                    value = selectedCategory?.name.orEmpty(),
                    onValueChange = {},
                    label = { Text(stringResource(R.string.event_category)) },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category.name) },
                            onClick = {
                                selectedCategoryId = category.id
                                error = null
                                expanded = false
                            }
                        )
                    }
                }
            }

            if (error != null) {
                Text(
                    text = error.orEmpty(),
                    color = MaterialTheme.colorScheme.error
                )
            }

            Button(
                onClick = {
                    error = when {
                        title.isBlank() -> context.getString(R.string.title_required)
                        description.isBlank() -> context.getString(R.string.description_required)
                        selectedCategoryId == null -> context.getString(R.string.category_required)
                        else -> null
                    }

                    if (error == null) {
                        eventViewModel.addEvent(
                            title = title,
                            description = description,
                            categoryId = selectedCategoryId ?: return@Button
                        )
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.save_event))
            }

            TextButton(onClick = { navController.popBackStack() }) {
                Text(stringResource(R.string.back))
            }
        }
    }
}
