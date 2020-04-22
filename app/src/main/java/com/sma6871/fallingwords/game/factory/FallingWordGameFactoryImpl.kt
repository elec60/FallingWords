package com.sma6871.fallingwords.game.factory

import com.sma6871.fallingwords.domain.WordsRepository
import com.sma6871.fallingwords.game.model.Game
import com.sma6871.fallingwords.game.model.Question
import com.sma6871.fallingwords.game.model.Word
import io.reactivex.Single
import kotlin.random.Random

class FallingWordGameFactoryImpl(private val repository: WordsRepository) : FallingWordGameFactory {
    override fun buildGame(): Single<Game> {
        return repository.getWords().map { words: List<Word> ->
            val questions = buildQuestions(words)
            Game(questions)
        }
    }

    private fun buildQuestions(words: List<Word>): List<Question> {
        return words.map { word ->
            val translationShouldBeCorrect = Random.nextBoolean()

            val translation =
                if (translationShouldBeCorrect) word.spanish else words.random().spanish

            Question(
                word.english,
                translation,
                word.spanish == translation
            )
        }
    }


}