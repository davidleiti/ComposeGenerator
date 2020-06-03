package com.david.composegenerator.compose

import com.composegenerator.model.Attribute
import com.composegenerator.model.LayoutDimension
import com.composegenerator.model.View
import com.composegenerator.model.ViewOrientation
import com.composegenerator.model.ViewTextAlignment
import com.composegenerator.model.ViewVisibility
import com.david.composegenerator.R

const val DEFAULT_TEXT_SIZE = 12
const val PADDING_NONE = 0

val View.text: String get() = getAttributeValue<String, Attribute.Text>().orEmpty()
val View.textSize: Int get() = getAttributeValue<Int, Attribute.TextSize>() ?: DEFAULT_TEXT_SIZE
val View.Progress.isIndeterminate: Boolean get() = getAttributeValue<Boolean, Attribute.Indeterminate>() ?: false
val View.Image.source: Int get() = getAttributeValue<Int, Attribute.Source>() ?: R.drawable.ic_baseline_error_24
val View.textAlignment: ViewTextAlignment get() = getAttributeValue<ViewTextAlignment, Attribute.TextAlignment>() ?: ViewTextAlignment.Start
val View.Container.Linear.orientation: ViewOrientation get() = getAttributeValue<ViewOrientation, Attribute.Orientation>() ?: ViewOrientation.Vertical
val View.visible: Boolean get() = getAttributeValue<ViewVisibility, Attribute.Visibility>()?.let { it is ViewVisibility.Visible } ?: true
val View.layoutHeight: LayoutDimension get() = getAttributeValue<LayoutDimension, Attribute.LayoutHeight>() ?: LayoutDimension.WrapContent
val View.layoutWidth: LayoutDimension get() = getAttributeValue<LayoutDimension, Attribute.LayoutWidth>() ?: LayoutDimension.WrapContent
val View.paddingTop: Int get() = getAttributeValue<Int, Attribute.PaddingTop>() ?: PADDING_NONE
val View.paddingBottom: Int get() = getAttributeValue<Int, Attribute.PaddingBottom>() ?: PADDING_NONE
val View.paddingStart: Int get() = getAttributeValue<Int, Attribute.PaddingStart>() ?: PADDING_NONE
val View.paddingEnd: Int get() = getAttributeValue<Int, Attribute.PaddingEnd>() ?: PADDING_NONE

inline fun <S, reified T : Attribute<S>> View.getAttributeValue(): S? =
    (attributes.firstOrNull { it is T } as? T)?.value