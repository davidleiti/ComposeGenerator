package com.composegenerator.model

sealed class ViewType {
    object Text : ViewType()
    object Image : ViewType()
    object Button : ViewType()
    object Check : ViewType()
    object Progress : ViewType()
    object Switch : ViewType()

    sealed class Container : ViewType() {
        object Frame : Container()
        object Linear : Container()
        object Scroll : Container()
        object Card : Container()
    }

    companion object {
        fun createFromTag(tag: String): ViewType = when (tag) {
            "TextView" -> Text
            "ImageView" -> Image
            "Button" -> Button
            "CheckBox" -> Check
            "Switch" -> Switch
            "ProgressBar" -> Progress
            "FrameLayout" -> Container.Frame
            "LinearLayout" -> Container.Linear
            "ScrollView" -> Container.Scroll
            "CardView" -> Container.Card
            else -> throw UnsupportedViewException(tag)
        }
    }
}
