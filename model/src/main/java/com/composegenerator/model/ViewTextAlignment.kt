package com.composegenerator.model

sealed class ViewTextAlignment {
    object Start : ViewTextAlignment()
    object End : ViewTextAlignment()
    object Center : ViewTextAlignment()

    val string: String
        get() = when (this) {
            is Start -> "start"
            is End -> "end"
            is Center -> "center"
        }
}