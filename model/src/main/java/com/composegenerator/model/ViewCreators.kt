package com.composegenerator.model

fun ViewType.create(attributes: Set<Attribute<Any>>, children: List<View> = listOf()): View {
    return when (this) {
        is ViewType.Text -> View.Text(attributes)
        is ViewType.Image -> View.Image(attributes)
        is ViewType.Button -> View.Button(attributes)
        is ViewType.Check -> View.Check(attributes)
        is ViewType.Progress -> View.Progress(attributes)
        is ViewType.Switch -> View.Switch(attributes)
        is ViewType.Container -> create(attributes, children)
    }
}

fun ViewType.Container.create(
    attributes: Set<Attribute<Any>>,
    children: List<View>
): View.Container {
    return when (this) {
        ViewType.Container.Frame -> View.Container.Frame(attributes, children)
        ViewType.Container.Linear -> View.Container.Linear(attributes, children)
        ViewType.Container.Scroll -> View.Container.Scroll(attributes, children)
        ViewType.Container.Card -> View.Container.Card(attributes, children)
        ViewType.Container.Table -> View.Container.Table(attributes, children)
    }
}
