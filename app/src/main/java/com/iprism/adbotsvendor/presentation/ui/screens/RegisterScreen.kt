package com.iprism.adbotsvendor.presentation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iprism.adbotsvendor.R
import com.iprism.adbotsvendor.presentation.ui.components.CustomSpinner
import com.iprism.adbotsvendor.presentation.ui.components.CustomTextField
import com.iprism.adbotsvendor.presentation.ui.components.Gender
import com.iprism.adbotsvendor.presentation.ui.components.TitleText
import com.iprism.adbotsvendor.presentation.ui.components.TopBar
import com.iprism.adbotsvendor.presentation.ui.theme.BLACK
import com.iprism.adbotsvendor.presentation.ui.theme.DarkBlue

@Composable
fun RegisterScreen(onBack :() -> Unit) {

    var yourName by rememberSaveable { mutableStateOf("") }
    var businessName by rememberSaveable { mutableStateOf("") }
    var mobileNumber by rememberSaveable { mutableStateOf("") }
    var selectedCity by rememberSaveable { mutableStateOf<Gender?>(null) }
    var selectedArea by rememberSaveable { mutableStateOf<Gender?>(null) }
    var selectedBusinessCat by rememberSaveable { mutableStateOf<Gender?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
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
            Text(stringResource(R.string.add_business_details), style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(16.dp))
            TitleText(stringResource(R.string.your_name))
            Spacer(Modifier.height(12.dp))

            CustomTextField(
                yourName,
                stringResource(R.string.enter),
                KeyboardType.Text
            ) { yourName = it }

            Spacer(Modifier.height(12.dp))

            TitleText(stringResource(R.string.business_name))
            Spacer(Modifier.height(12.dp))

            CustomTextField(
                businessName,
                stringResource(R.string.enter),
                KeyboardType.Text
            ) { yourName = it }
            Spacer(Modifier.height(12.dp))

            TitleText(stringResource(R.string.mobile_number))
            Spacer(Modifier.height(12.dp))
            CustomTextField(
                mobileNumber,
                stringResource(R.string.enter),
                KeyboardType.Phone
            ) { mobileNumber = it }

            Spacer(Modifier.height(12.dp))

            TitleText(stringResource(R.string.city))
            Spacer(Modifier.height(12.dp))
            CustomSpinner(
                label = stringResource(R.string.choose),
                items = cities,
                selectedItem = selectedCity,
                onItemSelected = {
                    selectedCity = it
                }
            )
            Spacer(Modifier.height(12.dp))
            TitleText(stringResource(R.string.area))
            Spacer(Modifier.height(12.dp))
            CustomSpinner(
                label = stringResource(R.string.choose),
                items = cities,
                selectedItem = selectedArea,
                onItemSelected = {
                    selectedArea = it
                }
            )
            Spacer(Modifier.height(12.dp))
            TitleText(stringResource(R.string.business_category))
            Spacer(Modifier.height(12.dp))
            CustomSpinner(
                label = stringResource(R.string.choose),
                items = cities,
                selectedItem = selectedBusinessCat,
                onItemSelected = {
                    selectedBusinessCat = it
                }
            )
            Spacer(Modifier.height(12.dp))
        }

        Button(
            onClick = {

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, bottom = 12.dp, end = 12.dp)
                .imePadding(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkBlue),
        ) {
            Text("Continue", style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(8.dp))
        }
    }
}

private val cities = listOf(
    Gender("Hyderabad", 1),
    Gender("Bengaluru", 2),
    Gender("Vizag", 3),
)

@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    RegisterScreen({})
}
