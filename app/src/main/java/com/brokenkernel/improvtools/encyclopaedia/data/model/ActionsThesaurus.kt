package com.brokenkernel.improvtools.encyclopaedia.data.model

// TODO: this itself should be injected, future work.

internal object ActionsThesaurus {
    data class ActionItem(
        val word: String,
        val synonyms: Set<String> = setOf(),
    )

    // TODO: i18n
    // TODO: bidirectional is not always true, perhaps either explicitly list, or have an option or some such?
    private val actionItemList = setOf(
        ActionItem("access", setOf("reach", "enter")),
        ActionItem("accost", setOf("attack", "confront")),
        ActionItem("acquire", setOf("attain", "obtain")),
        ActionItem("abide", setOf("bear", "tolerate")),
        ActionItem("bear", setOf("stomach")),
        ActionItem("beg", setOf("solicit")),
        ActionItem("edit", setOf("modify")),
        ActionItem("poison", setOf("pollute")),
        ActionItem("punish", setOf()),
        ActionItem("tolerate", setOf("bear", "indulge")),
        ActionItem("destroy", setOf("hit", "kill")),
    )

    private val bidirectionalMap: Map<String, Set<String>>

    init {
        // TODO: this badly needs tests
        bidirectionalMap = HashMap()
        actionItemList.forEach { (word, synonyms) ->
            val set: MutableSet<String> = bidirectionalMap.getOrPut(word, { synonyms }).toMutableSet()
            synonyms.forEach { synonym ->
                set.add(synonym)
                val reverseSet: MutableSet<String> = bidirectionalMap.getOrPut(synonym, { emptySet() }).toMutableSet()
                reverseSet.add(word)
                bidirectionalMap.put(synonym, reverseSet)
            }
        }
    }

    fun keys(): Set<String> {
        return bidirectionalMap.keys
    }

    fun synonymsForWord(word: String): Set<String> {
        return bidirectionalMap[word].orEmpty()
    }
}
