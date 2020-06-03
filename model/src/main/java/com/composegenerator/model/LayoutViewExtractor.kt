package com.composegenerator.model

interface LayoutViewExtractor {
    fun extractRootView(resourceId: Int): View
}