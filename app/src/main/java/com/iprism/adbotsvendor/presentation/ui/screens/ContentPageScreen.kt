package com.iprism.adbotsvendor.presentation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iprism.adbotsvendor.R
import com.iprism.adbotsvendor.data.models.contentpages.ContentPagesRequest
import com.iprism.adbotsvendor.presentation.ui.components.LoadingScreen
import com.iprism.adbotsvendor.presentation.ui.theme.BLACK
import com.iprism.adbotsvendor.presentation.viewmodels.ContentPagesViewModel
import com.iprism.adbotsvendor.utils.UiState

@Composable
fun ContentPageScreen(
    type: String,
    onBack: () -> Unit,
    viewModel: ContentPagesViewModel = hiltViewModel()
) {
    val state by viewModel.response.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.register(ContentPagesRequest(viewType = type))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            IconButton(
                onClick = { onBack() },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.back_img),
                    contentDescription = "Back",
                    tint = BLACK,
                    modifier = Modifier.size(28.dp)
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(12.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                val title = when (type) {
                    "terms" -> "Terms & Conditions"
                    "privacy" -> "Privacy Policy"
                    "about_us" -> "About Us"
                    else -> "Content"
                }
                Text(title, style = MaterialTheme.typography.headlineMedium)
                Spacer(Modifier.height(16.dp))

                if (state is UiState.Success) {
                    val content = (state as UiState.Success).data.response.firstOrNull()?.content
                        ?: "No content available"
                    Text(
                        text = content,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }

        if (state is UiState.Loading) {
            LoadingScreen()
        }
    }
}