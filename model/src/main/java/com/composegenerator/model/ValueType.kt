package com.composegenerator.model

sealed class ValueType {
    object String : ValueType()
    object Int : ValueType()
    object Boolean : ValueType()
}