package com.david.composegenerator.data

import android.content.Context
import android.content.res.XmlResourceParser
import com.composegenerator.model.Attribute
import com.composegenerator.model.AttributeType
import com.composegenerator.model.LayoutDimension
import com.composegenerator.model.LayoutViewExtractor
import com.composegenerator.model.ValueType
import com.composegenerator.model.View
import com.composegenerator.model.ViewType
import com.composegenerator.model.create
import com.composegenerator.model.nameSpace
import com.composegenerator.model.supportedAttributes
import com.composegenerator.model.tag
import com.composegenerator.model.valueType
import org.xmlpull.v1.XmlPullParser.END_TAG
import org.xmlpull.v1.XmlPullParser.START_TAG

class DefaultLayoutViewExtractor(private val context: Context) : LayoutViewExtractor {

    override fun extractRootView(resourceId: Int): View {
        val parser = context.resources.getLayout(resourceId)
        while (parser.eventType != START_TAG) {
            parser.next()
        }
        return parser.extractView()
    }

    private fun XmlResourceParser.extractView(): View {
        val type = ViewType.createFromTag(name)
        val attributes = extractAttributes(type.supportedAttributes)

        return if (type is ViewType.Container) {
            val children = extractChildren()
            type.create(attributes, children)
        } else {
            next()
            type.create(attributes)
        }
    }

    private fun XmlResourceParser.extractAttributes(supportedAttributes: Set<AttributeType>): Set<Attribute<Any>> {
        val attributes: MutableSet<Attribute<Any>> = mutableSetOf()
        for (type: AttributeType in supportedAttributes) {
            val attribute = when (type.valueType) {
                ValueType.String -> extractStringAttribute(type)
                ValueType.Int -> extractIntAttribute(type)
                ValueType.Boolean -> extractBooleanAttribute(type)
            }
            attribute?.let { attributes.add(it) }
        }

        return attributes
    }

    private fun XmlResourceParser.extractChildren(): List<View> {
        val children = mutableListOf<View>()
        while (next() != END_TAG) {
            if (eventType != START_TAG) {
                continue
            }
            children.add(extractView())
        }

        return children
    }

    private fun XmlResourceParser.extractStringAttribute(type: AttributeType): Attribute<Any>? =
        extractAttributeResourceId(type)?.let { resourceId ->
            type.create(context.getString(resourceId))
        } ?: run {
            getAttributeValue(type.nameSpace, type.tag)?.let { value -> type.create(value) }
        }

    private fun XmlResourceParser.extractBooleanAttribute(type: AttributeType): Attribute<Any>? =
        getAttributeValue(type.nameSpace, type.tag)?.let {
            val value = getAttributeBooleanValue(type.nameSpace, type.tag, false)
            type.create(value)
        }

    private fun XmlResourceParser.extractIntAttribute(type: AttributeType): Attribute<Any>? =
        extractAttributeResourceId(type)?.let { resourceId ->
            createResourceAttribute(type, resourceId)
        } ?: run {
            createRawAttribute(type)
        }

    private fun XmlResourceParser.extractAttributeResourceId(type: AttributeType): Int? {
        val defaultValue = Int.MIN_VALUE
        val resourceId = getAttributeResourceValue(type.nameSpace, type.tag, defaultValue)
        return if (resourceId != defaultValue) resourceId else null
    }

    private fun createResourceAttribute(type: AttributeType, resourceId: Int): Attribute<Any>? {
        return when (type) {
            AttributeType.ID, AttributeType.Source -> type.create(resourceId)
            AttributeType.LayoutWidth,
            AttributeType.LayoutHeight -> {
                val dimension = context.getDimensionInDp(resourceId)
                val layoutDimension = LayoutDimension.FixedSize(dimension)
                type.create(layoutDimension)
            }
            AttributeType.TextSize,
            AttributeType.PaddingBottom,
            AttributeType.PaddingEnd,
            AttributeType.PaddingStart,
            AttributeType.PaddingTop -> {
                val dimension = context.getDimensionInDp(resourceId)
                type.create(dimension)
            }
            else -> null
        }
    }

    private fun XmlResourceParser.createRawAttribute(type: AttributeType): Attribute<Any>? {
        val defaultValue = Int.MIN_VALUE
        val value: Int = getAttributeIntValue(type.nameSpace, type.tag, defaultValue).takeIf { it != defaultValue }
            ?: getRawIntAttributeValue(type) ?: defaultValue
        return if (value != defaultValue) {
            when (type) {
                AttributeType.ID -> type.create(value)
                AttributeType.LayoutWidth, AttributeType.LayoutHeight -> type.create(value.toLayoutDimension())
                AttributeType.TextAlignment -> type.create(value.toTextAlignment())
                AttributeType.Visibility -> type.create(value.toViewVisibility())
                AttributeType.Orientation -> type.create(value.toViewOrientation())
                AttributeType.TextSize,
                AttributeType.PaddingStart,
                AttributeType.PaddingTop,
                AttributeType.PaddingBottom,
                AttributeType.PaddingEnd -> type.create(value)
                else -> null
            }
        } else {
            null
        }
    }

    private fun XmlResourceParser.getRawIntAttributeValue(type: AttributeType): Int? = try {
        getAttributeValue(type.nameSpace, type.tag).replace(Regex("[a-zA-Z]"), "").toFloat().toInt()
    } catch (exception: Exception) {
        null
    }
}



