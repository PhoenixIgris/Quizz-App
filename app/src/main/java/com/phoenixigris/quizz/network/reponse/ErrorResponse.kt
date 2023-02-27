package com.phoenixigris.quizz.network.reponse

data class ErrorResponse(
    val code: Int,
    val message: String,
    val status: Boolean
)
