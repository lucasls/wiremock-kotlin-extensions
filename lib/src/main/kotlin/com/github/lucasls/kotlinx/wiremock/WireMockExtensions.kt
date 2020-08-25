package com.github.lucasls.kotlinx.wiremock

import com.github.tomakehurst.wiremock.client.CountMatchingStrategy
import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMockBuilder
import com.github.tomakehurst.wiremock.junit.Stubbing
import com.github.tomakehurst.wiremock.matching.MultipartValuePatternBuilder
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder
import com.github.tomakehurst.wiremock.matching.UrlPathPattern
import com.github.tomakehurst.wiremock.matching.UrlPattern
import com.github.tomakehurst.wiremock.recording.RecordSpecBuilder
import com.github.tomakehurst.wiremock.stubbing.StubMapping

fun createWireMock(block: WireMockBuilder.() -> Unit = {}): WireMock =
    WireMock.create()
        .also(block)
        .build()

fun givenThat(mappingBuilder: MappingBuilder): StubMapping =
    WireMock.givenThat(mappingBuilder)

fun stubFor(mappingBuilder: MappingBuilder): StubMapping =
    WireMock.stubFor(mappingBuilder)

fun givenThat(vararg mappingBuilders: MappingBuilder) = mappingBuilders.map {
    givenThat(it)
}

fun stubFor(vararg mappingBuilders: MappingBuilder) = mappingBuilders.map {
    stubFor(it)
}

fun Stubbing.givenThat(vararg mappingBuilders: MappingBuilder) = mappingBuilders.map {
    givenThat(it)
}

fun Stubbing.stubFor(vararg mappingBuilders: MappingBuilder) = mappingBuilders.map {
    stubFor(it)
}

fun urlEqualTo(testUrl: String): UrlPattern = WireMock.urlEqualTo(testUrl)

fun urlMatching(urlRegex: String): UrlPattern = WireMock.urlMatching(urlRegex)

fun urlPathEqualTo(testUrl: String): UrlPathPattern = WireMock.urlPathEqualTo(testUrl)

fun urlPathMatching(urlRegex: String): UrlPathPattern = WireMock.urlPathMatching(urlRegex)

fun anyUrl(): UrlPattern = WireMock.anyUrl()

val anyUrl: UrlPattern get() = WireMock.anyUrl()

fun lessThan(expected: Int): CountMatchingStrategy = WireMock.lessThan(expected)

fun lessThanOrExactly(expected: Int): CountMatchingStrategy = WireMock.lessThanOrExactly(expected)

fun exactly(expected: Int): CountMatchingStrategy = WireMock.exactly(expected)

fun moreThanOrExactly(expected: Int): CountMatchingStrategy = WireMock.moreThanOrExactly(expected)

fun moreThan(expected: Int): CountMatchingStrategy = WireMock.moreThan(expected)

fun aMultipart(): MultipartValuePatternBuilder = WireMock.aMultipart()

fun aMultipart(name: String): MultipartValuePatternBuilder = WireMock.aMultipart(name)

fun verify(requestPatternBuilder: RequestPatternBuilder) =
    WireMock.verify(requestPatternBuilder)

fun verify(vararg requestPatternBuilders: RequestPatternBuilder) = requestPatternBuilders.map {
    verify(it)
}

fun verify(count: Int, requestPatternBuilder: RequestPatternBuilder) =
    WireMock.verify(count, requestPatternBuilder)

fun verify(
    countMatchingStrategy: CountMatchingStrategy, requestPatternBuilder: RequestPatternBuilder
) = WireMock.verify(countMatchingStrategy, requestPatternBuilder)

fun Stubbing.verify(vararg requestPatternBuilders: RequestPatternBuilder) = requestPatternBuilders.map {
    verify(it)
}

fun recordSpec(block: RecordSpecBuilder.() -> Unit): RecordSpecBuilder =
    WireMock.recordSpec().apply(block)