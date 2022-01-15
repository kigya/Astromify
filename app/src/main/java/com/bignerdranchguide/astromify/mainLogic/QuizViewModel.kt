package com.bignerdranchguide.astromify.mainLogic

import androidx.lifecycle.ViewModel
import com.bignerdranchguide.astromify.R
import com.bignerdranchguide.astromify.extensions.log

class QuizViewModel : ViewModel() {
    init {
        log(this, "ViewModel instance created")
    }

    override fun onCleared() {
        super.onCleared()
        log(this, "ViewModel instance about to be destroyed")
    }

    private val questionBank = listOf(
        Question(R.string.question_1, false),
        Question(R.string.question_2, false),
        Question(R.string.question_3, true),
        Question(R.string.question_4, true),
        Question(R.string.question_5, true),
        Question(R.string.question_6, false),
        Question(R.string.question_7, false),
        Question(R.string.question_8, false),
        Question(R.string.question_9, false),
        Question(R.string.question_10, false)
    )

    private val answerBank = listOf(
        Answer(R.string.answer_1),
        Answer(R.string.answer_2),
        Answer(R.string.answer_3),
        Answer(R.string.answer_4),
        Answer(R.string.answer_5),
        Answer(R.string.answer_6),
        Answer(R.string.answer_7),
        Answer(R.string.answer_8),
        Answer(R.string.answer_9),
        Answer(R.string.answer_10)
    )

    var currentIndex = 0
    var correctAmount = 0
    var isCheater = false

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    val currentAnswerText: Int
        get() = answerBank[currentIndex].textResId

    fun moveToNext() {
        currentIndex += 1
    }

}