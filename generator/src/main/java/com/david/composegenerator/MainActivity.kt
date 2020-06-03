package com.david.composegenerator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.ui.core.setContent
import com.david.composegenerator.data.XmlLayoutViewExtractor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewExtractor = ComposeGenerator.createViewExtractor(applicationContext)
        val rootView = viewExtractor.extractRootView(R.layout.activity_main)
        setContent {
            UiElement(view = rootView)
        }
    }
}
