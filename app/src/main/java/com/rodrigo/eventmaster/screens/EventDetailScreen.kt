package com.rodrigo.eventmaster.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rodrigo.eventmaster.R
import com.rodrigo.eventmaster.screens.components.EmptyState
import com.rodrigo.eventmaster.screens.components.EventCard
import com.rodrigo.eventmaster.viewmodel.EventDetailViewModel

@Composable
fun EventDetailScreen(
    onBack: () -> Unit,
    viewModel: EventDetailViewModel = hiltViewModel()
) {
    val event by viewModel.event.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.detail_title),
            style = MaterialTheme.typography.headlineSmall
        )

        event?.let {
            EventCard(event = it, onClick = {})
        } ?: EmptyState(text = stringResource(R.string.event_not_found))

        Button(onClick = onBack) {
            Text(stringResource(R.string.back))
        }
    }
}
