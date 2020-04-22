package com.sma6871.fallingwords.utils

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun Context.getColorX(@ColorRes colorId: Int): Int {
    return ContextCompat.getColor(this, colorId)
}