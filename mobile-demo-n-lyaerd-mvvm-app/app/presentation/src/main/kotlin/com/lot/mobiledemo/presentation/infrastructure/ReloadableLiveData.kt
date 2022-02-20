package com.lot.mobiledemo.presentation.infrastructure

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class ReloadableLiveData<T>(
    private val coroutineScope: CoroutineScope,
    private var sourceFlow: Flow<T>? = null
) : MediatorLiveData<T>() {
    private var sourceLiveData: LiveData<T>? = null

    fun load() {
        recreateFlowLiveData(sourceFlow)
    }

    fun reload() {
        recreateFlowLiveData(sourceFlow)
    }

    fun setSource(flow: Flow<T>? = null) {
        sourceFlow = flow
        recreateFlowLiveData(sourceFlow)
    }

    private fun recreateFlowLiveData(flow: Flow<T>?) {
        checkNotNull(flow) { "Flow was not provided!" }
        sourceLiveData?.let { removeSource(it) }
        sourceLiveData = flow.asLiveData(coroutineScope.coroutineContext)
        addSource(sourceLiveData!!) { value = it }
    }
}

fun <T> Flow<T>.asReloadableLiveData(
    coroutineScope: CoroutineScope
): ReloadableLiveData<T> {
    return ReloadableLiveData(coroutineScope, this)
}
