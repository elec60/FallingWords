package com.sma6871.fallingwords.game.factory

import com.sma6871.fallingwords.game.model.Game
import io.reactivex.Single

interface FallingWordGameFactory {
    fun buildGame(): Single<Game>

}