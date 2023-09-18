package com.danotech.rinfo.model.service.impl

import android.util.Patterns
import javax.inject.Singleton

class EmailValidator {
    fun isValidEmail(email: String): Boolean {
        // Implement your email validation logic here
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
