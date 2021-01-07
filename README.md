# Compose Generator
Compose Generator is a framework that aims to provide compatibility between the
standard [Android XML layouts](https://developer.android.com/guide/topics/ui/declaring-layout) and [Jetpack Compose](https://developer.android.com/jetpack/compose), Google’s newest toolkit for native Android UI development.
## Jetpack Compose
Jetpack Compose is a library that is being developed by Google in order to create a
more modern and versatile framework for Android UI development. The existing
View-based UI framework is as old as Android itself and because of this it has many features
that were created for old devices and based on old methodologies.
Inspired by recent web languages such as Flutter, React, Litho, and Vue.js, Jetpack
Compose allows developers to create UI components in a declarative manner.
What Jetpack Compose offers is a new Kotlin-based API for creating
Android user interfaces in a declarative manner. In Compose, rather than having the
UI elements of our applications be managed via View object hierarchies that are
defined in completely separate XML files, the components of the UI are instead
created as a hierarchy of **@Composable** Kotlin functions.
```xml
<?xml version="1.0" encoding="utf-8"?>
<TextView xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:fontFamily="sans-serif"
    android:padding="16dp"
    android:text="Hello World!"
    android:textAlignment="center"
    android:textSize="22sp" />
```
_activity_main.xml_
```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_main)
    }
}
```
_MainActivity.kt_

Above we can see a very simple Android UI element composed of only a simple text
view. With this approach, the definition of the interface itself is defined in a
manner completely different than that of the programming style in which most of the
Android development is done (i.e. XML view and attribute definitions vs dynamic
Kotlin programming)
```kotlin
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      Message(content = "Hello World!")
    }
  }
}

@Composable
fun Message(content: String) {
  Text(
    text = content,
    modifier = Modifier
      .padding(16.dp)
      .fillMaxWidth()
      .wrapContentHeight(Alignment.CenterVertically),
    style = TextStyle(
      textAlign = TextAlign.Center,
      fontSize = 22.sp,
      fontFamily = FontFamily.SansSerif
    )
  )
}
```
_MainActivity.kt_

Compared to this, above we have the exact same UI element defined
and used via Jetpack Compose. The core concept here is the @Composable
annotation which marks that the function on which it is applied to is a so-called
Composable function, meaning that it defines a piece of user interface in a
declarative manner using Kotlin types and functions. The Text() function is, in turn,
also a Composable function from which a text view will be rendered on the screen
based on the parameters it has been given and the subsequent Composable
elements which may be added to its view hierarchy.

This approach provides a much more consistent, straight-forward and modular way
for UI development and even though it is currently only an experimental framework, it
being in the development preview phase, a very similar approach has been
developed for the iOS ecosystem in the form of SwiftUI, which has seen some
remarkable adoption rates and has been heavily pushed by Apple.

## Objective

Even though Compose offers great backwards-compatibility meaning that it is
available for lower-level Android API levels as well as compatibility with existing
Android code, one thing which it doesn’t allow as of yet is that of reusing existing UI
components defined as XML layouts in Composable elements. This means that any
layout that has been defined as such has to be converted into a hierarchy of
Composable functions, which in some cases may be burdensome and time
consuming, especially when it is not the main goal of the developers when
introducing Compose into their projects.

This is exactly what Compose Generator is trying to solve, and the way it does that is
by converting XML layout files provided by its users into a pre-generated hierarchy of
Composable UI components that have the same look and feel as the original one
without any of the extra code and time investment.

## Implementation

The framework has been written entirely in Kotlin and is structured into two main
modules: **model** and **generator**. The **model** is a plain Kotlin module where the
objects that will be extracted from the XML layout are defined, which are essentially
class hierarchies that are derived from **View**, **Attribute** and attribute value classes,
that represent the building blocks of classic Android user interfaces. These are for
the most part a hierarchy of View objects and attributes associated to these views.

Along these objects, each View and Attribute is associated with a ViewType and
AttributeType that will be later used during data extraction for finding out information
about the specific views or attributes and for creating instances of these classes.

This module is also where the interface for extracting the view objects from the given
layout is defined. This interface provides a single method with which the view defined
in the layout file is extracted along its attributes or a more complex view hierarchy
that has a View.Container object as its root view.
```kotlin
interface LayoutViewExtractor {
  fun extractRootView(layoutResource: Int): View
}
```
_LayoutViewExtractor.kt_

The model of the UI elements and their attributes that are to be extracted from the
layouts is in a very large part composed of [sealed class](https://kotlinlang.org/docs/reference/sealed-classes.html)
object hierarchies. In Kotlin,
Sealed Classes are used for representing restricted class hierarchies, when a value
can have one of the types from a limited set, but cannot have any other type. They
are, in a sense, an extension of enum classes: the set of values for an enum type is
also restricted, but each enum constant exists only as a single instance, whereas a
subclass of a sealed class can have multiple instances which can contain state.
These specifications make the sealed class approach used for modeling complex
View hierarchies a straightforward choice, especially when thinking about the
simplicity and safety provided by them in combination with Kotlin’s powerful when
statement and [extension functions](https://kotlinlang.org/docs/reference/extensions.html).

In the **generator** (Android module) the actual implementation of the view extraction
is found in the form of the XmlLayoutExtractor class. This class internally uses and
extends the XmlResourceParser interface provided by the Android framework to
extract the View objects along the set of attributes supported by the Compose
Generator framework from the provided layout file and maps the extracted data to fit
the model defined in the model.

The other significant part of the generator module is the set of @Composable
methods that are used as the building blocks of the UI generated by the framework.
These methods take as parameters the View objects extracted by the previously
mentioned objects and recursively build the appropriate Compose elements based
on the views’ types and provided attributes. The only such method that is actually
exposed by the framework UiElement(view: View) that will internally instantiate the
entire hierarchy of UI elements from the provided root view. The rest of the
composable elements are only defined for internal use within the framework and are
not being made available to the users.
```kotlin
abstract class ComposeGenerator {
  companion object {
    fun createViewExtractor(appContext: Context): LayoutViewExtractor =
    XmlLayoutViewExtractor(appContext)
  }
}

@Composable
fun UiElement(view: View) {
  GeneratedView(view)
}
```
_ComposeGenerator.kt - The core API exposed by the framework._

The implementation of the data extraction phase as well as some of the intermediate
data transformations heavily relies on Kotlin’s [extension functions and properties](https://kotlinlang.org/docs/reference/extensions.html).
Such functions and properties provide the ability to essentially extend the
functionality and characteristics of the classes they are written for without having to
inherit from them or affect their inner implementation. When defining extension
functions, the ```this``` keyword will refer to the receiver object of the function for which it
was defined, allowing for a concise and elegant way of enriching the underlying
classes. Extension properties work in a similar manner as they can be easily thought
of as custom getter functions hidden behind standard property syntax.

As such, these extensions provide a highly convenient and elegant way of retrieving
information and reasoning about certain objects possibly based on their type. Because of this, they are highly powerful tools especially when being used in concordance with hierarchies of well-defined sealed classes.

## Installation
The project is hosted on JitPack with the following installation steps for Gradle
projects:

Add these lines to the the project’s build.gradle file
```gradle
allprojects {
  repositories {
  ...
  maven { url 'https://jitpack.io' }
  }
}
```

Declare the dependency in the module’s build.gradle file
```gradle
dependencies {
 implementation 'com.github.davidleiti:ComposeGenerator:1.0.1-alpha01'
}
```

## Usage
Using the framework is relatively straight-forward, the steps for converting a standard Android layout defined
in activity_main.xml for this example is done in the following steps:
* Retrieve an instance of a ```LayoutViewExtractor``` interface from
ComposeGenerator by providing it the application context
* Extract the root view of the layout via ```viewExtractor.extractRootView()``` by
providing the target layout identifier
* Use the generated Compose element in a ```@Composable``` function such as
```ComponentActivity.setContent { ... }```
```kotlin
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
```
_MainActivity.kt_

## Limitations & Further improvements
- Limited number of supported Views, Attributes, and UiActions - the only
types supported by the framework are the following:
  - View types: TextView, ImageView, Button, CheckBox, Switch,
ProgressBar, FrameLayout, LinearLayout, CardView, ScrollView,
TableView
  - Attribute types: id, layout_height, layout_width, text, textSize, src,
checked, indeterminate, visibility, textAlignment, orientation, enabled,
clickable, hint, focusable, paddingTop, paddingBottom, paddingStart,
paddingEnd
  - UiActions: currently the framework only allows for binding OnClick 
actions to the generated composables by specifiying the Id of the source
views
- No support for relationship attributes between views such as constraints
relative to each other
