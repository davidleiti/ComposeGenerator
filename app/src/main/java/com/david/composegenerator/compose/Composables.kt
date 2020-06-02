package com.david.composegenerator.compose

import android.content.Context
import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.foundation.VerticalScroller
import androidx.ui.graphics.imageFromResource
import androidx.ui.layout.Column
import androidx.ui.layout.Row
import androidx.ui.layout.fillMaxHeight
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.height
import androidx.ui.layout.padding
import androidx.ui.layout.width
import androidx.ui.layout.wrapContentHeight
import androidx.ui.layout.wrapContentWidth
import androidx.ui.material.Button
import androidx.ui.material.Card
import androidx.ui.material.CircularProgressIndicator
import androidx.ui.text.TextStyle
import androidx.ui.text.style.TextAlign
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import com.composegenerator.model.Attribute
import com.composegenerator.model.LayoutDimension
import com.composegenerator.model.View
import com.composegenerator.model.ViewOrientation
import com.composegenerator.model.ViewTextAlignment
import com.composegenerator.model.ViewVisibility
import com.david.composegenerator.R

@Composable
fun GeneratedView(context: Context, view: View) {
    when (view) {
        is View.Text -> GeneratedText(view = view)
        is View.Button -> GeneratedButton(view = view)
        is View.Image -> GeneratedImage(context = context, view = view)
        is View.Progress -> GeneratedProgressBar(view = view)
        is View.Container.Linear -> GeneratedLinearLayout(context = context, view = view)
        is View.Container.Scroll -> GeneratedScrollView(context = context, view = view)
        is View.Container.Frame -> GeneratedFrame(context = context, view = view)
        is View.Container.Card -> GeneratedCard(context = context, view = view)
        else -> Box()
    }
}

@Composable
fun GeneratedText(view: View.Text) {
    Text(
        text = view.text,
        style = TextStyle(
            fontSize = view.textSize.sp,
            textAlign = when (view.textAlignment) {
                ViewTextAlignment.Start -> TextAlign.Start
                ViewTextAlignment.End -> TextAlign.End
                ViewTextAlignment.Center -> TextAlign.Center
            }
        ),
        modifier = Modifier.applyGenericAttributes(view)
    )
}

@Composable
fun GeneratedProgressBar(view: View.Progress) {
    when (view.isIndeterminate) {
        true -> CircularProgressIndicator(Modifier.applyGenericAttributes(view))
        else -> CircularProgressIndicator(0F, Modifier.applyGenericAttributes(view))
    }
}

@Composable
fun GeneratedImage(context: Context, view: View.Image) {
    Image(
        asset = imageFromResource(context.resources, view.source),
        modifier = Modifier.applyGenericAttributes(view)
    )
}

@Composable
fun GeneratedButton(view: View.Button) {
    Button(
        onClick = {},
        modifier = Modifier.applyGenericAttributes(view)
    ) {
        Text(
            text = view.text,
            style = TextStyle(fontSize = view.textSize.sp)
        )
    }
}

@Composable
fun GeneratedLinearLayout(context: Context, view: View.Container.Linear) {
    when (view.orientation) {
        is ViewOrientation.Vertical -> GeneratedColumn(context = context, view = view)
        is ViewOrientation.Horizontal -> GeneratedRow(context = context, view = view)
    }
}


@Composable
fun GeneratedScrollView(context: Context, view: View.Container.Scroll) {
    VerticalScroller(
        modifier = Modifier.applyGenericAttributes(view)
    ) {
        view.children.forEach {
            GeneratedView(context = context, view = it)
        }
    }
}

@Composable
fun GeneratedFrame(context: Context, view: View.Container.Frame) {
    Box(
        modifier = Modifier.applyGenericAttributes(view)
    ) {
        view.children.forEach {
            GeneratedView(context = context, view = it)
        }
    }
}

@Composable
fun GeneratedCard(context: Context, view: View.Container.Card) {
    Card(
        modifier = Modifier.applyGenericAttributes(view)
    ) {
        view.children.forEach {
            GeneratedView(context = context, view = it)
        }
    }
}

@Composable
fun GeneratedRow(context: Context, view: View.Container.Linear) {
    Row(
        modifier = Modifier.applyGenericAttributes(view)
    ) {
        view.children.forEach {
            GeneratedView(context, it)
        }
    }
}

@Composable
fun GeneratedColumn(context: Context, view: View.Container.Linear) {
    Column(
        modifier = Modifier.applyGenericAttributes(view)
    ) {
        view.children.forEach {
            GeneratedView(context, it)
        }
    }
}

fun Modifier.applyGenericAttributes(view: View): Modifier =
    padding(
        start = view.paddingStart.dp,
        end = view.paddingEnd.dp,
        top = view.paddingTop.dp,
        bottom = view.paddingBottom.dp
    ).applyLayoutHeight(view.layoutWidth).applyLayoutHeight(view.layoutHeight)

fun Modifier.applyLayoutHeight(dimension: LayoutDimension): Modifier =
    when (dimension) {
        is LayoutDimension.WrapContent -> wrapContentHeight(Alignment.CenterVertically)
        is LayoutDimension.MatchParent -> fillMaxHeight()
        is LayoutDimension.FixedSize -> height(dimension.size.dp)
        else -> this
    }

fun Modifier.applyLayoutWidth(dimension: LayoutDimension): Modifier =
    when (dimension) {
        is LayoutDimension.WrapContent -> wrapContentWidth(Alignment.CenterHorizontally)
        is LayoutDimension.MatchParent -> fillMaxWidth()
        is LayoutDimension.FixedSize -> width(dimension.size.dp)
        else -> this
    }

const val DEFAULT_TEXT_SIZE = 12
const val PADDING_NONE = 0
const val PADDING_DEFAULT = 12

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
val View.paddingBottom: Int get() = getAttributeValue<Int, Attribute.PaddingTop>() ?: PADDING_NONE
val View.paddingStart: Int get() = getAttributeValue<Int, Attribute.PaddingTop>() ?: PADDING_NONE
val View.paddingEnd: Int get() = getAttributeValue<Int, Attribute.PaddingTop>() ?: PADDING_NONE

inline fun <S, reified T : Attribute<S>> View.getAttributeValue(): S? =
    (attributes.firstOrNull { it is T } as? T)?.value

