package com.composegenerator.model

class UnsupportedViewException(type: String) : Exception("Unsupported view type: $type")
class InvalidAttributeValue(attribute: String, value: String) : Exception("Invalid value for attribute $attribute: $value")