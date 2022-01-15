package com.bignerdranchguide.astromify.cheating

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import com.bignerdranchguide.astromify.R
import java.util.*

private const val EXTRA_ANSWER_IS_TRUE =
    "com.bignerdranchguide.astromify.answer_is_true"

private const val EXTRA_ANSWER_TEXT =
    "com.bignerdranchguide.astromify.answer_text"

private const val EXTRA_QUESTION_NUMBER =
    "com.bignerdranchguide.astromify.question_number"

const val EXTRA_ANSWER_SHOWN =
    "com.bignerdranchguide.astromify.answer_shown"

class CheatActivity : AppCompatActivity() {

    private lateinit var answerTextView: AppCompatTextView
    private lateinit var isCorrectAnswerTextView: AppCompatTextView
    private lateinit var questionBookNumberTextView: AppCompatTextView
    private lateinit var backButton: AppCompatButton

    private var answerIsTrue = false
    private var answerTextId: Int = 0
    private var questionNumber: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)
        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        answerTextId = intent.getIntExtra(EXTRA_ANSWER_TEXT, 0)
        questionNumber = intent.getIntExtra(EXTRA_QUESTION_NUMBER, 0)

        answerTextView = findViewById(R.id.answer_text)
        isCorrectAnswerTextView = findViewById(R.id.answer_result_text)
        questionBookNumberTextView = findViewById(R.id.question_book_number)
        backButton = findViewById(R.id.button_back)

        backButton.setOnClickListener {
            finish()
        }
        setAnswerShownResult(true)

        updateIsCorrectAnswerText()
        updateAnswerText()
        updateQuestionNumber()
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }

    companion object {
        fun newIntent(
            packageContext: Context,
            answerIsTrue: Boolean,
            answerTextId: Int,
            questionNumber: Int
        ): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
                putExtra(EXTRA_ANSWER_TEXT, answerTextId)
                putExtra(EXTRA_QUESTION_NUMBER, questionNumber)
            }
        }
    }

    private fun updateIsCorrectAnswerText() {
        isCorrectAnswerTextView.text = answerIsTrue.toString().replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        }.plus("!")
    }

    private fun updateAnswerText() {
        answerTextView.setText(answerTextId)
    }

    private fun updateQuestionNumber() {
        questionBookNumberTextView.text = questionNumber.toString()
    }

}