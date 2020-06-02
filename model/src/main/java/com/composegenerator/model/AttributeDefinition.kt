package com.composegenerator.model

val AttributeType.nameSpace: String
    get() = when (this) {
        AttributeType.ID,
        AttributeType.LayoutWidth,
        AttributeType.LayoutHeight,
        AttributeType.Text,
        AttributeType.TextSize,
        AttributeType.Source,
        AttributeType.Visibility,
        AttributeType.Orientation,
        AttributeType.Clickable,
        AttributeType.Enabled,
        AttributeType.TextAlignment,
        AttributeType.Focusable,
        AttributeType.IsIndeterminate,
        AttributeType.Checked,
        AttributeType.Hint,
        AttributeType.PaddingTop,
        AttributeType.PaddingBottom,
        AttributeType.PaddingStart,
        AttributeType.PaddingEnd -> Attribute.NAMESPACE_ANDROID
    }

val AttributeType.tag: String
    get() = when (this) {
        AttributeType.ID -> "id"
        AttributeType.LayoutWidth -> "layout_width"
        AttributeType.LayoutHeight -> "layout_height"
        AttributeType.Text -> "text"
        AttributeType.TextSize -> "textSize"
        AttributeType.Visibility -> "visibility"
        AttributeType.Orientation -> "orientation"
        AttributeType.Clickable -> "clickable"
        AttributeType.Checked -> "checked"
        AttributeType.TextAlignment -> "textAlignment"
        AttributeType.IsIndeterminate -> "indeterminate"
        AttributeType.Enabled -> "enabled"
        AttributeType.Focusable -> "focusable"
        AttributeType.Hint -> "hint"
        AttributeType.PaddingTop -> "paddingTop"
        AttributeType.PaddingBottom -> "paddingBottom"
        AttributeType.PaddingStart -> "paddingStart"
        AttributeType.PaddingEnd -> "paddingEnd"
        AttributeType.Source -> "src"
    }

val AttributeType.valueType: ValueType
    get() = when (this) {
        AttributeType.ID,
        AttributeType.LayoutHeight,
        AttributeType.LayoutWidth,
        AttributeType.TextSize,
        AttributeType.Source,
        AttributeType.Orientation,
        AttributeType.PaddingTop,
        AttributeType.PaddingBottom,
        AttributeType.PaddingStart,
        AttributeType.PaddingEnd,
        AttributeType.TextAlignment,
        AttributeType.Visibility -> ValueType.Int
        AttributeType.Enabled,
        AttributeType.Clickable,
        AttributeType.Checked,
        AttributeType.IsIndeterminate,
        AttributeType.Focusable -> ValueType.Boolean
        AttributeType.Text,
        AttributeType.Hint -> ValueType.String
    }
