package com.phoenixigris.quizz.repository

import com.phoenixigris.quizz.ui.login.data.Result
import com.phoenixigris.quizz.ui.login.data.model.LoggedInUser
import javax.inject.Inject


class UserRepository @Inject constructor(val dataSource: UserDataSource) {

    var user: String? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {

        user = null
    }

    fun logout() {
        user = null
        dataSource.logout()
    }

    fun register(username: String, email: String, password: String) {
        val result = dataSource.register(email, password, object : AuthCallback {
            override fun onSignUpSuccess() {
                setLoggedInUser(username)
            }

            override fun onSignUpFailure(message: String) {

            }

        })
        return result
    }

    private fun setLoggedInUser(loggedInUser: String) {
        this.user = loggedInUser
    }

    fun login(
        email: String,
        password: String
    ) {
        val result = dataSource.login(email, password, object : AuthCallback {
            override fun onSignUpSuccess() {
                setLoggedInUser(email)
            }

            override fun onSignUpFailure(message: String) {

            }

        })
        return result
    }
}