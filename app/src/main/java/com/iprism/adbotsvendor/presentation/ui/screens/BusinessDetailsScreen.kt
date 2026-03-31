package com.iprism.adbotsvendor.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iprism.adbotsvendor.R
import com.iprism.adbotsvendor.presentation.ui.components.CustomSpinner
import com.iprism.adbotsvendor.presentation.ui.components.CustomTextField
import com.iprism.adbotsvendor.presentation.ui.components.Gender
import com.iprism.adbotsvendor.presentation.ui.components.TitleText
import com.iprism.adbotsvendor.presentation.ui.theme.BLACK
import com.iprism.adbotsvendor.presentation.ui.theme.BLACK1
import com.iprism.adbotsvendor.presentation.ui.theme.DarkBlue
import com.iprism.adbotsvendor.presentation.ui.theme.MontserratFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusinessDetailsScreen(onBack :() -> Unit, onContinueClick :() -> Unit) {

    var yourName by rememberSaveable { mutableStateOf("") }
    var businessName by rememberSaveable { mutableStateOf("") }
    var mobileNumber by rememberSaveable { mutableStateOf("") }
    var selectedCity by rememberSaveable { mutableStateOf<Gender?>(null) }
    var selectedArea by rememberSaveable { mutableStateOf<Gender?>(null) }
    var selectedBusinessCat by rememberSaveable { mutableStateOf<Gender?>(null) }
    
    var showDatePickerSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    if (showDatePickerSheet) {
        ModalBottomSheet(
            onDismissRequest = { showDatePickerSheet = false },
            sheetState = sheetState,
            dragHandle = {
                Box(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .size(40.dp, 8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(DarkBlue)
                )
            },
            containerColor = Color.White
        ) {
            ChooseDatesContent(onContinue = {
                showDatePickerSheet = false
                onContinueClick()
            })
        }
    }

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
            Text("Business Details", style = MaterialTheme.typography.headlineMedium)
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
            ) { businessName = it }
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
                items = cities, // Use appropriate list if different
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
                items = cities, // Use appropriate list if different
                selectedItem = selectedBusinessCat,
                onItemSelected = {
                    selectedBusinessCat = it
                }
            )
            Spacer(Modifier.height(12.dp))
        }

        Button(
            onClick = {
                showDatePickerSheet = true
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

@Composable
fun ChooseDatesContent(onContinue: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Text(
            text = "Choose Dates",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Start Date", style = MaterialTheme.typography.bodySmall, color = BLACK1)
                Spacer(modifier = Modifier.height(12.dp))
                DateSelectorBox(onClick = { /* Pick Start Date */ })
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "End Date", style = MaterialTheme.typography.bodySmall, color = BLACK1)
                Spacer(modifier = Modifier.height(12.dp))
                DateSelectorBox(onClick = { /* Pick End Date */ })
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onContinue,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFEEEEEE),
                contentColor = Color.LightGray
            )
        ) {
            Text(
                text = "Continue",
                style = MaterialTheme.typography.labelMedium
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun DateSelectorBox(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Choose", fontFamily = MontserratFamily, fontWeight = FontWeight.Normal, color = BLACK1, fontSize = 12.sp)
            Icon(
                painter = painterResource(R.drawable.calender_img),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color.Black
            )
        }
    }
}

private val cities = listOf(
    Gender("Hyderabad", 1),
    Gender("Bengaluru", 2),
    Gender("Vizag", 3),
)
