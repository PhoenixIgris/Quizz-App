package com.phoenixigris.quizz.ui.login.ui.login

data class LoginResult(
    val success: LoggedInUserView? = null,
    val error: Int? = null
)