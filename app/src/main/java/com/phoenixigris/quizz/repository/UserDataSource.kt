package com.phoenixigris.quizz.repository

import android.content.Intent
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

interface AuthCallback {
    fun onSignUpSuccess()
    fun onSignUpFailure(message: String)
}

class UserDataSource @Inject constructor() {

    fun register(email: String, password: String, callback: AuthCallback) {
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

    fun logout() {
    }

    fun login(email: String, password: String, callback: AuthCallback) {
        try {
            val mAuth = FirebaseAuth.getInstance()
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    callback.onSignUpFailure(task.exception?.message ?: "Error")
                } else {
                    callback.onSignUpSuccess()
                }
            }
        } catch (e: Throwable) {
            return callback.onSignUpFailure("SignUp Unsuccessful, ${e.message}")
        }
    }

}