package com.iprism.adbotsvendor.presentation.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.iprism.adbotsvendor.R
import com.iprism.adbotsvendor.presentation.ui.components.CustomSpinner
import com.iprism.adbotsvendor.presentation.ui.components.CustomTextField
import com.iprism.adbotsvendor.presentation.ui.components.LoadingScreen
import com.iprism.adbotsvendor.presentation.ui.components.SpinnerItem
import com.iprism.adbotsvendor.presentation.ui.components.TitleText
import com.iprism.adbotsvendor.presentation.ui.theme.BLACK
import com.iprism.adbotsvendor.presentation.ui.theme.BLACK1
import com.iprism.adbotsvendor.presentation.ui.theme.DarkBlue
import com.iprism.adbotsvendor.presentation.ui.theme.LightGrey1
import com.iprism.adbotsvendor.presentation.ui.theme.MontserratFamily
import com.iprism.adbotsvendor.presentation.ui.theme.White
import com.iprism.adbotsvendor.presentation.viewmodels.AddPromotionViewModel
import com.iprism.adbotsvendor.utils.UiState
import kotlinx.coroutines.flow.collectLatest
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromotionScreen(
    onBack: () -> Unit,
    onContinueClick: () -> Unit,
    viewModel: AddPromotionViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val formState by viewModel.formState.collectAsStateWithLifecycle()
    val dropDownsState by viewModel.dropDownsResponse.collectAsStateWithLifecycle()
    val areasState by viewModel.areasResponse.collectAsStateWithLifecycle()

    var showBottomSheet by remember { mutableStateOf(false) }
    var bottomSheetStep by remember { mutableIntStateOf(0) }
    val sheetState = rememberModalBottomSheetState()

    var showVideoPreview by remember { mutableStateOf(false) }

    val videoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let { viewModel.updateVideoUri(it.toString()) }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        // Even if permission is denied, we can still try to launch the picker 
        // as it often works without it on many Android versions.
        videoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly))
        if (!isGranted) {
            Toast.makeText(context, "Continuing with limited access", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.validationEvent.collectLatest { isValid ->
            if (isValid) showBottomSheet = true
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.toastMessage.collectLatest { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    val cities = remember(dropDownsState) {
        (dropDownsState as? UiState.Success)?.data?.response?.cities?.map {
            SpinnerItem(it.cityName, it.id.toIntOrNull() ?: 0)
        } ?: emptyList()
    }

    val categories = remember(dropDownsState) {
        (dropDownsState as? UiState.Success)?.data?.response?.categories?.map {
            SpinnerItem(it.name, it.id.toIntOrNull() ?: 0)
        } ?: emptyList()
    }

    val areas = remember(areasState) {
        (areasState as? UiState.Success)?.data?.response?.areas?.map {
            SpinnerItem(it.name, it.id.toIntOrNull() ?: 0)
        } ?: emptyList()
    }

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
                ChooseDatesContent(onContinue = { start, end ->
                    viewModel.setDates(start, end)
                    bottomSheetStep = 1
                })
            } else {
                HowManyScreensContent(onContinue = { count ->
                    viewModel.setScreenCount(count)
                    showBottomSheet = false
                    bottomSheetStep = 0
                    onContinueClick()
                })
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
                onClick = onBack,
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
                    formState.name,
                    stringResource(R.string.enter),
                    KeyboardType.Text,
                    onValueChange = viewModel::updateName
                )

                Spacer(Modifier.height(12.dp))
                TitleText(stringResource(R.string.business_name))
                Spacer(Modifier.height(12.dp))
                CustomTextField(
                    formState.businessName,
                    stringResource(R.string.enter),
                    KeyboardType.Text,
                    onValueChange = viewModel::updateBusinessName
                )

                Spacer(Modifier.height(12.dp))
                TitleText(stringResource(R.string.mobile_number))
                Spacer(Modifier.height(12.dp))
                CustomTextField(
                    formState.mobile,
                    stringResource(R.string.enter),
                    KeyboardType.Phone,
                    onValueChange = viewModel::updateMobile
                )

                Spacer(Modifier.height(12.dp))
                TitleText(stringResource(R.string.city))
                Spacer(Modifier.height(12.dp))
                CustomSpinner(
                    label = stringResource(R.string.choose),
                    items = cities,
                    selectedItem = cities.find { it.id.toString() == formState.cityId },
                    onItemSelected = { viewModel.updateCity(it.id.toString(), it.name) }
                )

                Spacer(Modifier.height(12.dp))
                TitleText(stringResource(R.string.area))
                Spacer(Modifier.height(12.dp))
                CustomSpinner(
                    label = stringResource(R.string.choose),
                    items = areas,
                    selectedItem = areas.find { it.id.toString() == formState.areaId },
                    onItemSelected = { viewModel.updateArea(it.id.toString(), it.name) }
                )

                Spacer(Modifier.height(12.dp))
                TitleText(stringResource(R.string.business_category))
                Spacer(Modifier.height(12.dp))
                CustomSpinner(
                    label = stringResource(R.string.choose),
                    items = categories,
                    selectedItem = categories.find { it.id.toString() == formState.categoryId },
                    onItemSelected = { viewModel.updateCategory(it.id.toString(), it.name) }
                )
                Spacer(Modifier.height(16.dp))

                TitleText("Promotional Video")
                Spacer(Modifier.height(12.dp))
                VideoUploadBox(
                    videoUri = formState.videoUri,
                    onUploadClick = {
                        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            Manifest.permission.READ_MEDIA_VIDEO
                        } else {
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        }

                        when {
                            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
                                // Modern Android: Photo Picker doesn't need permissions
                                videoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly))
                            }
                            ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED -> {
                                videoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly))
                            }
                            else -> {
                                permissionLauncher.launch(permission)
                            }
                        }
                    },
                    onPreviewClick = { showVideoPreview = true },
                    onDeleteClick = { viewModel.updateVideoUri(null) }
                )

                Spacer(Modifier.height(12.dp))
            }

            Button(
                onClick = viewModel::validateBusinessDetails,
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

        if (dropDownsState is UiState.Loading) {
            LoadingScreen()
        }

        if (showVideoPreview && formState.videoUri != null) {
            VideoPreviewDialog(
                videoUri = formState.videoUri!!,
                onDismiss = { showVideoPreview = false }
            )
        }
    }
}

