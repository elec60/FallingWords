package com.sma6871.fallingwords.di

import com.sma6871.fallingwords.game.factory.FallingWordGameFactory
import com.sma6871.fallingwords.presentation.viewmodel.GameViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { GameViewModel(get() as FallingWordGameFactory) }
}