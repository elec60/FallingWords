package com.sma6871.fallingwords.domain

import com.sma6871.fallingwords.game.model.Word
import io.reactivex.Single

interface WordsRepository {
    fun getWords(): Single<List<Word>>
}