package com.sma6871.fallingwords.presentation.state

import com.sma6871.fallingwords.game.model.Question

sealed class GameState

object WelcomeState : GameState()
data class QuestionState(val question: Question) : GameState()
object LevelResultState : GameState()
object FinishState : GameState()

