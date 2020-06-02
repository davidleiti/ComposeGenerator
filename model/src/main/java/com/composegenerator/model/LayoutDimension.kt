package com.composegenerator.model

sealed class LayoutDimension {
    class FixedSize(val size: Int) : LayoutDimension()
    object MatchParent : LayoutDimension()
    object WrapContent : LayoutDimension()
    object MatchConstraint : LayoutDimension()

    val string: String
        get() = when (this) {
            is FixedSize -> "$size"
            is MatchParent -> "match_parent"
            is WrapContent -> "wrap_content"
            is MatchConstraint -> "0dp"
        }
}
