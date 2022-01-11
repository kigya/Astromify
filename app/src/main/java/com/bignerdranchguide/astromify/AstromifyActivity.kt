package com.bignerdranchguide.astromify

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
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

private var currentIndex = 0
private var correctAmount = 0

private val observer = LifecycleEventObserver()

class MainActivity : AppCompatActivity() {
    @SuppressLint("ResourceType", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.astromify_main)
        lifecycle.addObserver(observer)

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
            currentIndex = (currentIndex + 1)
            if (currentIndex != 10) {
                updateQuestion()
                hideNextButton()
                showTrueButton()
                showFalseButton()
                hideAnswer()
                updateQuestionNumber()
                hideResultText()
                if (currentIndex > 2) {
                    if (currentIndex > 5) {
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
                questionBookNumber.text = "${(correctAmount * 10)}%"
                nextButton.setText(R.string.quit)
            }
        }

        updateQuestion()
        updateAnswer()
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
    }

    private fun updateAnswer() {
        val answerTextResId = answerBank[currentIndex].textResId
        answerTextView.setText(answerTextResId)
    }

    private fun updateQuestionNumber() {
        questionBookNumber.text = (currentIndex + 1).toString()
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        var messageResId by Delegates.notNull<Int>()
        if (userAnswer == correctAnswer) {
            messageResId = R.string.correct_toast
            correctAmount++
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