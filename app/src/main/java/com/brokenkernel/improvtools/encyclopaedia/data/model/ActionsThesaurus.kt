package com.brokenkernel.improvtools.encyclopaedia.data.model

import com.brokenkernel.improvtools.encyclopaedia.data.model.RawActionItemDataList.actionItemList

internal object ActionsThesaurus {

    private val aiAsMap: Map<String, Set<String>> = actionItemList.associate { (k, v) ->
        (k to v)
    }

    fun keys(): Set<String> {
        return aiAsMap.keys
    }

    fun synonymsForWord(word: String): Set<String> {
        return aiAsMap[word].orEmpty()
    }
}
