package com.phoenixigris.quizz.database

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.phoenixigris.quizz.network.reponse.QuestionResponse
import com.phoenixigris.quizz.utils.Constants
import com.phoenixigris.quizz.utils.QuizModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
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
    private val quizStatusKey = stringPreferencesKey(Constants.QUIZ_STATUS)
    suspend fun setRandomQuestion(question: QuestionResponse) {
        val questionString = Gson().toJson(question)
        editStore(randomQuestionKey, questionString)
    }

    fun getRandomQuestion(): Flow<QuestionResponse> {
        return dataStore.data.map { store ->
            val json = store[randomQuestionKey]
            Gson().fromJson(json, QuestionResponse::class.java) ?: QuestionResponse()
        }
    }


    suspend fun setLinuxQuestion(question: QuestionResponse) {
        val questionString = Gson().toJson(question)
        editStore(linuxQuestionKey, questionString)
    }

    fun getLinuxQuestion(): Flow<QuestionResponse> {
        return dataStore.data.map { store ->
            val json = store[linuxQuestionKey]
            Gson().fromJson(json, QuestionResponse::class.java) ?: QuestionResponse()
        }
    }


    suspend fun setDevOpsQuestion(question: QuestionResponse) {
        val questionString = Gson().toJson(question)
        editStore(devOpsQuestionKey, questionString)
    }

    fun getDevOpsQuestion(): Flow<QuestionResponse> {
        return dataStore.data.map { store ->
            val json = store[devOpsQuestionKey]
            Gson().fromJson(json, QuestionResponse::class.java) ?: QuestionResponse()
        }
    }


    suspend fun setDockerQuestion(question: QuestionResponse) {
        val questionString = Gson().toJson(question)
        editStore(dockerQuestionKey, questionString)
    }

    fun getDockerQuestion(): Flow<QuestionResponse> {
        return dataStore.data.map { store ->
            val json = store[dockerQuestionKey]
            Gson().fromJson(json, QuestionResponse::class.java) ?: QuestionResponse()
        }
    }


    suspend fun setSqlQuestion(question: QuestionResponse) {
        val questionString = Gson().toJson(question)
        editStore(sqlQuestionKey, questionString)
    }

    fun getSqlQuestion(): Flow<QuestionResponse> {
        return dataStore.data.map { store ->
            val json = store[sqlQuestionKey]
            Gson().fromJson(json, QuestionResponse::class.java) ?: QuestionResponse()
        }
    }

    suspend fun setCodeQuestion(question: QuestionResponse) {
        val questionString = Gson().toJson(question)
        editStore(codeQuestionKey, questionString)
    }

    fun getCodeQuestion(): Flow<QuestionResponse> {
        return dataStore.data.map { store ->
            val json = store[codeQuestionKey]
            Gson().fromJson(json, QuestionResponse::class.java) ?: QuestionResponse()
        }
    }

    suspend fun setCMSQuestion(question: QuestionResponse) {
        val questionString = Gson().toJson(question)
        editStore(cmsQuestionKey, questionString)
    }

    fun getCMSQuestion(): Flow<QuestionResponse> {
        return dataStore.data.map { store ->
            val json = store[cmsQuestionKey]
            Gson().fromJson(json, QuestionResponse::class.java) ?: QuestionResponse()
        }
    }

    suspend fun setQuizStatusList(quizStatus: List<QuizModel>) {
        val quizStatusList = Gson().toJson(quizStatus)
        Log.e("Debug", "setQuizStatusList: $quizStatusList ", )
        editStore(quizStatusKey, quizStatusList)
    }

    fun getQuizStatusList(): Flow<List<QuizModel>> {
        return dataStore.data.map { store ->
            Gson().fromJson(
                store[quizStatusKey],
                object : TypeToken<List<QuizModel>>() {}.type
            ) ?: emptyList()
        }
    }

    private suspend fun <T> editStore(key: Preferences.Key<T>, value: T) {
        dataStore.edit { store ->
            store[key] = value
        }
    }


    suspend fun saveStringToDatastore(data: Pair<String, String>) =
        withContext(Dispatchers.IO) {
            Log.d("DataStoreHelper", "saveStringToDatastore: $data")
            val prefKey = stringPreferencesKey(data.first)
            dataStore.edit {
                it[prefKey] = data.second
            }
        }

    suspend fun readStringFromDatastore(key: String): String? =
        withContext(Dispatchers.IO) {
            val datastoreKey = stringPreferencesKey(key)
            val value = dataStore.data.first()[datastoreKey]
            Log.d("DataStoreHelper", "readStringFromDatastore: $key -> $value")
            return@withContext value
        }


    suspend fun saveBooleanToDatastore(data: Pair<String, Boolean>) =
        withContext(Dispatchers.IO) {
            Log.d("DataStoreHelper", "saveBooleanToDatastore: $data")
            val prefKey = booleanPreferencesKey(data.first)
            dataStore.edit {
                it[prefKey] = data.second
            }
        }

    suspend fun readBooleanFromDatastore(key: String): Boolean =
        withContext(Dispatchers.IO) {
            val datastoreKey = booleanPreferencesKey(key)
            val value = dataStore.data.first()[datastoreKey] ?: false
            Log.d("DataStoreHelper", "readBooleanFromDatastore: $key -> $value")
            return@withContext value
        }

    suspend fun clear() {
        dataStore.edit {
            it.clear()
        }
    }

}