package com.phoenixigris.quizz.network.safeapicall

interface ApiCallListener {
    fun onError(errorMessage: String?)
    fun onSuccess(message: String?, data: String?)
}