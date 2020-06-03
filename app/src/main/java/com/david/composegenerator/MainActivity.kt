package com.david.composegenerator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.ui.core.setContent
import com.david.composegenerator.compose.GeneratedView
import com.david.composegenerator.data.DefaultLayoutViewExtractor

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityView = DefaultLayoutViewExtractor(applicationContext).extractRootView(R.layout.activity_main)
        setContent {
            GeneratedView(view = activityView)
        }
    }
}