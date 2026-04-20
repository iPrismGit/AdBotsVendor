package com.iprism.adbotsvendor.presentation.ui.screens

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iprism.adbotsvendor.R
import com.iprism.adbotsvendor.presentation.ui.components.CustomTextField
import com.iprism.adbotsvendor.presentation.ui.components.LoadingScreen
import com.iprism.adbotsvendor.presentation.ui.components.TitleText
import com.iprism.adbotsvendor.presentation.ui.theme.BLACK
import com.iprism.adbotsvendor.presentation.ui.theme.Green
import com.iprism.adbotsvendor.presentation.ui.theme.Teal
import com.iprism.adbotsvendor.presentation.viewmodels.ContactUsViewModel
import com.iprism.adbotsvendor.utils.UiState

@Composable
fun ContactUsScreen(
    onBack: () -> Unit,
    onContinueClick: () -> Unit,
    viewModel: ContactUsViewModel = hiltViewModel()
) {

    var name by rememberSaveable { mutableStateOf("") }
    var emailId by rememberSaveable { mutableStateOf("") }
    var mobileNumber by rememberSaveable { mutableStateOf("") }
    var message by rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current
    val uiState by viewModel.contactUsResponse.collectAsStateWithLifecycle()

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.onCallClicked()
        } else {
            Toast.makeText(context, "Permission denied to make calls", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is ContactUsViewModel.UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }

                ContactUsViewModel.UiEvent.Success -> {
                    onContinueClick()
                }

                is ContactUsViewModel.UiEvent.MakeCall -> {
                    val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:${event.phoneNumber}"))
                    if (ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.CALL_PHONE
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        context.startActivity(intent)
                    } else {
                        permissionLauncher.launch(Manifest.permission.CALL_PHONE)
                    }
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
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
                        viewModel.submitContactUs(name, emailId, mobileNumber, message)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .imePadding(),
                    enabled = uiState !is UiState.Loading,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Teal),
                ) {
                    if (uiState is UiState.Loading) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
                    } else {
                        Text(
                            "Confirm",
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
                Spacer(Modifier.height(20.dp))
                Text(
                    "Or",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(20.dp))
                Button(
                    onClick = {
                        if (ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.CALL_PHONE
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            viewModel.onCallClicked()
                        } else {
                            permissionLauncher.launch(Manifest.permission.CALL_PHONE)
                        }
                    },
                    modifier = Modifier
                        .imePadding()
                        .align(alignment = Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Green),
                ) {
                    Text(
                        "Call",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContactUsScreenPreview() {
    ContactUsScreen(onBack = {}, onContinueClick = {})
}
