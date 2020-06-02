package com.composegenerator.model

sealed class ViewOrientation {
    object Horizontal : ViewOrientation()
    object Vertical : ViewOrientation()

    val string: String
        get() = when (this) {
            is Horizontal -> "horizontal"
            is Vertical -> "vertical"
        }
}