package com.sma6871.fallingwords.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sma6871.fallingwords.domain.repository.WordsRepository
import com.sma6871.fallingwords.domain.repository.WordsRepositoryImpl
import com.sma6871.fallingwords.game.factory.FallingWordGameFactory
import com.sma6871.fallingwords.game.factory.FallingWordGameFactoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

    single { GsonBuilder().create() }

    factory { WordsRepositoryImpl(androidContext(), get() as Gson) as WordsRepository }

    factory { FallingWordGameFactoryImpl(get() as WordsRepository) as FallingWordGameFactory }

}