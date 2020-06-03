package com.composegenerator.model

fun AttributeType.create(value: Int): Attribute<Any> =
    when (this) {
        AttributeType.ID -> Attribute.ID(value)
        AttributeType.Source -> Attribute.Source(value)
        AttributeType.TextSize -> Attribute.TextSize(value)
        AttributeType.PaddingTop -> Attribute.PaddingTop(value)
        AttributeType.PaddingBottom -> Attribute.PaddingBottom(value)
        AttributeType.PaddingStart -> Attribute.PaddingStart(value)
        AttributeType.PaddingEnd -> Attribute.PaddingEnd(value)
        else -> throw InvalidAttributeValue(toString(), value.toString())
    }

fun AttributeType.create(value: Boolean): Attribute<Any> =
    when (this) {
        AttributeType.Enabled -> Attribute.Enabled(value)
        AttributeType.Focusable -> Attribute.Focusable(value)
        AttributeType.Clickable -> Attribute.Clickable(value)
        AttributeType.Checked -> Attribute.Checked(value)
        AttributeType.IsIndeterminate -> Attribute.Indeterminate(value)
        else -> throw InvalidAttributeValue(toString(), value.toString())
    }

fun AttributeType.create(value: String): Attribute<Any> =
    when (this) {
        AttributeType.Text -> Attribute.Text(value)
        AttributeType.Hint -> Attribute.Hint(value)
        else -> throw InvalidAttributeValue(toString(), value)
    }

fun AttributeType.create(dimension: LayoutDimension): Attribute<Any> =
    when (this) {
        AttributeType.LayoutWidth -> Attribute.LayoutWidth(dimension)
        AttributeType.LayoutHeight -> Attribute.LayoutHeight(dimension)
        else -> throw InvalidAttributeValue(toString(), dimension.toString())
    }

fun AttributeType.create(textAlignment: ViewTextAlignment): Attribute<ViewTextAlignment> =
    when (this) {
        AttributeType.TextAlignment -> Attribute.TextAlignment(textAlignment)
        else -> throw InvalidAttributeValue(toString(), textAlignment.toString())
    }

fun AttributeType.create(visibility: ViewVisibility): Attribute<ViewVisibility> =
    when (this) {
        AttributeType.Visibility -> Attribute.Visibility(visibility)
        else -> throw InvalidAttributeValue(toString(), visibility.toString())
    }

fun AttributeType.create(orientation: ViewOrientation): Attribute<ViewOrientation> =
    when (this) {
        AttributeType.Orientation -> Attribute.Orientation(orientation)
        else -> throw InvalidAttributeValue(toString(), orientation.toString())
    }