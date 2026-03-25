package com.iprism.adbotsvendor.presentation.ui.components

import android.graphics.Color
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iprism.adbotsvendor.presentation.ui.theme.Grey

@Composable
fun OTPView(
    otpLength: Int = 4,
    onOtpComplete: (String) -> Unit
) {

    val focusRequesters = List(otpLength) { FocusRequester() }
    val otpValues = remember { mutableStateListOf("", "", "", "") }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {

        repeat(otpLength) { index ->

            OutlinedTextField(
                value = otpValues[index],
                onValueChange = { value ->

                    if (value.length <= 1) {
                        otpValues[index] = value

                        if (value.isNotEmpty()) {
                            if (index < otpLength - 1) {
                                focusRequesters[index + 1].requestFocus()
                            }
                        }
                    }

                    val otp = otpValues.joinToString("")
                    if (otp.length == otpLength) {
                        onOtpComplete(otp)
                    }
                },
                modifier = Modifier
                    .width(60.dp)
                    .focusRequester(focusRequesters[index]),
                textStyle = TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Grey,
                    unfocusedBorderColor = Grey,
                    cursorColor = Grey
                )
            )
        }
    }
}