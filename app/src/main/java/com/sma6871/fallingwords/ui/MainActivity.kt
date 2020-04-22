package com.sma6871.fallingwords.ui

import android.animation.ValueAnimator
import android.graphics.Shader
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.sma6871.fallingwords.R
import com.sma6871.fallingwords.game.enums.AnswerOption
import com.sma6871.fallingwords.game.model.Question
import com.sma6871.fallingwords.presentation.state.FinishState
import com.sma6871.fallingwords.presentation.state.LevelResultState
import com.sma6871.fallingwords.presentation.state.QuestionState
import com.sma6871.fallingwords.presentation.state.WelcomeState
import com.sma6871.fallingwords.presentation.viewmodel.GameViewModel
import com.sma6871.fallingwords.ui.dialog.FinishDialogFragment
import com.sma6871.fallingwords.ui.dialog.ResultDialogFragment
import com.sma6871.fallingwords.ui.dialog.WelcomeDialogFragment
import com.sma6871.fallingwords.utils.TileDrawable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: GameViewModel by viewModel()
    private val bag = CompositeDisposable()

    private var animation: ValueAnimator? = null

    companion object {
        const val LevelDuration = 5000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        setBackgroundPattern()
        observeState()
        observeLiveData()
        setListeners()
    }

    private fun setBackgroundPattern() {
        ContextCompat.getDrawable(this, R.drawable.ic_zig_zag)?.let { pattern ->
            imgBackground.setImageDrawable(TileDrawable(pattern, Shader.TileMode.REPEAT))
        }
    }

    private fun setListeners() {
        btnWrong.setOnClickListener {
            viewModel.answerQuestion(AnswerOption.WRONG)
        }
        btnCorrect.setOnClickListener {
            viewModel.answerQuestion(AnswerOption.CORRECT)
        }
    }

    private fun observeLiveData() {
        viewModel.getScore().observe(this, Observer {
            txtScore.text = getString(R.string.score).format(it.currentScore)
        })
        viewModel.getError().observe(this, Observer {
            if (it) {
                showError(getString(R.string.error_message))
            }
        })
    }

    private fun observeState() {
        bag += viewModel.getGameState().subscribe(
            { state ->
                when (state) {
                    WelcomeState -> showWelcomeScreen()
                    is QuestionState -> showQuestion(state.question)
                    LevelResultState -> showLevelResult()
                    FinishState -> showFinalScreen()
                }
            },
            {
                it.printStackTrace()
                showError(it.message)
            }
        )
    }

    private fun showError(message: String?) {
        message?.let {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }

    private fun showWelcomeScreen() {
        WelcomeDialogFragment().show(supportFragmentManager, "WELCOME_FRAGMENT")
    }

    private fun showLevelResult() {
        animation?.removeAllUpdateListeners()
        animation?.removeAllListeners()
        ResultDialogFragment().show(supportFragmentManager, "RESULT_DIALOG")
    }

    private fun showQuestion(question: Question) {
        txtWord.text = question.english
        txtTranslation.text = question.spanish


        animation = ValueAnimator.ofFloat(
            0F,
            (floor.top - txtTranslation.height - txtTranslation.top).toFloat()
        )

        animation?.apply {
            addUpdateListener { valueAnimator ->
                val translation = valueAnimator.animatedValue as Float
                txtTranslation.translationY = translation
            }
            doOnStart {
                txtTranslation.translationY = 0f
            }
            doOnEnd {
                viewModel.answerQuestion(AnswerOption.NONE)
            }
            duration = LevelDuration
            start()
        }


    }

    private fun showFinalScreen() {
        val dialog = FinishDialogFragment()
        dialog.isCancelable = false
        dialog.show(supportFragmentManager, "FINISH_FRAGMENT")
    }


    override fun onDestroy() {
        super.onDestroy()
        bag.clear()
    }

}