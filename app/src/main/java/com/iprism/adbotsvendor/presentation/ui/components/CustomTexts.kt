package com.iprism.adbotsvendor.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.iprism.adbotsvendor.presentation.ui.theme.BLACK
import com.iprism.adbotsvendor.presentation.ui.theme.Grey555
import com.iprism.adbotsvendor.presentation.ui.theme.LightGrey


@Composable
fun TitleText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall,
        color = BLACK
    )
}

@Composable
fun RequiredTitleText(text: String) {
    Text(
        text = buildAnnotatedString {
            append(text)

            withStyle(
                style = SpanStyle(color = Color.Red)
            ) {
                append(" *")
            }
        },
        style = MaterialTheme.typography.bodySmall,
        color = BLACK
    )
}

@Composable
fun CustomTextField(
    value: String,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    enabled: Boolean = true,
    onValueChange: (String) -> Unit
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        textStyle = MaterialTheme.typography.bodySmall,
        placeholder = { Text(placeholder, style = MaterialTheme.typography.bodySmall, color = Grey555)},
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = LightGrey
        )
    )
}
