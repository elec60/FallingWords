package com.sma6871.fallingwords.game.model

import com.sma6871.fallingwords.game.enums.AnswerOption

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

    fun answer(question: Question, answerOption: AnswerOption) {
        val result = question.answer(answerOption)
        if (result)
            score.increment()
    }
}