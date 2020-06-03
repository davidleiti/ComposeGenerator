package com.david.composegenerator.data

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.ui.text.style.TextAlign
import com.composegenerator.model.LayoutDimension
import com.composegenerator.model.ViewOrientation
import com.composegenerator.model.ViewTextAlignment
import com.composegenerator.model.ViewVisibility

internal fun Int.toTextAlignment(): ViewTextAlignment = when (this) {
    View.TEXT_ALIGNMENT_CENTER -> ViewTextAlignment.Center
    View.TEXT_ALIGNMENT_TEXT_END, View.TEXT_ALIGNMENT_VIEW_END -> ViewTextAlignment.End
    View.TEXT_ALIGNMENT_TEXT_START, View.TEXT_ALIGNMENT_VIEW_START -> ViewTextAlignment.Start
    else -> ViewTextAlignment.Start
}

internal fun ViewTextAlignment.toUiTextAlign(): TextAlign = when (this) {
    ViewTextAlignment.Start -> TextAlign.Start
    ViewTextAlignment.End -> TextAlign.End
    ViewTextAlignment.Center -> TextAlign.Center
}

internal fun Int.toLayoutDimension(): LayoutDimension = when (this) {
    ViewGroup.LayoutParams.MATCH_PARENT -> LayoutDimension.MatchParent
    ViewGroup.LayoutParams.WRAP_CONTENT -> LayoutDimension.WrapContent
    0 -> LayoutDimension.MatchConstraint
    else -> LayoutDimension.FixedSize(this)
}

internal fun Int.toViewVisibility(): ViewVisibility = when (this) {
    View.VISIBLE -> ViewVisibility.Visible
    View.INVISIBLE -> ViewVisibility.Invisible
    else -> ViewVisibility.Gone
}

internal fun Int.toViewOrientation(): ViewOrientation = when (this) {
    LinearLayout.HORIZONTAL -> ViewOrientation.Horizontal
    else -> ViewOrientation.Vertical
}