package com.phoenixigris.quizz.ui.register.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.phoenixigris.quizz.R
import com.phoenixigris.quizz.databinding.ActivityRegisterBinding
import com.phoenixigris.quizz.repository.AuthCallback
import com.phoenixigris.quizz.ui.login.ui.login.LoggedInUserView
import com.phoenixigris.quizz.ui.login.ui.login.LoginActivity
import com.phoenixigris.quizz.utils.afterTextChanged
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private val registerViewModel: RegisterViewModel by viewModels()
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        registerViewModel.registerFormState.observe(this, Observer {
            val registerState = it ?: return@Observer
            binding.register?.isEnabled = registerState.isDataValid
            binding.nameLyt?.isErrorEnabled = false
            binding.emailLyt?.isErrorEnabled = false
            binding.passwordLyt?.isErrorEnabled = false
            if (registerState.usernameError != null) {
                binding.nameLyt?.isErrorEnabled = true
                binding.nameLyt?.error = registerState.usernameError
            }
            if (registerState.emailError != null) {
                binding.emailLyt?.isErrorEnabled = true
                binding.emailLyt?.error = registerState.emailError
            }
            if (registerState.passwordError != null) {
                binding.passwordLyt?.isErrorEnabled = true
                binding.passwordLyt?.error = registerState.passwordError
            }
        })

        registerViewModel.registerResult.observe(this, Observer {
            val registerResult = it ?: return@Observer
            if (registerResult.error != null) {
                showregisterFailed(registerResult.error)
            }
            if (registerResult.success != null) {
                updateUiWithUser(registerResult.success)
            }
            setResult(Activity.RESULT_OK)
            finish()
            startActivity(Intent(this, LoginActivity::class.java))
        })

        binding.username?.afterTextChanged {
            registerViewModel.registerDataChanged(
                binding.username?.text.toString(),
                binding.email?.text.toString(),
                binding.password.text.toString()
            )
        }
        binding.email?.afterTextChanged {
            registerViewModel.registerDataChanged(
                binding.username?.text.toString(),
                binding.email?.text.toString(),
                binding.password.text.toString()
            )
        }
        binding.password.apply {
            afterTextChanged {
                registerViewModel.registerDataChanged(
                    binding.username?.text.toString(),
                    binding.email?.text.toString(),
                    binding.password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        register(
                            binding.username?.text.toString(),
                            binding.email?.text.toString(),
                            binding.password.text.toString()
                        )
                }
                false
            }
        }
        binding.register?.setOnClickListener {
            register(
                binding.username?.text.toString(),
                binding.email?.text.toString(),
                binding.password.text.toString()
            )
        }
        binding.login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }

    private fun register(username: String, email: String, password: String) {
        binding.loading.visibility = View.VISIBLE
        registerViewModel.register(username, email, password, object : AuthCallback {
            override fun onSignUpSuccess() {
                binding.loading.visibility = View.GONE
                this@RegisterActivity.finish()
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            }

            override fun onSignUpFailure(message: String) {
                binding.loading.visibility = View.GONE
                Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
            }

        })
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

    private fun showregisterFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}
