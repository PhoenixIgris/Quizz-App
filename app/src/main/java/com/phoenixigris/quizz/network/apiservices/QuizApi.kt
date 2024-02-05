package com.phoenixigris.quizz.network.apiservices

import com.phoenixigris.quizz.network.reponse.QuestionAnswerListResponse
import com.phoenixigris.quizz.network.reponse.QuestionResponse
import com.phoenixigris.quizz.network.reponse.QuizCategoryModel
import com.phoenixigris.quizz.utils.Constants
import retrofit2.Response
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface QuizApi {

    @GET(ApiEndPoints.API_URL)
    suspend fun fetchRandomQuestion(
        @Query("apiKey") apiKey: String = Constants.AUTH_TOKEN
    ): Response<QuestionResponse>

    @GET(ApiEndPoints.API_URL)
    suspend fun fetchLinuxQuestion(
        @Query("apiKey") apikey: String = Constants.AUTH_TOKEN,
        @Query("category") category: String = Constants.LINUX,
        @Query("difficulty") difficulty: String = Constants.DIFFICULTY_EASY
    ): Response<QuestionResponse>

    @GET(ApiEndPoints.API_URL)
    suspend fun fetchDevOpsQuestion(
        @Query("apiKey") apikey: String = Constants.AUTH_TOKEN,
        @Query("category") category: String = Constants.DEVOPS,
        @Query("difficulty") difficulty: String = Constants.DIFFICULTY_EASY
    ): Response<QuestionResponse>

    @GET("/api/v1/questions")
    suspend fun fetchDockerQuestion(
        @Query("apiKey") apikey: String = Constants.AUTH_TOKEN,
        @Query("category") category: String = Constants.DOCKER,
        @Query("difficulty") difficulty: String = Constants.DIFFICULTY_EASY
    ): Response<QuestionResponse>

    @GET(ApiEndPoints.API_URL)
    suspend fun fetchSQLQuestion(
        @Query("apiKey") apikey: String = Constants.AUTH_TOKEN,
        @Query("category") category: String = Constants.SQL,
        @Query("difficulty") difficulty: String = Constants.DIFFICULTY_EASY
    ): Response<QuestionResponse>

    @GET(ApiEndPoints.API_URL)
    suspend fun fetchCodeQuestion(
        @Query("apiKey") apikey: String = Constants.AUTH_TOKEN,
        @Query("category") category: String = Constants.CODE,
        @Query("difficulty") difficulty: String = Constants.DIFFICULTY_EASY
    ): Response<QuestionResponse>

    @GET(ApiEndPoints.API_URL)
    suspend fun fetchCMSQuestion(
        @Query("apiKey") apikey: String = Constants.AUTH_TOKEN,
        @Query("category") category: String = Constants.CMS,
        @Query("difficulty") difficulty: String = Constants.DIFFICULTY_EASY
    ): Response<QuestionResponse>


    @GET(ApiEndPoints.CATEGORIES)
    suspend fun fetchCategories(): Response<QuizCategoryModel>

    @GET(ApiEndPoints.MAIN_URL)
    suspend fun getTriviaQuestionsList(
        @Query("amount") amount: Int? = 10,
        @Query("category") categoryId: Int?,
        @Query("difficulty") difficulty: String?,
        @Query("type") type: String? = "multiple"
    ): Response<QuestionAnswerListResponse>


    @POST("api/login")
    suspend fun login() : Response<Any>

}