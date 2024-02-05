package com.phoenixigris.quizz.repository

import android.util.Log
import com.google.gson.Gson
import com.phoenixigris.quizz.R
import com.phoenixigris.quizz.database.DataStoreHelper
import com.phoenixigris.quizz.network.apiservices.QuizApi
import com.phoenixigris.quizz.network.reponse.QuestionAnswerListResponse
import com.phoenixigris.quizz.network.reponse.QuestionResponse
import com.phoenixigris.quizz.network.reponse.QuizCategoryModel
import com.phoenixigris.quizz.network.reponse.TriviaCategory
import com.phoenixigris.quizz.network.safeapicall.Resource
import com.phoenixigris.quizz.network.safeapicall.SafeApiCall
import com.phoenixigris.quizz.utils.Constants
import com.phoenixigris.quizz.utils.QuizModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val TAG = "QuizRepository"

class QuizRepository @Inject constructor(
    private val quizApi: QuizApi,
    private val dataStoreHelper: DataStoreHelper
) {

//    suspend fun fetchRandomQuestions(): Resource<QuestionResponse> {
//        val response = SafeApiCall.execute { quizApi.fetchRandomQuestion() }
//        when (response) {
//            is Resource.Success -> {
//                Log.e(TAG, "fetchRandomQuestions: ${response.value}")
//                dataStoreHelper.setRandomQuestion(response.value)
//            }
//            is Resource.Failure -> {
//                Log.e(TAG, "fetchRandomQuestions: ${response.errorMsg}")
//            }
//            is Resource.Loading -> {}
//        }
//        return response
//    }

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
        val response = SafeApiCall.execute { quizApi.fetchDevOpsQuestion() }
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
        val response = SafeApiCall.execute { quizApi.fetchDockerQuestion() }
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

//    suspend fun fetchSqlQuestions(): Resource<QuestionResponse> {
//        val response = SafeApiCall.execute { quizApi.fetchRandomQuestion() }
//        when (response) {
//            is Resource.Success -> {
//                dataStoreHelper.setSqlQuestion(response.value)
//            }
//
//            is Resource.Failure -> {
//            }
//
//            is Resource.Loading -> {}
//        }
//        return response
//    }

    suspend fun fetchCodeQuestions(): Resource<QuestionResponse> {
        val response = SafeApiCall.execute { quizApi.fetchCodeQuestion() }
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
        val response = SafeApiCall.execute { quizApi.fetchCMSQuestion() }
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

    fun getRandomQuestionList() = dataStoreHelper.getRandomQuestion()
    fun getLinuxQuestion() = dataStoreHelper.getLinuxQuestion()
    fun getDevOpsQuestionList() = dataStoreHelper.getDevOpsQuestion()
    fun getDockerQuestionList() = dataStoreHelper.getDockerQuestion()
    fun getSqlQuestionList() = dataStoreHelper.getSqlQuestion()
    fun getCodeQuestionList() = dataStoreHelper.getCodeQuestion()
    fun getCmsQuestionList() = dataStoreHelper.getCMSQuestion()

    fun getQuizStatusList(): Flow<List<QuizModel>> {
        return dataStoreHelper.getQuizStatusList()
    }


    suspend fun getCategoryList(): List<com.phoenixigris.quizz.ui.home.model.QuizCategoryModel> {
        val categoryModel: QuizCategoryModel? =
            Gson().fromJson(
                dataStoreHelper.readStringFromDatastore(Constants.CATEGORY),
                QuizCategoryModel::class.java
            )

        categoryModel?.trivia_categories?.let {
            return getFormattedCategoryList(it)

        }
        return emptyList()
    }

    private fun getFormattedCategoryList(triviaCategories: List<TriviaCategory>): List<com.phoenixigris.quizz.ui.home.model.QuizCategoryModel> {
        val list = mutableListOf<com.phoenixigris.quizz.ui.home.model.QuizCategoryModel>()
        triviaCategories.forEach { category ->
            list.add(
                com.phoenixigris.quizz.ui.home.model.QuizCategoryModel(
                    id = category.id,
                    name = simplifyCategoryName(category.name),
                    imageResource = getImageResourceForCategory(category.name)
                )
            )
        }
        return list
    }

    private fun simplifyCategoryName(originalName: String): String {
        val parts = originalName.split(":")
        if (parts.size > 1) {
            return parts[1].trim()
        }
        return originalName.trim()
    }

    private fun getImageResourceForCategory(categoryName: String): Int {
        return when (categoryName) {
            "General Knowledge" -> R.raw.ic_gk
            "Entertainment: Books" -> R.raw.ic_books
            "Entertainment: Film" -> R.raw.ic_movie
            "Entertainment: Music" -> R.raw.ic_music
            "Entertainment: Musicals & Theatres" -> R.raw.ic_music
            "Entertainment: Television" -> R.raw.ic_movie
            "Entertainment: Video Games" -> R.raw.ic_video_games
            "Entertainment: Board Games" -> R.raw.ic_board_games
            "Science & Nature" -> R.raw.ic_nature
            "Science: Computers" -> R.raw.ic_computers
            "Science: Mathematics" -> R.raw.ic_maths
            "Mythology" -> R.raw.ic_myths
            "Sports" -> R.raw.ic_sports
            "Geography" -> R.raw.ic_geography
            "History" -> R.raw.ic_history
            "Politics" -> R.raw.ic_politics
            "Art" -> R.raw.ic_art
            "Celebrities" -> R.raw.ic_celebrity
            "Animals" -> R.raw.ic_animal
            "Vehicles" -> R.raw.ic_gadget
            "Entertainment: Comics" -> R.raw.ic_comic
            "Science: Gadgets" -> R.raw.ic_gadget
            "Entertainment: Japanese Anime & Manga" -> R.raw.ic_anime
            "Entertainment: Cartoon & Animations" -> R.raw.ic_cartoon
            else -> R.raw.ic_gk
        }
    }

    suspend fun setQuizStatusList(list: List<QuizModel>) {
        dataStoreHelper.setQuizStatusList(list)
    }


    suspend fun getCategories() {
        when (val response = SafeApiCall.execute { quizApi.fetchCategories() }) {
            is Resource.Failure -> {}
            Resource.Loading -> {}
            is Resource.Success -> {
                dataStoreHelper.saveStringToDatastore(Constants.CATEGORY to Gson().toJson(response.value))
            }
        }
    }

    suspend fun getTriviaQuestionsList(
        categoryId: Int?,
        difficulty: String?
    ): Resource<QuestionAnswerListResponse> {


        return SafeApiCall.execute {
            quizApi.getTriviaQuestionsList(
                categoryId = categoryId,
                difficulty = difficulty
            )
        }
    }


}