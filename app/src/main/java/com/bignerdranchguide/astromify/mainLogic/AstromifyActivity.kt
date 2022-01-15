package com.bignerdranchguide.astromify.mainLogic

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.bignerdranchguide.astromify.R
import com.bignerdranchguide.astromify.cheating.CheatActivity
import com.bignerdranchguide.astromify.cheating.EXTRA_ANSWER_SHOWN
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
private lateinit var cheatButton: AppCompatImageButton

private const val KEY_INDEX = "index"
private const val REQUEST_CODE_CHEAT = 0

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
        cheatButton = findViewById(R.id.cheat_button)

        trueButton.setOnClickListener {
            updateAnswer()
            checkAnswer(true)
            hideTrueButton()
            hideFalseButton()
            showNextButton()
            hideCheatButton()
            showAnswer()
        }

        falseButton.setOnClickListener {
            updateAnswer()
            checkAnswer(false)
            hideFalseButton()
            hideTrueButton()
            hideCheatButton()
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
                showCheatButton()
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
                if (!quizViewModel.isCheater) {
                    questionBookNumber.text = "${(quizViewModel.correctAmount * 10)}%"
                } else {
                    questionBookNumber.text
                }
                nextButton.setText(R.string.quit)
            }
        }

        cheatButton.setOnClickListener {
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val answerText = quizViewModel.currentAnswerText
            val questionNumber = quizViewModel.currentIndex + 1
            val intent = CheatActivity.newIntent(
                this@MainActivity,
                answerIsTrue, answerText, questionNumber
            )
            startActivityForResult(intent, REQUEST_CODE_CHEAT)
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

    override fun onActivityResult(requestCode: Int,
                                  resultCode: Int,
                                  data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            quizViewModel.isCheater =
                data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    private fun updateAnswer() {
        val answerTextResId = quizViewModel.currentAnswerText
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

        if(quizViewModel.isCheater) {
            Toast.makeText(this, "Cheater!", Toast.LENGTH_SHORT)
                .show()
        }
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

    private fun hideCheatButton() {
        cheatButton.isVisible = false
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

    private fun showCheatButton() {
        cheatButton.isVisible = true
    }
}