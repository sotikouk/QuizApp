package com.sotkou.quizapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sotkou.quizapp.model.QuestionsList
import com.sotkou.quizapp.repository.QuizRepository

class QuizViewModel: ViewModel() {
    var repository: QuizRepository = QuizRepository()

    lateinit var questionsLiveData: LiveData<QuestionsList>

    init {
        questionsLiveData = repository.getQuestionsFromAPI()
    }

    fun getQuestionsFromLiveData(): LiveData<QuestionsList> {
        return questionsLiveData
    }

}