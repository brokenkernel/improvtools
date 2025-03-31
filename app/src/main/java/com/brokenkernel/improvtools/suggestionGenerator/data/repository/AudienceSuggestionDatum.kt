package com.brokenkernel.improvtools.suggestionGenerator.data.repository

interface AudienceSuggestionDatumRepository {
    fun getAudienceDatum(): List<String>
}

class ResourcesAudienceSuggestionDatumRepository(): AudienceSuggestionDatumRepository {
    override fun getAudienceDatum(): List<String> {
        //            xmlResource
//            val context = LocalContext.current
//            val resources = context.resources
//            val audienceDatumAsXML = resources.getXml(R.xml.audience_suggestion_datum)

        return listOf("foo", "bar")
    }
}