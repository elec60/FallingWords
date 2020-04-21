package com.sma6871.fallingwords.domain.model

import com.sma6871.fallingwords.domain.enums.Answer

class Game(
    val questions: List<Question> = listOf(),
    val score: Score = Score()
) {

    private var questionIndex = 0

    fun nextQuestion(): Question? {
        if (questions.isEmpty())
            throw NoSuchElementException("Questions list is empty")
        if (questionIndex < questions.size) {
            return questions[questionIndex++]
        }
        return null
    }

    fun answer(question: Question, answer: Answer) {
        val result = question.answer(answer)
        if (result)
            score.increment()
    }
}