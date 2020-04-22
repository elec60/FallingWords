package com.sma6871.fallingwords.game.model

import com.sma6871.fallingwords.game.enums.Answer
import com.sma6871.fallingwords.game.fake.QuestionFakeFactory
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class QuestionUnitTests {

    lateinit var question: Question

    @Before
    fun setUp() {
        question = QuestionFakeFactory.getRandomQuestion()
    }

    @Test
    fun `when creating question should not have answered option`() {
        assertNull(question.answeredOption)
    }

    @Test
    fun `when answering should have answered option`() {
        question.answer(Answer.CORRECT)
        assertEquals(Answer.CORRECT, question.answeredOption)
    }

    @Test
    fun `when answering with correct option should return True`() {
        val result = question.answer(if(question.isCorrect) Answer.CORRECT else Answer.WRONG)
        assertTrue(result)
    }

    @Test
    fun `when answering with wrong option should return False`() {
        val result = question.answer(if(!question.isCorrect) Answer.CORRECT else Answer.WRONG)
        assertFalse(result)
    }

    @Test
    fun `when not answeringshould return False`() {
        val result = question.answer(Answer.NONE)
        assertFalse(result)
    }


}