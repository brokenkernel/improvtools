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
        ActionItem("abide", setOf("bear", "tolerate")),
        ActionItem("bear", setOf("stomach")),
        ActionItem("beg", setOf("solicit")),
        ActionItem("poison", setOf("pollute")),
        ActionItem("punish", setOf()),
        ActionItem("tolerate", setOf("bear", "indulge")),
        ActionItem("one", setOf("two")),
        ActionItem("two", setOf("one")),
    )

//    // TODO: back merge words too
//    private val actionItemMapOneWay: Map<String, Set<String>> = actionItemList
//        .associate { ail -> ail.word to ail.synonyms }
//
//    private val backwardMap: Map<String, Set<String>> = actionItemList
//        .flatMap { (word, synonyms) ->
//            synonyms.map { synonym ->
//                synonym to word
//            }
//        }
//        .groupBy { k -> k.first }
//        .mapValues { (k, v) ->
//            v.map { innerV -> innerV.second }.toSet()
//        }

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
