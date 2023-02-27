package com.phoenixigris.quizz.database

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.phoenixigris.quizz.helpers.Constants
import com.phoenixigris.quizz.network.reponse.QuestionResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

const val DATA_STORE_NAME = "Quizz Datastore"
private val Context.dataStore by preferencesDataStore(DATA_STORE_NAME)

class DataStoreHelper @Inject constructor(@ApplicationContext appContext: Context) {

    private val dataStore = appContext.dataStore


    private val randomQuestionKey = stringPreferencesKey(Constants.RANDOM)
    private val linuxQuestionKey = stringPreferencesKey(Constants.LINUX)
    private val devOpsQuestionKey = stringPreferencesKey(Constants.DEVOPS)
    private val dockerQuestionKey = stringPreferencesKey(Constants.DOCKER)
    private val sqlQuestionKey = stringPreferencesKey(Constants.SQL)
    private val codeQuestionKey = stringPreferencesKey(Constants.CODE)
    private val cmsQuestionKey = stringPreferencesKey(Constants.CMS)

    suspend fun setRandomQuestion(question: QuestionResponse) {
        val questionString = Gson().toJson(question)
        editStore(randomQuestionKey, questionString)
    }

    fun getRandomQuestion(): Flow<QuestionResponse> {
        return dataStore.data.map { store ->
            val subscriptionJson = store[randomQuestionKey]
            Gson().fromJson(subscriptionJson, QuestionResponse::class.java)
        }
    }


    suspend fun setLinuxQuestion(question: QuestionResponse) {
        val questionString = Gson().toJson(question)
        editStore(linuxQuestionKey, questionString)
    }

    fun getLinuxQuestion(): Flow<QuestionResponse> {
        return dataStore.data.map { store ->
            val subscriptionJson = store[linuxQuestionKey]
            Gson().fromJson(subscriptionJson, QuestionResponse::class.java)
        }
    }


    suspend fun setDevOpsQuestion(question: QuestionResponse) {
        val questionString = Gson().toJson(question)
        editStore(devOpsQuestionKey, questionString)
    }

    fun getDevOpsQuestion(): Flow<QuestionResponse> {
        return dataStore.data.map { store ->
            val subscriptionJson = store[devOpsQuestionKey]
            Gson().fromJson(subscriptionJson, QuestionResponse::class.java)
        }
    }


    suspend fun setDockerQuestion(question: QuestionResponse) {
        val questionString = Gson().toJson(question)
        editStore(dockerQuestionKey, questionString)
    }

    fun getDockerQuestion(): Flow<QuestionResponse> {
        return dataStore.data.map { store ->
            val subscriptionJson = store[dockerQuestionKey]
            Gson().fromJson(subscriptionJson, QuestionResponse::class.java)
        }
    }


    suspend fun setSqlQuestion(question: QuestionResponse) {
        val questionString = Gson().toJson(question)
        editStore(sqlQuestionKey, questionString)
    }

    fun getSqlQuestion(): Flow<QuestionResponse> {
        return dataStore.data.map { store ->
            val subscriptionJson = store[sqlQuestionKey]
            Gson().fromJson(subscriptionJson, QuestionResponse::class.java)
        }
    }

    suspend fun setCodeQuestion(question: QuestionResponse) {
        val questionString = Gson().toJson(question)
        editStore(codeQuestionKey, questionString)
    }

    fun getCodeQuestion(): Flow<QuestionResponse> {
        return dataStore.data.map { store ->
            val subscriptionJson = store[codeQuestionKey]
            Gson().fromJson(subscriptionJson, QuestionResponse::class.java)
        }
    }

    suspend fun setCMSQuestion(question: QuestionResponse) {
        val questionString = Gson().toJson(question)
        editStore(cmsQuestionKey, questionString)
    }

    fun getCMSQuestion(): Flow<QuestionResponse> {
        return dataStore.data.map { store ->
            val subscriptionJson = store[cmsQuestionKey]
            Gson().fromJson(subscriptionJson, QuestionResponse::class.java)
        }
    }


    private suspend fun <T> editStore(key: Preferences.Key<T>, value: T) {
        dataStore.edit { store ->
            store[key] = value
        }
    }
}