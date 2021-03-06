package com.david.composegenerator.data

import android.content.Context

internal fun Context.getDimensionInDp(resourceId: Int): Int {
    val dimensionInPixels = resources.getDimension(resourceId)
    val dimensionInDp = dimensionInPixels / resources.displayMetrics.density
    return dimensionInDp.toInt()
}