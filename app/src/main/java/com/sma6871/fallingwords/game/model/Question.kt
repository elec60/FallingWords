package com.sma6871.fallingwords.game.model

import com.sma6871.fallingwords.game.enums.Answer

class Question(
    val english: String,
    val spanish: String,
    val isCorrect: Boolean = true
) {
    var answeredOption: Answer? = null
        private set

    val isAnsweredCorrectly: Boolean?
        get() = when (answeredOption) {
            Answer.NONE -> false
            Answer.CORRECT -> isCorrect
            Answer.WRONG -> !isCorrect
            null -> null
        }


    fun answer(answer: Answer): Boolean {
        answeredOption = answer

        return isAnsweredCorrectly!!
    }

}
