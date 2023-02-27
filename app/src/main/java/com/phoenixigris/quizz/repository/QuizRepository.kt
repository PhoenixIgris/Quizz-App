package com.phoenixigris.quizz.repository

import android.util.Log
import com.phoenixigris.quizz.database.DataStoreHelper
import com.phoenixigris.quizz.network.apiservices.QuizApi
import com.phoenixigris.quizz.network.reponse.QuestionResponse
import com.phoenixigris.quizz.network.safeapicall.Resource
import com.phoenixigris.quizz.network.safeapicall.SafeApiCall
import javax.inject.Inject

private const val TAG = "QuizRepository"

class QuizRepository @Inject constructor(
    private val quizApi: QuizApi,
    private val dataStoreHelper: DataStoreHelper
) {

    suspend fun fetchRandomQuestions(): Resource<QuestionResponse> {
        val response = SafeApiCall.execute { quizApi.fetchRandomQuestion() }
        when (response) {
            is Resource.Success -> {
                Log.e(TAG, "fetchRandomQuestions: ${response.value}")
                dataStoreHelper.setRandomQuestion(response.value)
            }
            is Resource.Failure -> {
                Log.e(TAG, "fetchRandomQuestions: ${response.errorMsg}")
            }
            is Resource.Loading -> {}
        }
        return response
    }

    suspend fun fetchLinuxQuestions(): Resource<QuestionResponse> {
        val response = SafeApiCall.execute { quizApi.fetchLinuxQuestion() }
        when (response) {
            is Resource.Success -> {
                Log.e(TAG, "fetchLinuxQuestions: ${response.value}")
                dataStoreHelper.setLinuxQuestion(response.value)
            }
            is Resource.Failure -> {
                Log.e(TAG, "fetchLinuxQuestions: ${response.errorMsg}")
            }
            is Resource.Loading -> {}
        }
        return response
    }

    suspend fun fetchDevOpsQuestions(): Resource<QuestionResponse> {
        val response = SafeApiCall.execute { quizApi.fetchRandomQuestion() }
        when (response) {
            is Resource.Success -> {
                Log.e(TAG, "fetchLinuxQuestions: ${response.value}")
                dataStoreHelper.setDevOpsQuestion(response.value)
            }
            is Resource.Failure -> {
                Log.e(TAG, "fetchLinuxQuestions: ${response.errorMsg}")
            }
            is Resource.Loading -> {}
        }
        return response
    }

    suspend fun fetchDockerQuestions(): Resource<QuestionResponse> {
        val response = SafeApiCall.execute { quizApi.fetchRandomQuestion() }
        when (response) {
            is Resource.Success -> {
                Log.e(TAG, "fetchLinuxQuestions: ${response.value}")
                dataStoreHelper.setDockerQuestion(response.value)
            }
            is Resource.Failure -> {
                Log.e(TAG, "fetchLinuxQuestions: ${response.errorMsg}")
            }
            is Resource.Loading -> {}
        }
        return response
    }

    suspend fun fetchSqlQuestions(): Resource<QuestionResponse> {
        val response = SafeApiCall.execute { quizApi.fetchRandomQuestion() }
        when (response) {
            is Resource.Success -> {
                dataStoreHelper.setSqlQuestion(response.value)
            }
            is Resource.Failure -> {
            }
            is Resource.Loading -> {}
        }
        return response
    }

    suspend fun fetchCodeQuestions(): Resource<QuestionResponse> {
        val response = SafeApiCall.execute { quizApi.fetchRandomQuestion() }
        when (response) {
            is Resource.Success -> {
                dataStoreHelper.setCodeQuestion(response.value)
            }
            is Resource.Failure -> {
            }
            is Resource.Loading -> {}
        }
        return response
    }

    suspend fun fetchCMSQuestions(): Resource<QuestionResponse> {
        val response = SafeApiCall.execute { quizApi.fetchRandomQuestion() }
        when (response) {
            is Resource.Success -> {
                dataStoreHelper.setCMSQuestion(response.value)
            }
            is Resource.Failure -> {
            }
            is Resource.Loading -> {}
        }
        return response
    }
}