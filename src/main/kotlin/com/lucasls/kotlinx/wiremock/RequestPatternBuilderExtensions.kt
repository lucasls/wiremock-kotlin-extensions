@file:Suppress("unused")
package com.lucasls.kotlinx.wiremock

import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.extension.Parameters
import com.github.tomakehurst.wiremock.http.Request
import com.github.tomakehurst.wiremock.matching.MultipartValuePatternBuilder
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder
import com.github.tomakehurst.wiremock.matching.UrlPattern
import com.github.tomakehurst.wiremock.matching.ValueMatcher

// Creators

inline fun getRequestedFor(
    urlPattern: UrlPattern, block: RequestPatternBuilder.() -> Unit = {}
): RequestPatternBuilder =
    WireMock.getRequestedFor(urlPattern).apply(block)

inline fun postRequestedFor(
    urlPattern: UrlPattern, block: RequestPatternBuilder.() -> Unit = {}
): RequestPatternBuilder =
    WireMock.postRequestedFor(urlPattern).apply(block)

inline fun putRequestedFor(
    urlPattern: UrlPattern, block: RequestPatternBuilder.() -> Unit = {}
): RequestPatternBuilder =
    WireMock.putRequestedFor(urlPattern).apply(block)

inline fun deleteRequestedFor(
    urlPattern: UrlPattern, block: RequestPatternBuilder.() -> Unit = {}
): RequestPatternBuilder =
    WireMock.deleteRequestedFor(urlPattern).apply(block)

inline fun patchRequestedFor(
    urlPattern: UrlPattern, block: RequestPatternBuilder.() -> Unit = {}
): RequestPatternBuilder =
    WireMock.patchRequestedFor(urlPattern).apply(block)

inline fun headRequestedFor(
    urlPattern: UrlPattern, block: RequestPatternBuilder.() -> Unit = {}
): RequestPatternBuilder =
    WireMock.headRequestedFor(urlPattern).apply(block)

inline fun optionsRequestedFor(
    urlPattern: UrlPattern, block: RequestPatternBuilder.() -> Unit = {}
): RequestPatternBuilder =
    WireMock.optionsRequestedFor(urlPattern).apply(block)

inline fun traceRequestedFor(
    urlPattern: UrlPattern, block: RequestPatternBuilder.() -> Unit = {}
): RequestPatternBuilder =
    WireMock.traceRequestedFor(urlPattern).apply(block)

inline fun anyRequestedFor(
    urlPattern: UrlPattern, block: RequestPatternBuilder.() -> Unit = {}
): RequestPatternBuilder =
    WireMock.anyRequestedFor(urlPattern).apply(block)

inline fun requestMadeFor(
    customMatcherName: String, parameters: Parameters, block: RequestPatternBuilder.() -> Unit = {}
): RequestPatternBuilder =
    WireMock.requestMadeFor(customMatcherName, parameters).apply(block)

inline fun requestMadeFor(
    requestMatcher: ValueMatcher<Request>, block: RequestPatternBuilder.() -> Unit = {}
): RequestPatternBuilder =
    WireMock.requestMadeFor(requestMatcher).apply(block)

// Extensions

inline fun RequestPatternBuilder.withAnyRequestBodyPart(
    block: MultipartValuePatternBuilder.() -> Unit
): RequestPatternBuilder =
    this.withAnyRequestBodyPart(aMultipart().also(block))

inline fun RequestPatternBuilder.withAllRequestBodyParts(
    block: MultipartValuePatternBuilder.() -> Unit
): RequestPatternBuilder =
    this.withAllRequestBodyParts(aMultipart().also(block))