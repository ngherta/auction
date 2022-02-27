package com.lot.mobiledata.datasources.network.auth

import com.lot.mobiledata.datasources.disk.auth.TokenDiskSource
import com.lot.mobiledata.datasources.memmory.auth.TokenMemorySource
import javax.inject.Inject

class TokenProvider @Inject constructor(
    private val diskSource: TokenDiskSource,
    private val memorySource: TokenMemorySource
) {
    val token: String? get() = memorySource.data?.token ?: diskSource.data?.token
}
