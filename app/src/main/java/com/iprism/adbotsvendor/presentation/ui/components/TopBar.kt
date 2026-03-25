package com.iprism.adbotsvendor.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.iprism.adbotsvendor.presentation.ui.theme.DarkBlue

@Composable
fun TopBar(
    onBack: () -> Unit,
    title: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(DarkBlue).padding(top = 4.dp, bottom = 4.dp),
        horizontalArrangement = Arrangement.Start
    )
    {
        IconButton(onClick = { onBack() }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }
        Text(text = title, modifier = Modifier.fillMaxWidth().align(Alignment.CenterVertically), color = Color.White)
    }
}