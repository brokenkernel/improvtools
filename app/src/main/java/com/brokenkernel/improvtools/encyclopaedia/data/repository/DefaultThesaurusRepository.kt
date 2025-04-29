package com.brokenkernel.improvtools.encyclopaedia.data.repository

import com.brokenkernel.improvtools.encyclopaedia.data.model.ActionsThesaurus
import net.sf.extjwnl.dictionary.Dictionary

internal class DefaultThesaurusRepository : ThesaurusRepository {
    val dictionary: Dictionary? = Dictionary.getDefaultResourceInstance()

    override fun getActionsThesaurus(): ActionsThesaurus {
        return ActionsThesaurus
    }

    override fun getEXTJWNLDictionary(): Dictionary? {
        return dictionary
    }
}
