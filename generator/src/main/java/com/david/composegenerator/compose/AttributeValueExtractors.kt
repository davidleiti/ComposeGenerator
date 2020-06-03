package com.david.composegenerator.compose

import com.composegenerator.model.Attribute
import com.composegenerator.model.LayoutDimension
import com.composegenerator.model.View
import com.composegenerator.model.ViewOrientation
import com.composegenerator.model.ViewTextAlignment
import com.composegenerator.model.ViewVisibility
import com.david.composegenerator.R

internal const val DEFAULT_TEXT_SIZE = 12
internal const val PADDING_NONE = 0

internal val View.text: String get() = getAttributeValue<String, Attribute.Text>().orEmpty()
internal val View.textSize: Int get() = getAttributeValue<Int, Attribute.TextSize>() ?: DEFAULT_TEXT_SIZE
internal val View.Progress.isIndeterminate: Boolean get() = getAttributeValue<Boolean, Attribute.Indeterminate>() ?: false
internal val View.Image.source: Int get() = getAttributeValue<Int, Attribute.Source>() ?: R.drawable.ic_baseline_error_24
internal val View.textAlignment: ViewTextAlignment get() = getAttributeValue<ViewTextAlignment, Attribute.TextAlignment>() ?: ViewTextAlignment.Start
internal val View.Container.Linear.orientation: ViewOrientation get() = getAttributeValue<ViewOrientation, Attribute.Orientation>() ?: ViewOrientation.Vertical
internal val View.visible: Boolean get() = getAttributeValue<ViewVisibility, Attribute.Visibility>()?.let { it is ViewVisibility.Visible } ?: true
internal val View.layoutHeight: LayoutDimension get() = getAttributeValue<LayoutDimension, Attribute.LayoutHeight>() ?: LayoutDimension.WrapContent
internal val View.layoutWidth: LayoutDimension get() = getAttributeValue<LayoutDimension, Attribute.LayoutWidth>() ?: LayoutDimension.WrapContent
internal val View.paddingTop: Int get() = getAttributeValue<Int, Attribute.PaddingTop>() ?: PADDING_NONE
internal val View.paddingBottom: Int get() = getAttributeValue<Int, Attribute.PaddingBottom>() ?: PADDING_NONE
internal val View.paddingStart: Int get() = getAttributeValue<Int, Attribute.PaddingStart>() ?: PADDING_NONE
internal val View.paddingEnd: Int get() = getAttributeValue<Int, Attribute.PaddingEnd>() ?: PADDING_NONE

internal inline fun <S, reified T : Attribute<S>> View.getAttributeValue(): S? =
    (attributes.firstOrNull { it is T } as? T)?.value