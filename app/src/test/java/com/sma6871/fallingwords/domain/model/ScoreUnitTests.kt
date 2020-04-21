package com.sma6871.fallingwords.domain.model

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ScoreUnitTests {
    lateinit var score: Score

    @Before
    fun setup() {
        score = Score()
    }

    @Test
    fun `when increment score should increment current score`() {
        score.increment()

        assertEquals(1, score.currentScore)
    }

}