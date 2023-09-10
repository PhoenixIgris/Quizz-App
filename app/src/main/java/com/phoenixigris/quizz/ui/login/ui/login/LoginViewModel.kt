package com.phoenixigris.quizz.ui.login.ui.login

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.phoenixigris.quizz.R
import com.phoenixigris.quizz.repository.UserRepository
import com.phoenixigris.quizz.ui.login.data.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: UserRepository) :
    ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val result = loginRepository.login(email, password)

//            if (result is Result.Success) {
//                _loginResult.value =
//                    LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
//            } else {
//                _loginResult.value = LoginResult(error = R.string.login_failed)
//            }
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }


    private fun isUserNameValid(username: String): Boolean {
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