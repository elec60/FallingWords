package com.sma6871.fallingwords.domain.model

import com.sma6871.fallingwords.domain.enums.Answer

class Question(
    val english: String,
    val spanish: String,
    val isCorrect: Boolean = true
) {
    fun answer(answer: Answer):Boolean {
        return true
    }

}
