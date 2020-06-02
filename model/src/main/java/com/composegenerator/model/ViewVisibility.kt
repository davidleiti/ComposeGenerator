package com.composegenerator.model

sealed class ViewVisibility {
    object Visible : ViewVisibility()
    object Gone : ViewVisibility()
    object Invisible : ViewVisibility()

    val string: String
        get() = when (this) {
            is Visible -> "visible"
            is Gone -> "gone"
            is Invisible -> "invisible"
        }
}
