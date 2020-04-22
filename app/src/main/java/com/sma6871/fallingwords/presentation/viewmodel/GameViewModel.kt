package com.sma6871.fallingwords.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sma6871.fallingwords.game.factory.FallingWordGameFactory
import com.sma6871.fallingwords.game.model.Game
import com.sma6871.fallingwords.game.model.Question
import com.sma6871.fallingwords.game.model.Score
import com.sma6871.fallingwords.presentation.state.FinishState
import com.sma6871.fallingwords.presentation.state.GameState
import com.sma6871.fallingwords.presentation.state.QuestionState
import com.sma6871.fallingwords.presentation.state.WelcomeState
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.subjects.PublishSubject

class GameViewModel(
    private val factory: FallingWordGameFactory
) : ViewModel() {

    private val loadingLiveData = MutableLiveData<Boolean>()
    private val errorLiveData = MutableLiveData<Boolean>()
    private val scoreLiveData = MutableLiveData<Score>()
    private val gameStateSubject = PublishSubject.create<GameState>()

    fun getLoading(): LiveData<Boolean> = loadingLiveData
    fun getError(): LiveData<Boolean> = errorLiveData
    fun getScore(): LiveData<Score> = scoreLiveData
    fun getGameState(): Observable<GameState> = gameStateSubject

    private var game: Game? = null
    private var question: Question? = null


    private val bag = CompositeDisposable()

    init {
        gameStateSubject.onNext(WelcomeState)
    }

    fun initGame() {
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
                    nextQuestion()
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


    override fun onCleared() {
        super.onCleared()
        bag.clear()
    }

}