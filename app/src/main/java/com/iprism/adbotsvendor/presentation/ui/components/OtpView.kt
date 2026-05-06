package com.iprism.adbotsvendor.presentation.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OTPView(
    otpLength: Int = 4,
    onOtpComplete: (String) -> Unit
) {
    var otpValue by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        // Hidden BasicTextField to handle input
        BasicTextField(
            value = otpValue,
            onValueChange = {
                if (it.length <= otpLength && it.all { char -> char.isDigit() }) {
                    otpValue = it
                    onOtpComplete(it)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .focusRequester(focusRequester)
                .fillMaxWidth()
                .height(60.dp), // Cover the visual area to capture clicks
            textStyle = TextStyle(color = Color.Transparent),
            cursorBrush = androidx.compose.ui.graphics.SolidColor(Color.Transparent) // Hide native cursor
        )

        // Visible Row of OTP boxes
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            repeat(otpLength) { index ->
                val char = when {
                    index < otpValue.length -> otpValue[index].toString()
                    else -> ""
                }
                
                val isFocused = otpValue.length == index

                Box(
                    modifier = Modifier
                        .width(60.dp)
                        .height(60.dp)
                        .border(
                            width = 1.2.dp,
                            color = if (isFocused) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = char,
                        style = TextStyle(
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                    
                    if (isFocused) {
                        BlinkingCursor()
                    }
                }
            }
        }
    }
}

@Composable
fun BlinkingCursor() {
    val infiniteTransition = rememberInfiniteTransition(label = "cursor")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1000
                1f at 0
                1f at 499
                0f at 500
                0f at 999
            },
            repeatMode = RepeatMode.Restart
        ),
        label = "cursorAlpha"
    )

    Box(
        modifier = Modifier
            .width(2.dp)
            .height(24.dp)
            .background(MaterialTheme.colorScheme.primary.copy(alpha = alpha))
    )
}
