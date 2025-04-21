package com.brokenkernel.improvtools.encyclopaedia.data.repository

import com.brokenkernel.improvtools.encyclopaedia.data.model.ActionsThesaurus

internal class DefaultThesaurusRepository : ThesaurusRepository {
    override fun getActionsThesaurus(): ActionsThesaurus {
        return ActionsThesaurus
    }
}
