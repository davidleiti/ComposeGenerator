package com.composegenerator.model

interface XmlViewExtractor {
    fun extractRootView(resourceId: Int): View
}