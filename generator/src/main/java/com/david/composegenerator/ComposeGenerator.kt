package com.david.composegenerator

import android.content.Context
import androidx.compose.Composable
import com.composegenerator.model.LayoutViewExtractor
import com.composegenerator.model.View
import com.david.composegenerator.compose.GeneratedView
import com.david.composegenerator.data.XmlLayoutViewExtractor

abstract class ComposeGenerator {
    companion object {
        fun createViewExtractor(appContext: Context): LayoutViewExtractor = XmlLayoutViewExtractor(appContext)
    }
}

@Composable
fun UiElement(view: View) {
    GeneratedView(view)
}