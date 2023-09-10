package com.phoenixigris.quizz.ui.register.ui.login

data class RegisterFormState(
    val usernameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val isDataValid: Boolean = false
)