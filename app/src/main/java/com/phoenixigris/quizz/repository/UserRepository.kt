package com.phoenixigris.quizz.repository

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.gson.Gson
import com.phoenixigris.quizz.database.DataStoreHelper
import com.phoenixigris.quizz.utils.Constants
import javax.inject.Inject


interface AuthCallback {
    fun onSignUpSuccess()
    fun onSignUpFailure(message: String)
}

private const val TAG = "UserRepository"

class UserRepository @Inject constructor(
    private val dataStoreHelper: DataStoreHelper
) {
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun register(userName: String, email: String, password: String, callback: AuthCallback) {
        try {
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        callback.onSignUpFailure(
                            task.exception?.message ?: "SignUp Unsuccessful, Please Try Again"
                        )
                    } else {
                        setLoggedInUser(userName)
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
            mAuth.signInWithEmailAndPassword(
                email,
                password
            )
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.e(TAG, "login success ${task.result}")
                        callback.onSignUpSuccess()
                    } else {
                        Log.e(TAG, "login failed ${task.exception}")
                        callback.onSignUpFailure(task.exception?.message.toString())
                    }
                }
        } catch (e: Throwable) {
            return callback.onSignUpFailure("SignUp Unsuccessful, ${e.message}")
        }
    }

    suspend fun setLoggedInUserInfo() {
        dataStoreHelper.saveBooleanToDatastore(Constants.IS_USER_LOGGED_IN to true)
        dataStoreHelper.saveStringToDatastore(
            Constants.LOGGED_IN_USER_DETAILS to Gson().toJson(
                mAuth.currentUser
            )
        )
    }


    private fun setLoggedInUser(userName: String) {
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(userName)
            .build()
        mAuth.currentUser?.updateProfile(profileUpdates)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "User profile updated.")
            }
        }
    }
}