package com.sotkou.quizapp.retrofit

import com.sotkou.quizapp.model.QuestionsList
import retrofit2.Response
import retrofit2.http.GET

interface QuestionsAPI {

    @GET("/api/quiz")
    suspend fun getQuestions(): Response<QuestionsList>
}