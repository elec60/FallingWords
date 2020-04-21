package com.sma6871.fallingwords.domain.fake

import com.sma6871.fallingwords.domain.model.Question
import java.util.*
import kotlin.random.Random

object QuestionFakeFactory {

    private fun makeRandomString() = UUID.randomUUID().toString()
    private fun makeRandomBoolean() = Random.nextBoolean()

    fun getRandomQuestion() = Question(makeRandomString(), makeRandomString(), makeRandomBoolean())
    fun getCorrectQuestion() = Question(makeRandomString(), makeRandomString(), true)
    fun getWrongQuestion() = Question(makeRandomString(), makeRandomString(), false)
}