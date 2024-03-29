package com.phoenixigris.quizz.ui.login.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.phoenixigris.quizz.R
import com.phoenixigris.quizz.databinding.ActivityLoginBinding
import com.phoenixigris.quizz.repository.AuthCallback
import com.phoenixigris.quizz.ui.MainActivity
import com.phoenixigris.quizz.ui.register.ui.login.RegisterActivity
import com.phoenixigris.quizz.utils.afterTextChanged
import dagger.hilt.android.AndroidEntryPoint


private const val TAG = "LoginActivity"

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer
            binding.login.isEnabled = loginState.isDataValid
            binding.emailLyt?.isErrorEnabled = false
            binding.passwordLyt?.isErrorEnabled = false
            if (loginState.usernameError != null) {
                binding.emailLyt?.isErrorEnabled = true
                binding.emailLyt?.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                binding.passwordLyt?.isErrorEnabled = true
                binding.passwordLyt?.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)

        })
        binding.username.afterTextChanged {
            loginViewModel.loginDataChanged(
                binding.username.text.toString(),
                binding.password.text.toString()
            )
        }

        binding.password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    binding.username.text.toString(),
                    binding.password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        login(
                            binding.username.text.toString(),
                            binding.password.text.toString()
                        )
                }
                false
            }
        }
        binding.login.setOnClickListener {
            login(
                binding.username.text.toString(),
                binding.password.text.toString()
            )
        }
        binding.register?.setOnClickListener {
            this.finish()
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }

    private fun login(email: String, password: String) {
        binding.loading.isVisible = true
        loginViewModel.login(
            email,
            password, object : AuthCallback {
                override fun onSignUpSuccess() {
                    binding.loading.isVisible = false
                    loginViewModel.setUserInfo()
                    this@LoginActivity.finish()
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                }

                override fun onSignUpFailure(message: String) {
                    binding.loading.isVisible = false
                    Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
                }

            }
        )
    }
}


