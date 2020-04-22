package com.sma6871.fallingwords.game.factory

import com.sma6871.fallingwords.domain.repository.WordsRepository
import com.sma6871.fallingwords.game.model.Game
import com.sma6871.fallingwords.game.model.Word
import com.sma6871.fallingwords.utils.RxSchedulersOverrideRule
import io.mockk.MockKSettings
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class FallingWordGameFactoryImplUnitTests {

    @get:Rule
    val rule: TestRule = RxSchedulersOverrideRule()

    lateinit var repository: WordsRepository
    lateinit var factory: FallingWordGameFactory

    private val words = listOf(
        Word("building", "edificio"),
        Word("house", "casa"),
        Word("station", "estación"),
        Word("castle", "castillo")
    )

    @Before
    fun setUp() {
        MockKSettings.setRelaxed(true)
        repository = mockk()
        factory = FallingWordGameFactoryImpl(repository)
    }


    @Test
    fun `build game should get cocktails from repo`() {
        factory.buildGame()

        verify { repository.getWords() }
    }

    @Test
    fun `build game should call onError`() {
        setupRepositoryWithError(repository)

        val testObservable = factory.buildGame().test()


        testObservable.assertFailure(RuntimeException::class.java)
    }

    @Test
    fun `build game should not create game with empty question list`() {
        setupRepositoryWithCocktails(repository)

        val testObservable = factory.buildGame().test()


        testObservable.assertValue { game: Game ->
            !game.questions.isNullOrEmpty()
        }
    }

    private fun setupRepositoryWithCocktails(repository: WordsRepository) {
        every { repository.getWords() } returns Single.just(words)
    }

    private fun setupRepositoryWithError(repository: WordsRepository) {
        every { repository.getWords() } returns Single.error(RuntimeException())
    }

    @Test
    fun `build game should build game with all questions`() {
        setupRepositoryWithCocktails(repository)

        val testGameObservable = factory.buildGame().test()

        testGameObservable.assertValue { game: Game ->
            val englishList = words.map { it.english }
            game.questions.all { englishList.contains(it.english) }
        }
    }

}