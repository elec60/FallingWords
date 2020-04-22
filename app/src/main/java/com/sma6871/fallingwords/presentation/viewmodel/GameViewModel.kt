package com.sma6871.fallingwords.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sma6871.fallingwords.game.factory.FallingWordGameFactory
import com.sma6871.fallingwords.game.model.Game
import com.sma6871.fallingwords.game.model.Question
import com.sma6871.fallingwords.game.model.Score
import com.sma6871.fallingwords.presentation.viewstate.GameViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

class GameViewModel(
    val factory: FallingWordGameFactory,
    val gameViewState: GameViewState
) : ViewModel() {

    private val loadingLiveData = MutableLiveData<Boolean>()
    private val errorLiveData = MutableLiveData<Boolean>()
    private val scoreLiveData = MutableLiveData<Score>()
    private val questionLiveData = MutableLiveData<Question>()

    fun getLoading(): LiveData<Boolean> = loadingLiveData
    fun getError(): LiveData<Boolean> = errorLiveData
    fun getScore(): LiveData<Score> = scoreLiveData
    fun getQuestion(): LiveData<Question> = questionLiveData

    private var game: Game? = null

    private val bag = CompositeDisposable()

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
                    //TODO: nextQuestion()
                }, {
                    loadingLiveData.value = false
                    errorLiveData.value = true
                }
            )

    }


    override fun onCleared() {
        super.onCleared()
        bag.clear()
    }

}