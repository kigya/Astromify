package com.bignerdranchguide.astromify

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.bignerdranchguide.astromify.extensions.log
import com.bignerdranchguide.astromify.lifecycle.LifecycleEventObserver
import com.google.android.material.button.MaterialButton
import kotlin.properties.Delegates

private lateinit var trueButton: MaterialButton
private lateinit var falseButton: MaterialButton
private lateinit var nextButton: MaterialButton
private lateinit var questionTextView: AppCompatTextView
private lateinit var answerTextView: AppCompatTextView
private lateinit var questionBookNumber: AppCompatTextView
private lateinit var resultAccuracyText: AppCompatTextView
private lateinit var difficultyState: AppCompatImageView
private lateinit var difficultyText: AppCompatTextView
private lateinit var questionTitleText: AppCompatTextView

private const val KEY_INDEX = "index"

private val observer = LifecycleEventObserver()

class MainActivity : AppCompatActivity() {

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }

    @SuppressLint("ResourceType", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.astromify_main)
        lifecycle.addObserver(observer)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex

        trueButton = findViewById(R.id.button_true)
        falseButton = findViewById(R.id.button_false)
        nextButton = findViewById(R.id.button_next)
        questionTextView = findViewById(R.id.question_text)
        answerTextView = findViewById(R.id.answer_text)
        questionBookNumber = findViewById(R.id.question_book_number)
        resultAccuracyText = findViewById(R.id.answer_result_text)
        difficultyState = findViewById(R.id.difficulty_state)
        difficultyText = findViewById(R.id.difficulty_text)
        questionTitleText = findViewById(R.id.question_title_book)

        trueButton.setOnClickListener {
            updateAnswer()
            checkAnswer(true)
            hideTrueButton()
            hideFalseButton()
            showNextButton()
            showAnswer()
        }

        falseButton.setOnClickListener {
            updateAnswer()
            checkAnswer(false)
            hideFalseButton()
            hideTrueButton()
            showNextButton()
            showAnswer()
        }

        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            if (quizViewModel.currentIndex != 10) {
                updateQuestion()
                hideNextButton()
                showTrueButton()
                showFalseButton()
                hideAnswer()
                updateQuestionNumber()
                hideResultText()
                if (quizViewModel.currentIndex > 2) {
                    if (quizViewModel.currentIndex > 5) {
                        difficultyState.setImageResource(R.drawable.ic_difficultyhard)
                    } else {
                        difficultyState.setImageResource(R.drawable.ic_difficultymedium)
                    }
                }
            } else {
                hideAnswer()
                hideFalseButton()
                hideResultText()
                hideTrueButton()
                hideDifficultyText()
                hideDifficultyState()
                hideQuestionText()
                questionTitleText.setText(R.string.result)
                questionBookNumber.text = "${(quizViewModel.correctAmount * 10)}%"
                nextButton.setText(R.string.quit)
            }
        }

        updateQuestion()
        updateAnswer()
        updateQuestionNumber()
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        log(this, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    private fun updateAnswer() {
        val answerTextResId = quizViewModel.currentQuestionText
        answerTextView.setText(answerTextResId)
    }

    private fun updateQuestionNumber() {
        questionBookNumber.text = (quizViewModel.currentIndex + 1).toString()
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        var messageResId by Delegates.notNull<Int>()
        if (userAnswer == correctAnswer) {
            messageResId = R.string.correct_toast
            quizViewModel.correctAmount++
        } else {
            messageResId = R.string.incorrect_toast
        }
        resultAccuracyText.setText(messageResId)
        showResultText()
    }

    private fun hideFalseButton() {
        falseButton.isVisible = false
    }

    private fun hideTrueButton() {
        trueButton.isVisible = false
    }

    private fun hideNextButton() {
        nextButton.isVisible = false
    }

    private fun hideAnswer() {
        answerTextView.isVisible = false
    }

    private fun hideResultText() {
        resultAccuracyText.isVisible = false
    }

    private fun hideDifficultyText() {
        difficultyText.isVisible = false
    }

    private fun hideDifficultyState() {
        difficultyState.isVisible = false
    }

    private fun hideQuestionText() {
        questionTextView.isVisible = false
    }

    private fun showFalseButton() {
        falseButton.isVisible = true
    }

    private fun showTrueButton() {
        trueButton.isVisible = true
    }

    private fun showNextButton() {
        nextButton.isVisible = true
    }

    private fun showAnswer() {
        answerTextView.isVisible = true
    }

    private fun showResultText() {
        resultAccuracyText.isVisible = true
    }
}