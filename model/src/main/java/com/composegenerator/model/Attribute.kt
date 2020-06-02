package com.composegenerator.model

sealed class Attribute<out T : Any>(val value: T) {
    class ID(value: Int) : Attribute<Int>(value)
    class LayoutHeight(value: LayoutDimension) : Attribute<LayoutDimension>(value)
    class LayoutWidth(value: LayoutDimension) : Attribute<LayoutDimension>(value)
    class Text(value: String) : Attribute<String>(value)
    class TextSize(value: Int) : Attribute<Int>(value)
    class Source(value: Int) : Attribute<Int>(value)
    class Checked(value: Boolean) : Attribute<Boolean>(value)
    class Indeterminate(value: Boolean) : Attribute<Boolean>(value)
    class Visibility(value: ViewVisibility) : Attribute<ViewVisibility>(value)
    class Orientation(value: ViewOrientation) : Attribute<ViewOrientation>(value)
    class Enabled(value: Boolean) : Attribute<Boolean>(value)
    class TextAlignment(value: ViewTextAlignment): Attribute<ViewTextAlignment>(value)
    class Clickable(value: Boolean) : Attribute<Boolean>(value)
    class Hint(value: String) : Attribute<String>(value)
    class Focusable(value: Boolean) : Attribute<Boolean>(value)
    class PaddingTop(value: Int) : Attribute<Int>(value)
    class PaddingBottom(value: Int) : Attribute<Int>(value)
    class PaddingStart(value: Int) : Attribute<Int>(value)
    class PaddingEnd(value: Int) : Attribute<Int>(value)

    val string: String
        get() = when (this) {
            is ID -> "id: $value"
            is LayoutHeight -> "layout_height: ${value.string}"
            is LayoutWidth -> "layout_width: ${value.string}"
            is Text -> "text: $value"
            is TextSize -> "textSize: $value"
            is Source -> "src: $value"
            is Visibility -> "visibility: ${value.string}"
            is Orientation -> "orientation: ${value.string}"
            is Enabled -> "enabled: $value"
            is TextAlignment -> "textAlignment: ${value.string}"
            is Checked -> "checked: $value"
            is Clickable -> "clickable: $value"
            is Indeterminate -> "indeterminate: $value"
            is Hint -> "hint: $value"
            is Focusable -> "focusable: $value"
            is PaddingTop -> "paddingTop: $value"
            is PaddingStart -> "paddingStart: $value"
            is PaddingEnd -> "paddingEnd: $value"
            is PaddingBottom -> "paddingBottom: $value"
        }

    companion object {
        const val NAMESPACE_ANDROID = "http://schemas.android.com/apk/res/android"
    }
}
