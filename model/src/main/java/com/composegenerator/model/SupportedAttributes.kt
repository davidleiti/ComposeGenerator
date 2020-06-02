package com.composegenerator.model

val ViewType.supportedAttributes: Set<AttributeType> get() = genericAttributes + specificAttributes

internal val ViewType.specificAttributes: Set<AttributeType>
    get() = when (this) {
        is ViewType.Text -> setOf(
            AttributeType.Text, AttributeType.TextSize, AttributeType.TextAlignment
        )
        is ViewType.Image -> setOf(
            AttributeType.Source
        )
        is ViewType.Button -> setOf(
            AttributeType.Text, AttributeType.TextSize, AttributeType.TextAlignment
        )
        is ViewType.Check -> setOf(
            AttributeType.Text, AttributeType.TextSize, AttributeType.Checked, AttributeType.TextAlignment
        )
        is ViewType.Switch -> setOf(
            AttributeType.Text, AttributeType.TextSize, AttributeType.Checked, AttributeType.TextAlignment
        )
        is ViewType.Progress -> setOf(
            AttributeType.IsIndeterminate
        )
        is ViewType.Container -> when (this) {
            ViewType.Container.Frame -> setOf()
            ViewType.Container.Linear -> setOf(
                AttributeType.Orientation
            )
            ViewType.Container.Card -> setOf()
            ViewType.Container.Table -> setOf()
            ViewType.Container.Scroll -> setOf()
        }
    }

internal val genericAttributes: Set<AttributeType>
    get() = setOf(
        AttributeType.ID,
        AttributeType.LayoutWidth,
        AttributeType.LayoutHeight,
        AttributeType.Visibility,
        AttributeType.Clickable,
        AttributeType.Enabled,
        AttributeType.Focusable,
        AttributeType.PaddingTop,
        AttributeType.PaddingEnd,
        AttributeType.PaddingStart,
        AttributeType.PaddingBottom
    )