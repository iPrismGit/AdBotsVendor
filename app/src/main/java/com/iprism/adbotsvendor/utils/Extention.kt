package com.iprism.adbotsvendor.utils

import android.content.Context
import android.widget.Toast

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}
/*fun Context.getUserDetails(): HashMap<String, String?> {
    val user = User(this)
    return user.getUserDetails()
}*/