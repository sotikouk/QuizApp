package com.sotkou.quizapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sotkou.quizapp.model.QuestionsList
import com.sotkou.quizapp.retrofit.QuestionsAPI
import com.sotkou.quizapp.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class QuizRepository {
    var questionsAPI: QuestionsAPI

    init {
        questionsAPI = RetrofitInstance().getRetrofitInstance().create(QuestionsAPI::class.java)
    }
    fun getQuestionsFromAPI(): LiveData<QuestionsList> {
        val data = MutableLiveData<QuestionsList>()
        var questionsList : QuestionsList
        GlobalScope.launch(Dispatchers.IO) {
            val response = questionsAPI.getQuestions()
            if (response.isSuccessful) {
                questionsList = response.body()!!
                data.postValue(questionsList)
                Log.i("TAGY",""+data.value)
            }
        }
        return data
    }

}