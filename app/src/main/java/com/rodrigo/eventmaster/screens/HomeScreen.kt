package com.rodrigo.eventmaster.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.rodrigo.eventmaster.EventMasterRoutes
import com.rodrigo.eventmaster.R
import com.rodrigo.eventmaster.screens.components.CategoryFilterChip
import com.rodrigo.eventmaster.screens.components.EmptyState
import com.rodrigo.eventmaster.screens.components.EventCard
import com.rodrigo.eventmaster.screens.components.SectionTitle
import com.rodrigo.eventmaster.viewmodel.CategoryViewModel
import com.rodrigo.eventmaster.viewmodel.EventViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    categoryViewModel: CategoryViewModel = hiltViewModel(),
    eventViewModel: EventViewModel = hiltViewModel()
) {
    val categories by categoryViewModel.categories.collectAsStateWithLifecycle()
    val events by eventViewModel.events.collectAsStateWithLifecycle()
    var selectedCategoryId by rememberSaveable { mutableStateOf<Long?>(null) }
    val filteredEvents = selectedCategoryId?.let { categoryId ->
        events.filter { it.categoryId == categoryId }
    } ?: events
    val eventsByCategory = filteredEvents.groupBy { it.categoryName }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = stringResource(R.string.home_title),
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = stringResource(R.string.home_subtitle),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { navController.navigate(EventMasterRoutes.ADD_CATEGORY) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(stringResource(R.string.new_category))
                }
                Button(
                    onClick = { navController.navigate(EventMasterRoutes.ADD_EVENT) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(stringResource(R.string.new_event))
                }
            }
        }

        item {
            SectionTitle(text = stringResource(R.string.categories))
        }

        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                item {
                    CategoryFilterChip(
                        text = stringResource(R.string.all),
                        selected = selectedCategoryId == null,
                        onClick = { selectedCategoryId = null }
                    )
                }
                items(categories, key = { it.id }) { category ->
                    CategoryFilterChip(
                        text = category.name,
                        selected = selectedCategoryId == category.id,
                        onClick = { selectedCategoryId = category.id }
                    )
                }
            }
        }

        item {
            SectionTitle(
                text = stringResource(R.string.events),
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        if (filteredEvents.isEmpty()) {
            item {
                EmptyState(
                    text = if (selectedCategoryId == null) {
                        stringResource(R.string.empty_events)
                    } else {
                        stringResource(R.string.empty_filtered_events)
                    }
                )
            }
        } else {
            eventsByCategory.forEach { (categoryName, categoryEvents) ->
                item {
                    SectionTitle(text = categoryName)
                }
                items(categoryEvents, key = { it.id }) { event ->
                    EventCard(
                        event = event,
                        onClick = { navController.navigate(EventMasterRoutes.eventDetail(event.id)) }
                    )
                }
            }
        }
    }
}
