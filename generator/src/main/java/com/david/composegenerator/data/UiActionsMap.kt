package com.david.composegenerator.data

import com.composegenerator.model.UiAction

class UiActionsMap(vararg actions: Pair<Int, UiAction>) {

    val actionsMap: MutableMap<Int, UiAction> = mutableMapOf<Int, UiAction>().apply { putAll(actions) }

    inline operator fun <reified T : UiAction?> get(viewRes: Int): T? =
        actionsMap.entries.firstOrNull { it.value is T && it.key == viewRes }?.value as T?

    operator fun <T : UiAction> plus(actionMapping: Pair<Int, UiAction>) {
        actionsMap[actionMapping.first] = actionMapping.second
    }
}