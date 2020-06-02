package com.david.composegenerator

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.david.composegenerator.xmlparser.DefaultXmlViewExtractor
import com.composegenerator.model.XmlViewExtractor
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private lateinit var viewExtractor: XmlViewExtractor

    @Before
    fun setUp() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        viewExtractor = DefaultXmlViewExtractor(appContext)
    }

    @Test
    fun testParser() {
        val rootView = viewExtractor.extractRootView(R.layout.activity_main)
        assertNotNull(rootView)
    }
}
