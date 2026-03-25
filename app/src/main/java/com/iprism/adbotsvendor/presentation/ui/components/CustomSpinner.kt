package com.iprism.adbotsvendor.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iprism.adbotsvendor.presentation.ui.theme.Grey555
import com.iprism.adbotsvendor.presentation.ui.theme.LightGrey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSpinner(
    label: String,
    items: List<Gender>,
    selectedItem: Gender?,
    onItemSelected: (Gender) -> Unit,
    modifier: Modifier = Modifier
) {

    var expanded by rememberSaveable { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {

        OutlinedTextField(
            value = selectedItem?.name ?: "",
            onValueChange = {},
            readOnly = true,
            placeholder = { Text(label, style = MaterialTheme.typography.bodySmall, color = Grey555)},
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
            },
            modifier = modifier
                .menuAnchor()
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = LightGrey
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {

            items.forEach { item ->

                DropdownMenuItem(
                    text = { Text(item.name, style = MaterialTheme.typography.bodySmall) },
                    onClick = {
                        onItemSelected(item)
                        expanded = false
                    }
                )

            }

        }
    }
}

data class Gender(
    val name : String,
    val id : Int
)