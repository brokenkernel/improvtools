package com.brokenkernel.improvtools.application.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.brokenkernel.improvtools.infrastructure.ImprovToolsNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class OuterContentForMasterScreenViewModel @Inject constructor(val improvToolsNavigator: ImprovToolsNavigator) :
    ViewModel()