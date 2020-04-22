package com.sma6871.fallingwords.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sma6871.fallingwords.game.enums.AnswerOption
import com.sma6871.fallingwords.game.factory.FallingWordGameFactory
import com.sma6871.fallingwords.game.model.Game
import com.sma6871.fallingwords.game.model.Question
import com.sma6871.fallingwords.game.model.Score
import com.sma6871.fallingwords.presentation.state.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.subjects.PublishSubject

class GameViewModel(
    private val factory: FallingWordGameFactory
) : ViewModel() {

    private val loadingLiveData = MutableLiveData(false)
    private val errorLiveData = MutableLiveData(false)
    private val scoreLiveData = MutableLiveData<Score>()
    private val gameStateSubject = PublishSubject.create<GameState>()

    fun getLoading(): LiveData<Boolean> = loadingLiveData
    fun getError(): LiveData<Boolean> = errorLiveData
    fun getScore(): LiveData<Score> = scoreLiveData
    fun getGameState(): Observable<GameState> = gameStateSubject

    private var game: Game? = null
    var questionCount = 0
    var question: Question? = null
        private set

    private val bag = CompositeDisposable()

    init {
        initGame()
    }

    private fun initGame() {
        loadingLiveData.value = true
        errorLiveData.value = false
        bag += factory.buildGame()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { newGame ->
                    loadingLiveData.value = false
                    errorLiveData.value = false
                    scoreLiveData.value = newGame.score
                    game = newGame
                    questionCount = newGame.questions.size
                    gameStateSubject.onNext(WelcomeState)
                }, {
                    loadingLiveData.value = false
                    errorLiveData.value = true
                }
            )

    }


    fun nextQuestion() {
        game?.let {
            question = it.nextQuestion()
            if (question != null) {
                gameStateSubject.onNext(QuestionState(question!!))
            } else {
                gameStateSubject.onNext(FinishState)
            }
        }
    }


    fun answerQuestion(answerOption: AnswerOption) {
        if (question == null) return
        game?.let {
            it.answer(question!!, answerOption)
            scoreLiveData.value = it.score
            gameStateSubject.onNext(LevelResultState)
        }
    }


    override fun onCleared() {
        super.onCleared()
        bag.clear()
    }

}