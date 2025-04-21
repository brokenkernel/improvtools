package com.brokenkernel.improvtools.encyclopaedia.data.model

internal object ActionsThesaurus {
    class ActionItem(
        val word: String,
        val synonyms: Set<String> = setOf(),
    )

    // TODO: i18n
    private val actionItemList = setOf(
        ActionItem("abide", setOf("bear", "tolerate")),
        ActionItem("bear", setOf("")),
        ActionItem("beg", setOf("solicit")),
        ActionItem("poison", setOf("pollute")),
        ActionItem("punish", setOf()),
        ActionItem("tolerate", setOf("bear")),
    )

    // TODO: back merge words too
    private val actionItemSet: Map<String, Set<String>> = actionItemList.associate { ail ->
        ail.word to ail.synonyms
    }

    fun keys(): Set<String> {
        return actionItemSet.keys
    }

    fun synonymsForWord(word: String): Set<String> {
        return actionItemSet[word].orEmpty()
    }
}
