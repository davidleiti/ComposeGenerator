package com.composegenerator.model

/**
 * Interface used for extracting the view hierarchy from a given layout file.
 */
interface LayoutViewExtractor {
    /**
     * Extracts the view hierarchy from the provided layout resource along with the attributes associated to each view object.
     * @param layoutResource and Int value identifying the layout resource from which the contents should be extracted
     * @return [View] object that can be a simple object such as [View.Text] or [View.Button], or a more complex View hierarchy having a
     * [View.Container] as its root view.
     */
    fun extractRootView(layoutResource: Int): View
}