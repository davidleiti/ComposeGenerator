package com.composegenerator.model

sealed class AttributeType {
    object ID : AttributeType()
    object LayoutHeight : AttributeType()
    object LayoutWidth : AttributeType()
    object Text : AttributeType()
    object TextSize : AttributeType()
    object Source : AttributeType()
    object Checked : AttributeType()
    object IsIndeterminate : AttributeType()
    object Visibility : AttributeType()
    object TextAlignment : AttributeType()
    object Orientation : AttributeType()
    object Enabled : AttributeType()
    object Clickable : AttributeType()
    object Hint : AttributeType()
    object Focusable : AttributeType()
    object PaddingTop : AttributeType()
    object PaddingBottom : AttributeType()
    object PaddingStart : AttributeType()
    object PaddingEnd : AttributeType()
}
