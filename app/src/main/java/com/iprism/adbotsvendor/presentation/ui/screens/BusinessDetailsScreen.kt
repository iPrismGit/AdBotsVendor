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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.iprism.adbotsvendor.presentation.ui.theme.LightGrey1
import com.iprism.adbotsvendor.presentation.ui.theme.MontserratFamily
import com.iprism.adbotsvendor.presentation.ui.theme.White
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusinessDetailsScreen(onBack: () -> Unit, onContinueClick: () -> Unit) {

    var yourName by rememberSaveable { mutableStateOf("") }
    var businessName by rememberSaveable { mutableStateOf("") }
    var mobileNumber by rememberSaveable { mutableStateOf("") }
    var selectedCity by rememberSaveable { mutableStateOf<Gender?>(null) }
    var selectedArea by rememberSaveable { mutableStateOf<Gender?>(null) }
    var selectedBusinessCat by rememberSaveable { mutableStateOf<Gender?>(null) }

    var showBottomSheet by remember { mutableStateOf(false) }
    var bottomSheetStep by remember { mutableIntStateOf(0) } // 0: Choose Dates, 1: How Many Screens
    val sheetState = rememberModalBottomSheetState()

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
                bottomSheetStep = 0
            },
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
            if (bottomSheetStep == 0) {
                ChooseDatesContent(onContinue = {
                    bottomSheetStep = 1
                })
            } else {
                HowManyScreensContent(onContinue = {
                    showBottomSheet = false
                    bottomSheetStep = 0
                    onContinueClick()
                })
            }
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
                showBottomSheet = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, bottom = 12.dp, end = 12.dp)
                .imePadding(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkBlue),
        ) {
            Text(
                "Continue",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseDatesContent(onContinue: () -> Unit) {
    var startDate by remember { mutableStateOf<Long?>(null) }
    var endDate by remember { mutableStateOf<Long?>(null) }

    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }

    val dateFormatter = remember { SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()) }

    if (showStartDatePicker) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showStartDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    startDate = datePickerState.selectedDateMillis
                    showStartDatePicker = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showStartDatePicker = false }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    if (showEndDatePicker) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showEndDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    endDate = datePickerState.selectedDateMillis
                    showEndDatePicker = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showEndDatePicker = false }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

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
                Text(
                    text = "Start Date",
                    style = MaterialTheme.typography.bodySmall,
                    color = BLACK1
                )
                Spacer(modifier = Modifier.height(12.dp))
                DateSelectorBox(
                    text = startDate?.let { dateFormatter.format(Date(it)) } ?: "Choose",
                    onClick = { showStartDatePicker = true }
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "End Date", style = MaterialTheme.typography.bodySmall, color = BLACK1)
                Spacer(modifier = Modifier.height(12.dp))
                DateSelectorBox(
                    text = endDate?.let { dateFormatter.format(Date(it)) } ?: "Choose",
                    onClick = { showEndDatePicker = true }
                )
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onContinue,
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (startDate != null && endDate != null) DarkBlue else Color(
                    0xFFEEEEEE
                ),
                contentColor = if (startDate != null && endDate != null) Color.White else Color.LightGray
            )
        ) {
            Text(
                text = "Continue",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun HowManyScreensContent(onContinue: () -> Unit) {
    var screenCount by remember { mutableIntStateOf(1) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Text(
            text = "How Many Screens",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "Select", style = MaterialTheme.typography.bodySmall, color = BLACK1)
        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = LightGrey1,
                    shape = RoundedCornerShape(12.dp)
                )
                .background(
                    color = White,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = String.format("%02d", screenCount),
                fontFamily = MontserratFamily,
                fontWeight = FontWeight.Normal,
                color = BLACK,
                fontSize = 12.sp
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = "-",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { if (screenCount > 1) screenCount-- }
                )
                Text(
                    text = screenCount.toString(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = MontserratFamily,
                    color = BLACK
                )
                Text(
                    text = "+",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { screenCount++ }
                )
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onContinue,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkBlue),
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
fun DateSelectorBox(text: String, onClick: () -> Unit) {
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
            Text(
                text = text,
                fontFamily = MontserratFamily,
                fontWeight = FontWeight.Normal,
                color = if (text == "Choose") BLACK1 else Color.Black,
                fontSize = 12.sp
            )
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
