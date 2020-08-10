package com.composegenerator.model

sealed class UiAction {
    class OnClick(val action: () -> Unit): UiAction() {
        fun invoke() = action()
    }
}