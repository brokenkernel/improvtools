package com.brokenkernel.improvtools.infrastructure

import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics.ConsentStatus
import com.google.firebase.analytics.FirebaseAnalytics.ConsentType
import com.google.firebase.analytics.analytics
import java.util.EnumMap

object ConsentManagement {
    fun configureConsentForFirebase(analyticsStorageConsent: Boolean) {
        val consentMap: MutableMap<ConsentType?, ConsentStatus?> =
            EnumMap<ConsentType?, ConsentStatus?>(ConsentType::class.java)

        if (analyticsStorageConsent) {
            consentMap.put(ConsentType.ANALYTICS_STORAGE, ConsentStatus.GRANTED)
        } else {
            consentMap.put(ConsentType.ANALYTICS_STORAGE, ConsentStatus.DENIED)
        }

        consentMap.put(ConsentType.AD_STORAGE, ConsentStatus.DENIED)
        consentMap.put(ConsentType.AD_USER_DATA, ConsentStatus.DENIED)
        consentMap.put(ConsentType.AD_PERSONALIZATION, ConsentStatus.DENIED)
        Firebase.analytics.setConsent(consentMap)
    }
}
