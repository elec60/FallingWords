package com.sma6871.fallingwords.domain.model

class Score {
    var currentScore = 0
        private set

    fun increment() {
        currentScore++
    }
}