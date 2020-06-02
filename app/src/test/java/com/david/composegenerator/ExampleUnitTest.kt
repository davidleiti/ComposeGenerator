package com.david.composegenerator

import com.david.composegenerator.xmlparser.DefaultXmlViewExtractor
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private val parser = DefaultXmlViewExtractor()

    @Test
    fun testParser() {
        val xmlContent = """
        <FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
                xmlns:app="http://schemas.android.com/apk/res-auto"
                android:layout_width="match_parent"
                android:layout_height="match_parent">

            <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="Hello World!"
                    app:layout_constraintBottom_toBottomOf="parent"
                    app:layout_constraintLeft_toLeftOf="parent"
                    app:layout_constraintRight_toRightOf="parent"
                    app:layout_constraintTop_toTopOf="parent" />

        </FrameLayout>
    """
        val parser = DefaultXmlViewExtractor()
        xmlContent.byteInputStream().use { inputStream ->
            parser.parse(inputStream)
        }
    }

}
