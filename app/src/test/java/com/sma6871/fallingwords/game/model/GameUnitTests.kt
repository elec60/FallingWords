package com.sma6871.fallingwords.game.model

import com.sma6871.fallingwords.game.enums.Answer
import com.sma6871.fallingwords.game.fake.QuestionFakeFactory
import io.mockk.MockKSettings
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class GameUnitTests {

    lateinit var game: Game

    @Before
    fun setUp() {
        MockKSettings.setRelaxed(true)
        game = Game()
    }

    @Test(expected = NoSuchElementException::class)
    fun `when getting next question on empty question list should throw exception`() {
        game.nextQuestion()
    }


    @Test
    fun `when getting next question on non empty question list should get first element`() {
        game = Game(questions = listOf(QuestionFakeFactory.getRandomQuestion()))
        val first = game.nextQuestion()
        assertNotNull(first)
    }

    @Test
    fun `when getting next question and there is no more question should return null`() {
        game = Game(questions = listOf(Question("FIRST_CORRECT", "FIRST_INCORRECT")))
        val first = game.nextQuestion()
        val mustBeNull = game.nextQuestion()
        assertNull(mustBeNull)
    }

    @Test
    fun `when answering should delegate to question`() {
        val question = mockk<Question>()
        val game = Game(questions = listOf(question))

        game.answer(question, Answer.NONE)

        verify { question.answer(Answer.NONE) }
    }

    @Test
    fun `when answering correctly should increment current score`() {
        val question = mockk<Question>()
        every { question.answer(any()) } returns true
        val score = mockk<Score>()
        val game = Game(listOf(question))

        game.answer(question, Answer.CORRECT)

        verify { score.increment() }
    }

    @Test
    fun `when answering incorrectly should not increment current score`() {
        val question = mockk<Question>()
        every { question.answer(any()) } returns false
        val score = mockk<Score>()
        val game = Game(listOf(question))

        game.answer(question, Answer.WRONG)

        verify(exactly = 0) { score.increment() }
    }


}