package com.lot.mobiledata

import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.Buffer
import java.io.IOException
import java.lang.StringBuilder

fun Request.addQueryParams(vararg params: Pair<String, String>): Request {
    return if (method == "POST") {
        newBuilder()
            .post(appendUrlEncodedFields(params.toMap()))
            .build()
    } else {
        val builder = url.newBuilder()
        params.forEach { (name, value) -> builder.addQueryParameter(name, value) }
        val url = builder.build()
        newBuilder().url(url).build();
    }
}

fun Request.appendUrlEncodedFields(fields: Map<String, String>): RequestBody {
    val originalBodyString = body?.readString()
    val additionalBodyString = FormBody.Builder().apply {
        fields.forEach { (name, value) -> add(name, value) }
    }.build()

    val requestStringBuilder = StringBuilder()

    if (!originalBodyString.isNullOrBlank()) {
        requestStringBuilder.append("$originalBodyString&")
    }
    requestStringBuilder.append(additionalBodyString.readString())

    val contentType = "application/x-www-form-urlencoded;charset=UTF-8".toMediaType()
    return requestStringBuilder.toString().toRequestBody(contentType)
}

private fun RequestBody?.readString(): String {
    return try {
        val requestBodyCopy: RequestBody? = this
        val buffer = Buffer()
        if (requestBodyCopy != null) {
            requestBodyCopy.writeTo(buffer)
            buffer.readUtf8()
        } else {
            ""
        }
    } catch (e: IOException) {
        ""
    }
}
