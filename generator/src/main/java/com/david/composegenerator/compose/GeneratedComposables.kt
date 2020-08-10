package com.david.composegenerator.compose

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Clickable
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
import com.composegenerator.model.UiAction
import com.composegenerator.model.View
import com.composegenerator.model.ViewOrientation
import com.david.composegenerator.data.UiActionsMap
import com.david.composegenerator.data.toUiTextAlign

@Composable
internal fun GeneratedView(view: View, uiActions: UiActionsMap) {
    val onClick = view.extractAction<UiAction.OnClick>(uiActions)
    when {
        onClick == null || view.supportsActionNatively<UiAction.OnClick>() -> GeneratedStaticView(view = view, uiActions = uiActions)
        else -> Clickable(onClick = { onClick.invoke() }) {
            GeneratedStaticView(view = view, uiActions = uiActions)
        }
    }
}

@Composable
internal fun GeneratedStaticView(view: View, uiActions: UiActionsMap) {
    if (view.visible) {
        when (view) {
            is View.Text -> GeneratedText(view = view)
            is View.Button -> GeneratedButton(view = view, onClick = view.extractAction(uiActions))
            is View.Image -> GeneratedImage(view = view)
            is View.Progress -> GeneratedProgressBar(view = view)
            is View.Container.Linear -> GeneratedLinearLayout(view = view, uiActions = uiActions)
            is View.Container.Scroll -> GeneratedScrollView(view = view, uiActions = uiActions)
            is View.Container.Frame -> GeneratedFrame(view = view, uiActions = uiActions)
            is View.Container.Card -> GeneratedCard(view = view, uiActions = uiActions)
            else -> Box()
        }
    }
}

@Composable
internal fun GeneratedText(view: View.Text) {
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
internal fun GeneratedProgressBar(view: View.Progress) {
    when (view.isIndeterminate) {
        true -> CircularProgressIndicator(Modifier.applyGenericAttributes(view))
        else -> CircularProgressIndicator(0F, Modifier.applyGenericAttributes(view))
    }
}

@Composable
internal fun GeneratedImage(view: View.Image) {
    Image(
        asset = imageResource(view.source),
        modifier = Modifier.applyGenericAttributes(view)
    )
}

@Composable
internal fun GeneratedButton(view: View.Button, onClick: UiAction.OnClick? = null) {
    Button(
        onClick = { onClick?.invoke() },
        modifier = Modifier.applyGenericAttributes(view)
    ) {
        Text(
            text = view.text,
            style = TextStyle(fontSize = view.textSize.sp)
        )
    }
}

@Composable
internal fun GeneratedLinearLayout(view: View.Container.Linear, uiActions: UiActionsMap) {
    when (view.orientation) {
        is ViewOrientation.Vertical -> GeneratedColumn(view = view, uiActions = uiActions)
        is ViewOrientation.Horizontal -> GeneratedRow(view = view, uiActions = uiActions)
    }
}

@Composable
internal fun GeneratedScrollView(view: View.Container.Scroll, uiActions: UiActionsMap) {
    VerticalScroller(
        modifier = Modifier.applyGenericAttributes(view)
    ) {
        view.children.forEach {
            GeneratedView(view = it, uiActions = uiActions)
        }
    }
}

@Composable
internal fun GeneratedFrame(view: View.Container.Frame, uiActions: UiActionsMap) {
    Box(
        modifier = Modifier.applyGenericAttributes(view)
    ) {
        view.children.forEach {
            GeneratedView(view = it, uiActions = uiActions)
        }
    }
}

@Composable
internal fun GeneratedCard(view: View.Container.Card, uiActions: UiActionsMap) {
    Card(
        modifier = Modifier.applyGenericAttributes(view)
    ) {
        view.children.forEach {
            GeneratedView(view = it, uiActions = uiActions)
        }
    }
}

@Composable
internal fun GeneratedRow(view: View.Container.Linear, uiActions: UiActionsMap) {
    Row(
        modifier = Modifier.applyGenericAttributes(view)
    ) {
        view.children.forEach {
            GeneratedView(view = it, uiActions = uiActions)
        }
    }
}

@Composable
internal fun GeneratedColumn(view: View.Container.Linear, uiActions: UiActionsMap) {
    Column(
        modifier = Modifier.applyGenericAttributes(view)
    ) {
        view.children.forEach {
            GeneratedView(view = it, uiActions = uiActions)
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

