package com.brokenkernel.improvtools.encyclopaedia.data.model

internal enum class ActionThesaurusType {
    Action,
}

internal object ActionsThesaurus {
    data class ActionItem(
        val word: String,
        val synonyms: Set<String>,
    )

    // TODO: i18n
    // TODO: bidirectional is not always true, perhaps either explicitly list, or have an option or some such?
    private val actionItemList = setOf(
        ActionItem("abide", setOf("bear", "tolerate")),
        ActionItem("access", setOf("reach", "enter")),
        ActionItem("accost", setOf("attack", "confront")),
        ActionItem("acquire", setOf("attain", "obtain")),
        ActionItem("addle", setOf("agitate", "befuddle", "confuse", "distact", "dizzy")),
        ActionItem("aprehend", setOf("arrest", "stop")),
        ActionItem("attire", setOf("cloth", "dress")),
        ActionItem("avenge", setOf("punish")),
        ActionItem("bear", setOf("stomach")),
        ActionItem("beg", setOf("solicit")),
        ActionItem("destroy", setOf("hit", "kill")),
        ActionItem("edit", setOf("modify")),
        ActionItem("poison", setOf("pollute")),
        ActionItem("punish", setOf()),
        ActionItem("tolerate", setOf("bear", "indulge")),
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
