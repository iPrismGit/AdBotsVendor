package com.iprism.adbotsvendor.presentation.ui.screens

import android.widget.Toast
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iprism.adbotsvendor.R
import com.iprism.adbotsvendor.presentation.ui.components.CustomSpinner
import com.iprism.adbotsvendor.presentation.ui.components.CustomTextField
import com.iprism.adbotsvendor.presentation.ui.components.SpinnerItem
import com.iprism.adbotsvendor.presentation.ui.components.TitleText
import com.iprism.adbotsvendor.presentation.ui.theme.BLACK
import com.iprism.adbotsvendor.presentation.ui.theme.DarkBlue
import com.iprism.adbotsvendor.presentation.viewmodels.ProfileDetailsViewModel
import com.iprism.adbotsvendor.utils.UiState

@Composable
fun EditProfileScreen(
    onBack: () -> Unit,
    viewModel: ProfileDetailsViewModel = hiltViewModel()
) {

    var yourName by rememberSaveable { mutableStateOf("") }
    var businessName by rememberSaveable { mutableStateOf("") }
    var mobileNumber by rememberSaveable { mutableStateOf("") }
    var selectedCity by rememberSaveable { mutableStateOf<SpinnerItem?>(null) }
    var selectedArea by rememberSaveable { mutableStateOf<SpinnerItem?>(null) }
    var selectedBusinessCat by rememberSaveable { mutableStateOf<SpinnerItem?>(null) }

    val context = LocalContext.current
    val registerState by viewModel.profileResponse.collectAsStateWithLifecycle()
    val dropDownsState by viewModel.dropDownsResponse.collectAsStateWithLifecycle()
    val areasState by viewModel.areasResponse.collectAsStateWithLifecycle()

    var cityList by remember { mutableStateOf<List<SpinnerItem>>(emptyList()) }
    var categoryList by remember { mutableStateOf<List<SpinnerItem>>(emptyList()) }
    var areaList by remember { mutableStateOf<List<SpinnerItem>>(emptyList()) }

    LaunchedEffect(dropDownsState) {
        if (dropDownsState is UiState.Success) {
            val data = (dropDownsState as UiState.Success).data.response
            cityList = data.cities.map { SpinnerItem(it.cityName, it.id.toIntOrNull() ?: 0) }
            categoryList = data.categories.map { SpinnerItem(it.name, it.id.toIntOrNull() ?: 0) }
        }
    }

    LaunchedEffect(areasState) {
        if (areasState is UiState.Success) {
            val data = (areasState as UiState.Success).data.response
            areaList = data.areas.map { SpinnerItem(it.name, it.id.toIntOrNull() ?: 0) }
        }
    }

    LaunchedEffect(registerState) {
        if (registerState is UiState.Success) {
            val profile = (registerState as UiState.Success).data.response
            if (yourName.isEmpty()) yourName = profile.name
            if (businessName.isEmpty()) businessName = profile.bussinessName
            if (mobileNumber.isEmpty()) mobileNumber = profile.mobile
        }
    }

    LaunchedEffect(dropDownsState, registerState, cityList, categoryList) {
        if (dropDownsState is UiState.Success && registerState is UiState.Success && cityList.isNotEmpty()) {
            val profile = (registerState as UiState.Success).data.response
            
            if (selectedCity == null) {
                selectedCity = cityList.find { it.id.toString() == profile.city || it.name.equals(profile.city, ignoreCase = true) }
                selectedCity?.let { viewModel.fetchAreas(it.id.toString()) }
            }
            
            if (selectedBusinessCat == null) {
                selectedBusinessCat = categoryList.find { it.id.toString() == profile.vendorCategory || it.name.equals(profile.vendorCategory, ignoreCase = true) }
            }
        }
    }

    LaunchedEffect(areasState, registerState, areaList) {
        if (areasState is UiState.Success && registerState is UiState.Success && areaList.isNotEmpty()) {
            val profile = (registerState as UiState.Success).data.response
            if (selectedArea == null) {
                selectedArea = areaList.find { it.id.toString() == profile.area || it.name.equals(profile.area, ignoreCase = true) }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is ProfileDetailsViewModel.UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }

                ProfileDetailsViewModel.UiEvent.NavigateToProfile -> {
                    onBack()
                    Toast.makeText(context, "Profile Updated Successfully", Toast.LENGTH_SHORT).show()
                }
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
            Text(
                stringResource(R.string.add_business_details),
                style = MaterialTheme.typography.headlineMedium
            )
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
                KeyboardType.Phone,
                enabled = false
            ) { mobileNumber = it }

            Spacer(Modifier.height(12.dp))

            TitleText(stringResource(R.string.city))
            Spacer(Modifier.height(12.dp))
            CustomSpinner(
                label = stringResource(R.string.choose),
                items = cityList,
                selectedItem = selectedCity,
                onItemSelected = {
                    selectedCity = it
                    selectedArea = null
                    areaList = emptyList()
                    viewModel.fetchAreas(it.id.toString())
                }
            )
            Spacer(Modifier.height(12.dp))
            TitleText(stringResource(R.string.area))
            Spacer(Modifier.height(12.dp))
            CustomSpinner(
                label = stringResource(R.string.choose),
                items = areaList,
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
                items = categoryList,
                selectedItem = selectedBusinessCat,
                onItemSelected = {
                    selectedBusinessCat = it
                }
            )
            Spacer(Modifier.height(12.dp))
        }

        Button(
            onClick = {
                viewModel.profile(
                    area =  selectedArea?.id.toString() ?: "",
                    city = selectedCity?.id.toString() ?: "",
                    businessName = businessName,
                    name = yourName,
                    vendorCategory = selectedBusinessCat?.id.toString() ?: "",
                    viewType = "update"
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, bottom = 12.dp, end = 12.dp)
                .imePadding(),
            enabled = registerState !is UiState.Loading,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkBlue),
        ) {
            if (registerState is UiState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    "Update",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}