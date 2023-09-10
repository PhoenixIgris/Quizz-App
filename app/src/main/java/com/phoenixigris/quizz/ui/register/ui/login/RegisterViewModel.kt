package com.phoenixigris.quizz.ui.register.ui.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.phoenixigris.quizz.repository.UserRepository
import com.phoenixigris.quizz.ui.login.ui.login.LoggedInUserView
import com.phoenixigris.quizz.ui.login.ui.login.LoginResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    private val _registerForm = MutableLiveData<RegisterFormState>()
    val registerFormState: LiveData<RegisterFormState> = _registerForm

    private val _registerResult = MutableLiveData<LoginResult>()
    val registerResult: LiveData<LoginResult> = _registerResult

    fun register(username: String, email: String, password: String) {
        val result = userRepository.register(username,email, password)
//        if (result is com.phoenixigris.quizz.ui.login.data.Result.Success) {
//            _registerResult.value =
//                LoginResult(success = LoggedInUserView(displayName = username))
//        } else {
//            _registerResult.value = LoginResult(error = 0)
//        }
    }

    fun registerDataChanged(username: String, email: String, password: String) {
        if (username.isEmpty()) {
            _registerForm.value = RegisterFormState(usernameError = "Invalid Name")
        } else if (!isEmailValid(email)) {
            _registerForm.value = RegisterFormState(emailError = "Invalid Email")
        } else if (!isPasswordValid(password)) {
            _registerForm.value = RegisterFormState(passwordError = "Invalid Password")
        } else {
            _registerForm.value = RegisterFormState(isDataValid = true)
        }
    }

    private fun isEmailValid(username: String): Boolean {
        return if (username.isNotBlank()) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            false
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}