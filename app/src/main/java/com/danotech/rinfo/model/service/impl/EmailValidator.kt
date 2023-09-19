package com.danotech.rinfo.model.service.impl

import android.util.Patterns

class EmailValidator {
    fun isValidEmail(email: String): Boolean {
        // Implement your email validation logic here
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        // Implement your password validation logic here
        return password.length >= 6
    }
}
