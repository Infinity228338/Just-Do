package com.domore.justdo.ui.base

import android.content.Context
import android.os.Build

fun getResColor(context: Context, colorRes: Int) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        context.getColor(colorRes);
    } else
        context.resources.getColor(colorRes)