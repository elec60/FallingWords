package com.sma6871.fallingwords.game.model

import com.sma6871.fallingwords.game.enums.AnswerOption

class Question(
    val english: String,
    val spanish: String,
    val isCorrect: Boolean = true
) {
    var answeredOption: AnswerOption? = null
        private set

    val isAnsweredCorrectly: Boolean?
        get() = when (answeredOption) {
            AnswerOption.NONE -> false
            AnswerOption.CORRECT -> isCorrect
            AnswerOption.WRONG -> !isCorrect
            null -> null
        }


    fun answer(answerOption: AnswerOption): Boolean {
        answeredOption = answerOption

        return isAnsweredCorrectly!!
    }

}
