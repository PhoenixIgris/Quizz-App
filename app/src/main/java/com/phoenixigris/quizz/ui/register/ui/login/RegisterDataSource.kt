package com.phoenixigris.quizz.ui.register.ui.login

import com.google.firebase.auth.FirebaseAuth
import com.phoenixigris.quizz.repository.AuthCallback
import javax.inject.Inject

class RegisterDataSource @Inject constructor() {

    suspend fun register(email: String, password: String, callback: AuthCallback) {
        try {
            val mAuth = FirebaseAuth.getInstance()
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        callback.onSignUpFailure(
                            task.exception?.message ?: "SignUp Unsuccessful, Please Try Again"
                        )
                    } else {
                        callback.onSignUpSuccess()
                    }
                }
        } catch (e: Throwable) {
            return callback.onSignUpFailure("SignUp Unsuccessful, ${e.message}")
        }
    }

}