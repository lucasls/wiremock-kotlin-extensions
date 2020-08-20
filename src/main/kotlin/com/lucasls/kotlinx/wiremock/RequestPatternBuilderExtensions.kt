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

fun getRequestedFor(
    urlPattern: UrlPattern, block: RequestPatternBuilder.() -> Unit = {}
): RequestPatternBuilder =
    WireMock.getRequestedFor(urlPattern).apply(block)

fun postRequestedFor(
    urlPattern: UrlPattern, block: RequestPatternBuilder.() -> Unit = {}
): RequestPatternBuilder =
    WireMock.postRequestedFor(urlPattern).apply(block)

fun putRequestedFor(
    urlPattern: UrlPattern, block: RequestPatternBuilder.() -> Unit = {}
): RequestPatternBuilder =
    WireMock.putRequestedFor(urlPattern).apply(block)

fun deleteRequestedFor(
    urlPattern: UrlPattern, block: RequestPatternBuilder.() -> Unit = {}
): RequestPatternBuilder =
    WireMock.deleteRequestedFor(urlPattern).apply(block)

fun patchRequestedFor(
    urlPattern: UrlPattern, block: RequestPatternBuilder.() -> Unit = {}
): RequestPatternBuilder =
    WireMock.patchRequestedFor(urlPattern).apply(block)

fun headRequestedFor(
    urlPattern: UrlPattern, block: RequestPatternBuilder.() -> Unit = {}
): RequestPatternBuilder =
    WireMock.headRequestedFor(urlPattern).apply(block)

fun optionsRequestedFor(
    urlPattern: UrlPattern, block: RequestPatternBuilder.() -> Unit = {}
): RequestPatternBuilder =
    WireMock.optionsRequestedFor(urlPattern).apply(block)

fun traceRequestedFor(
    urlPattern: UrlPattern, block: RequestPatternBuilder.() -> Unit = {}
): RequestPatternBuilder =
    WireMock.traceRequestedFor(urlPattern).apply(block)

fun anyRequestedFor(
    urlPattern: UrlPattern, block: RequestPatternBuilder.() -> Unit = {}
): RequestPatternBuilder =
    WireMock.anyRequestedFor(urlPattern).apply(block)

fun requestMadeFor(
    customMatcherName: String, parameters: Parameters, block: RequestPatternBuilder.() -> Unit = {}
): RequestPatternBuilder =
    WireMock.requestMadeFor(customMatcherName, parameters).apply(block)

fun requestMadeFor(
    requestMatcher: ValueMatcher<Request>, block: RequestPatternBuilder.() -> Unit = {}
): RequestPatternBuilder =
    WireMock.requestMadeFor(requestMatcher).apply(block)

// Extensions

fun RequestPatternBuilder.withAnyRequestBodyPart(
    block: MultipartValuePatternBuilder.() -> Unit
): RequestPatternBuilder =
    this.withAnyRequestBodyPart(aMultipart().also(block))

fun RequestPatternBuilder.withAllRequestBodyParts(
    block: MultipartValuePatternBuilder.() -> Unit
): RequestPatternBuilder =
    this.withAllRequestBodyParts(aMultipart().also(block))