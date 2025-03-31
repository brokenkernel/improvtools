package com.brokenkernel.improvtools.suggestionGenerator.data.repository

import android.content.Context
import android.content.res.Resources
import android.content.res.XmlResourceParser
import com.brokenkernel.improvtools.R
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.util.EnumMap
import javax.xml.parsers.DocumentBuilderFactory

interface AudienceSuggestionDatumRepository {
    fun getAudienceDatum(): List<String>
}

class ResourcesAudienceSuggestionDatumRepository(
    private val resources: Resources
): AudienceSuggestionDatumRepository {
//    private val audience_datum;

    init {
        val unprocessed_audience_datum: XmlResourceParser = resources.getXml(R.xml.audience_suggestion_datum)
        val parser = XmlPullParserFactory.newInstance().newPullParser()
        val fooMap: MutableMap<String, String> = HashMap()
        // <audience_suggestion_datum>
        //    <category name="noun">
        //        <w>Chair</w>
        //        <w>Fork>
        //        </w>
        //    </category>
        //    <category name="verb">
        //        <w>Deduct</w>
        //        <w>Think</w>
        //    </category>
        //</audience_suggestion_datum>
//        for
//        unprocessed_audience_datum.nextTag()
//        val dbf: DocumentBuilderFactory = DocumentBuilderFactory.newInstance()
//        val documentBuilder = dbf.newDocumentBuilder()
//        documentBuilder.parse(unprocessed_audience_datum)
//        unprocessed_audience_datum.
    }
    override fun getAudienceDatum(): List<String> {


//            val audienceDatumAsXML = resources.getXml(R.xml.audience_suggestion_datum)

        return listOf("foo", "bar")
    }
}