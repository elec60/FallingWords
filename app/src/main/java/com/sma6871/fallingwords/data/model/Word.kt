package com.sma6871.fallingwords.data.model

import com.google.gson.annotations.SerializedName

data class Word(
    @SerializedName("text_eng")
    val english: String,
    @SerializedName("text_spa")
    val spanish: String
)

