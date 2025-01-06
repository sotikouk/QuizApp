package com.sotkou.quizapp.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sotkou.quizapp.R
import com.sotkou.quizapp.databinding.ActivityMainBinding
import com.sotkou.quizapp.model.Question
import com.sotkou.quizapp.viewmodel.QuizViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var quizViewModel: QuizViewModel
    lateinit var questionsList: List<Question>

    companion object {
        var score = 0
        var totalQuestions = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        score = 0
        totalQuestions = 0
        quizViewModel = ViewModelProvider(this)[QuizViewModel::class.java]

        GlobalScope.launch(Dispatchers.Main) {
            quizViewModel.getQuestionsFromLiveData().observe(this@MainActivity, Observer {
                if (it.size>0){
                    questionsList = it
                    Log.i("TAGY","This is the 1st question: ${questionsList[0]}")
                    binding.apply {
                        textQuestion.text = questionsList[0].question
                        radio1.text = questionsList[0].option1
                        radio2.text = questionsList[0].option2
                        radio3.text = questionsList[0].option3
                        radio4.text = questionsList[0].option4
                    }
                }
            })

        }

        var i = 1 // για την δευτερη ερώτηση
        binding.apply { // για να μην γραφουμε binding συνεχεια
            next.setOnClickListener(View.OnClickListener{
                val selectedOption = radioGroup?.checkedRadioButtonId
                if (selectedOption != -1) { // αν η επιλογή δεν είναι κενή
                    val radbutton = findViewById<View>(selectedOption!!) as RadioButton
                    questionsList.let { // τροποποιούμε ολες τις ερωτήσεις (iterate) στο σωστο form
                        if (i < it.size!!) {
                            // Παιρνουμε τον αριθμο των ερωτησεων
                            totalQuestions = it.size
                            // Ελεγχουμε αν η απαντησή είναι σωστή
                            if (radbutton.text.toString().equals( it[i-1].correct_option)) {
                                score++
                                result?.text = "Correct Answer : $score"
                            }
                            // εμφανίζουμε τις επόμενες ερωτήσεις
                            textQuestion.text = "Question ${i+1}: "+questionsList[i].question
                            radio1.text = it[i].option1
                            radio2.text = it[i].option2
                            radio3.text = it[i].option3
                            radio4.text = it[i].option4
                            // Ελέγχουμε αν ειναι η τελευταία ερώτηση
                            if (i == it.size-1) {
                                next.text = "Finish"
                            }
                            radioGroup?.clearCheck()
                            i++
                        } else {
                            if (radbutton.text.toString().equals(it[i-1].correct_option)) {
                                score++
                                result?.text = "Correct Answer : $score"
                            } else {
                            }

                            val intent = Intent(this@MainActivity, ResultActivity::class.java)
                            startActivity(intent)
                        }
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Please select One Option", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}