@Composable
fun VideoUploadBox(
    videoUri: String?,
    onUploadClick: () -> Unit,
    onPreviewClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .border(1.dp, LightGrey1, RoundedCornerShape(12.dp))
            .background(White, RoundedCornerShape(12.dp))
            .clickable(enabled = videoUri == null) { onUploadClick() },
        contentAlignment = Alignment.Center
    ) {
        if (videoUri == null) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.PlayCircle,
                    contentDescription = null,
                    tint = DarkBlue,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(Modifier.height(8.dp))
                Text("Upload Promotional Video", style = MaterialTheme.typography.bodySmall, color = BLACK1)
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize().padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PlayCircle,
                    contentDescription = null,
                    tint = DarkBlue,
                    modifier = Modifier.size(48.dp).clickable { onPreviewClick() }
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "Video Selected",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = DarkBlue
                )
                TextButton(onClick = onDeleteClick) {
                    Text("Remove Video", color = Color.Red)
                }
            }
        }
    }
}

@Composable
fun VideoPreviewDialog(
    videoUri: String,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoUri))
            prepare()
            playWhenReady = true
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                AndroidView(
                    factory = {
                        PlayerView(it).apply {
                            player = exoPlayer
                            useController = true
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )

                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                        .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(24.dp))
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseDatesContent(onContinue: (String, String) -> Unit) {
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
        ) { DatePicker(state = datePickerState) }
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
        ) { DatePicker(state = datePickerState) }
    }

    Column(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
        Text("Choose Dates", style = MaterialTheme.typography.headlineSmall, color = Color.Black)
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Start Date", style = MaterialTheme.typography.bodySmall, color = BLACK1)
                Spacer(modifier = Modifier.height(12.dp))
                DateSelectorBox(
                    text = startDate?.let { dateFormatter.format(Date(it)) } ?: "Choose",
                    onClick = { showStartDatePicker = true }
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text("End Date", style = MaterialTheme.typography.bodySmall, color = BLACK1)
                Spacer(modifier = Modifier.height(12.dp))
                DateSelectorBox(
                    text = endDate?.let { dateFormatter.format(Date(it)) } ?: "Choose",
                    onClick = { showEndDatePicker = true }
                )
            }
        }
        Spacer(modifier = Modifier.height(48.dp))
        Button(
            onClick = {
                if (startDate != null && endDate != null) {
                    onContinue(dateFormatter.format(Date(startDate!!)), dateFormatter.format(Date(endDate!!)))
                }
            },
            enabled = startDate != null && endDate != null,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = DarkBlue,
                disabledContainerColor = Color(0xFFEEEEEE),
                contentColor = Color.White,
                disabledContentColor = Color.LightGray
            )
        ) {
            Text("Continue", style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(8.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun HowManyScreensContent(onContinue: (Int) -> Unit) {
    var screenCount by remember { mutableIntStateOf(1) }
    Column(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
        Text("How Many Screens", style = MaterialTheme.typography.headlineSmall, color = Color.Black)
        Spacer(modifier = Modifier.height(32.dp))
        Text("Select", style = MaterialTheme.typography.bodySmall, color = BLACK1)
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, LightGrey1, RoundedCornerShape(12.dp))
                .background(White, RoundedCornerShape(12.dp))
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(String.format("%02d", screenCount), fontFamily = MontserratFamily, color = BLACK, fontSize = 12.sp)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text("-", fontSize = 30.sp, fontWeight = FontWeight.Bold, modifier = Modifier.clickable { if (screenCount > 1) screenCount-- })
                Text(screenCount.toString(), fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = MontserratFamily, color = BLACK)
                Text("+", fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.clickable { screenCount++ })
            }
        }
        Spacer(modifier = Modifier.height(48.dp))
        Button(
            onClick = { onContinue(screenCount) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkBlue)
        ) {
            Text("Continue", style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(8.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun DateSelectorBox(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .border(1.dp, LightGrey1, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(horizontal = 12.dp),
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
                color = if (text == "Choose") Color.LightGray else BLACK,
                fontSize = 12.sp
            )
            Icon(painter = painterResource(R.drawable.calender_img), contentDescription = null, modifier = Modifier.size(20.dp), tint = BLACK1)
        }
    }
}
