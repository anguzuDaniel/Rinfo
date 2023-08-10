package com.danotech.rinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danotech.rinfo.common.SnackbarManager
import com.danotech.rinfo.common.SnackbarMessage.Companion.toSnackbarMessage
import com.danotech.rinfo.model.service.LogService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class RinfoViewModel(
    private val logService: LogService
) : ViewModel() {
    fun launchCatching(snackbar: Boolean = true, block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                if (snackbar) {
                    SnackbarManager.showMessage(throwable.toSnackbarMessage())
                }
                logService.logNonFatalCrash(throwable)
            },
            block = block
        )
}