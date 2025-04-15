package com.brokenkernel.improvtools.application.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.brokenkernel.improvtools.R

@Composable
internal fun PrivacyScreen() {
    Column {
        Text(stringResource(R.string.privacy_notification_use))
    }
}