@file:JvmName("DemoUtils")

package com.example.sagar.physicsanimation

import android.content.Context
import android.util.DisplayMetrics

/**
 * Created by sagar on 07/08/17.
 */

fun dpToPx(dp: Int, context: Context) : Int {
    val displayMetrics = context.resources.displayMetrics
    return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
}
