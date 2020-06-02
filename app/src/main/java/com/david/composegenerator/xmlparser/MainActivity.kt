package com.david.composegenerator.xmlparser

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.ui.core.setContent
import com.david.composegenerator.R
import com.david.composegenerator.compose.GeneratedView

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityView = DefaultXmlViewExtractor(this).extractRootView(R.layout.activity_main)
        Log.d("MainLogger", "Parse result for activity_main: ${activityView.string}")

        setContent {
            GeneratedView(this, view = activityView)
        }
    }
}