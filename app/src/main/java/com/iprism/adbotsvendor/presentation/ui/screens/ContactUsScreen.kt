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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iprism.adbotsvendor.R
import com.iprism.adbotsvendor.presentation.ui.components.CustomTextField
import com.iprism.adbotsvendor.presentation.ui.components.TitleText
import com.iprism.adbotsvendor.presentation.ui.theme.BLACK
import com.iprism.adbotsvendor.presentation.ui.theme.Green
import com.iprism.adbotsvendor.presentation.ui.theme.Teal

@Composable
fun ContactUsScreen(onBack :() -> Unit, onContinueClick :() -> Unit) {

    var name by rememberSaveable { mutableStateOf("") }
    var emailId by rememberSaveable { mutableStateOf("") }
    var mobileNumber by rememberSaveable { mutableStateOf("") }
    var message by rememberSaveable { mutableStateOf("") }

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
            Text("Contact us", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(16.dp))
            TitleText(stringResource(R.string.your_name))
            Spacer(Modifier.height(12.dp))

            CustomTextField(
                name,
                stringResource(R.string.enter),
                KeyboardType.Text
            ) { name = it }

            Spacer(Modifier.height(12.dp))

            TitleText("Email Id")
            Spacer(Modifier.height(12.dp))

            CustomTextField(
                emailId,
                stringResource(R.string.enter),
                KeyboardType.Text
            ) { emailId = it }
            Spacer(Modifier.height(12.dp))

            TitleText(stringResource(R.string.mobile_number))
            Spacer(Modifier.height(12.dp))
            CustomTextField(
                mobileNumber,
                stringResource(R.string.enter),
                KeyboardType.Phone
            ) { mobileNumber = it }

            Spacer(Modifier.height(12.dp))
            TitleText("Message")
            Spacer(Modifier.height(12.dp))

            CustomTextField(
                message,
                stringResource(R.string.enter),
                KeyboardType.Text
            ) { message = it }

            Spacer(Modifier.height(12.dp))
            Button(
                onClick = {
                    onContinueClick()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Teal),
            ) {
                Text("Confirm", style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(8.dp))
            }
            Spacer(Modifier.height(20.dp))
            Text("Or", style = MaterialTheme.typography.titleMedium, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(20.dp))
            Button(
                onClick = {
                    onContinueClick()
                },
                modifier = Modifier.imePadding().align(alignment = androidx.compose.ui.Alignment.CenterHorizontally),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Green),
            ) {
                Text("Call", textAlign = TextAlign.Center, style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(8.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContactUsScreenPreview() {
    ContactUsScreen(onBack = {}, onContinueClick = {})
}
