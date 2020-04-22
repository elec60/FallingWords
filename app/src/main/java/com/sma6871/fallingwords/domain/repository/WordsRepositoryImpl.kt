package com.sma6871.fallingwords.domain.repository

import android.content.Context
import android.content.res.Resources
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import com.sma6871.fallingwords.R
import com.sma6871.fallingwords.game.model.Word
import com.sma6871.fallingwords.utils.fromJson
import io.reactivex.Single

class WordsRepositoryImpl(
    private val context: Context,
    private val gson: Gson
) : WordsRepository {

    override fun getWords(): Single<List<Word>> {

        return Single.create { emitter ->
            try {
                val wordsString: String =
                    context.resources.openRawResource(R.raw.words)
                        .bufferedReader()
                        .use { it.readText() }
                val wordsList: List<Word> = gson.fromJson(wordsString)
                emitter.onSuccess(wordsList)

            } catch (exception: Resources.NotFoundException) {
                emitter.onError(exception)
            } catch (exception: JsonParseException) {
                emitter.onError(exception)
            } catch (exception: JsonSyntaxException) {
                emitter.onError(exception)
            }
        }
    }


}