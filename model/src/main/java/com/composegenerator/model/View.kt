package com.composegenerator.model

sealed class View(val attributes: Set<Attribute<Any>> = setOf()) {
    class Text(attributes: Set<Attribute<Any>>) : View(attributes)
    class Image(attributes: Set<Attribute<Any>>) : View(attributes)
    class Button(attributes: Set<Attribute<Any>>) : View(attributes)
    class Check(attributes: Set<Attribute<Any>>) : View(attributes)
    class Switch(attributes: Set<Attribute<Any>>) : View(attributes)
    class Progress(attributes: Set<Attribute<Any>>) : View(attributes)

    sealed class Container(attributes: Set<Attribute<Any>>, val children: List<out View>) :
        View(attributes) {
        class Frame(attributes: Set<Attribute<Any>>, children: List<View>) :
            Container(attributes, children)

        class Linear(attributes: Set<Attribute<Any>>, children: List<View>) :
            Container(attributes, children)

        class Card(attributes: Set<Attribute<Any>>, children: List<View>) :
            Container(attributes, children)

        class Scroll(attributes: Set<Attribute<Any>>, children: List<View>) :
            Container(attributes, children)

        class Table(attributes: Set<Attribute<Any>>, children: List<View>) :
            Container(attributes, children)

        internal val childrenString: String
            get() = children.map { it.string }.reduce { acc, element -> "$acc, $element" }
    }

    val string: String
        get() {
            val attributesString =
                attributes.map { it.string }.reduce { acc, string -> "$acc, $string" }
            return when (this) {
                is Container -> "${this::class.java.simpleName}($attributesString) [$childrenString]"
                else -> "${this::class.java.simpleName}($attributesString)"
            }
        }
}
