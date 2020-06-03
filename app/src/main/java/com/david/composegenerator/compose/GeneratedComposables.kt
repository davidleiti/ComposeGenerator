package com.david.composegenerator.compose

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.Column
import androidx.ui.layout.Row
import androidx.ui.layout.fillMaxHeight
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.height
import androidx.ui.layout.padding
import androidx.ui.layout.width
import androidx.ui.material.Button
import androidx.ui.material.Card
import androidx.ui.material.CircularProgressIndicator
import androidx.ui.res.imageResource
import androidx.ui.text.TextStyle
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import com.composegenerator.model.LayoutDimension
import com.composegenerator.model.View
import com.composegenerator.model.ViewOrientation
import com.david.composegenerator.data.toUiTextAlign

@Composable
fun GeneratedView(view: View) {
    if (view.visible) {
        when (view) {
            is View.Text -> GeneratedText(view = view)
            is View.Button -> GeneratedButton(view = view)
            is View.Image -> GeneratedImage(view = view)
            is View.Progress -> GeneratedProgressBar(view = view)
            is View.Container.Linear -> GeneratedLinearLayout(view = view)
            is View.Container.Scroll -> GeneratedScrollView(view = view)
            is View.Container.Frame -> GeneratedFrame(view = view)
            is View.Container.Card -> GeneratedCard(view = view)
            else -> Box()
        }
    }
}

@Composable
fun GeneratedText(view: View.Text) {
    Text(
        text = view.text,
        style = TextStyle(
            fontSize = view.textSize.sp,
            textAlign = view.textAlignment.toUiTextAlign()
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
fun GeneratedImage(view: View.Image) {
    Image(
        asset = imageResource(view.source),
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
fun GeneratedLinearLayout(view: View.Container.Linear) {
    when (view.orientation) {
        is ViewOrientation.Vertical -> GeneratedColumn(view = view)
        is ViewOrientation.Horizontal -> GeneratedRow(view = view)
    }
}

@Composable
fun GeneratedScrollView(view: View.Container.Scroll) {
    VerticalScroller(
        modifier = Modifier.applyGenericAttributes(view)
    ) {
        view.children.forEach {
            GeneratedView(view = it)
        }
    }
}

@Composable
fun GeneratedFrame(view: View.Container.Frame) {
    Box(
        modifier = Modifier.applyGenericAttributes(view)
    ) {
        view.children.forEach {
            GeneratedView(view = it)
        }
    }
}

@Composable
fun GeneratedCard(view: View.Container.Card) {
    Card(
        modifier = Modifier.applyGenericAttributes(view)
    ) {
        view.children.forEach {
            GeneratedView(view = it)
        }
    }
}

@Composable
fun GeneratedRow(view: View.Container.Linear) {
    Row(
        modifier = Modifier.applyGenericAttributes(view)
    ) {
        view.children.forEach {
            GeneratedView(it)
        }
    }
}

@Composable
fun GeneratedColumn(view: View.Container.Linear) {
    Column(
        modifier = Modifier.applyGenericAttributes(view)
    ) {
        view.children.forEach {
            GeneratedView(it)
        }
    }
}

private fun Modifier.applyGenericAttributes(view: View): Modifier =
    padding(
        start = view.paddingStart.dp,
        end = view.paddingEnd.dp,
        top = view.paddingTop.dp,
        bottom = view.paddingBottom.dp
    ).applyLayoutWidth(view.layoutWidth).applyLayoutHeight(view.layoutHeight)

private fun Modifier.applyLayoutHeight(dimension: LayoutDimension): Modifier =
    when (dimension) {
        is LayoutDimension.MatchParent -> fillMaxHeight()
        is LayoutDimension.FixedSize -> height(dimension.size.dp)
        else -> this
    }

private fun Modifier.applyLayoutWidth(dimension: LayoutDimension): Modifier =
    when (dimension) {
        is LayoutDimension.MatchParent -> fillMaxWidth()
        is LayoutDimension.FixedSize -> width(dimension.size.dp)
        else -> this
    }

