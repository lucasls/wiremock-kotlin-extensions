package com.github.lucasls.kotlinx.wiremock

import com.github.tomakehurst.wiremock.client.CountMatchingStrategy
import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMockBuilder
import com.github.tomakehurst.wiremock.junit.Stubbing
import com.github.tomakehurst.wiremock.matching.BinaryEqualToPattern
import com.github.tomakehurst.wiremock.matching.EqualToXmlPattern
import com.github.tomakehurst.wiremock.matching.MatchesXPathPattern
import com.github.tomakehurst.wiremock.matching.MultipartValuePatternBuilder
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder
import com.github.tomakehurst.wiremock.matching.StringValuePattern
import com.github.tomakehurst.wiremock.matching.UrlPathPattern
import com.github.tomakehurst.wiremock.matching.UrlPattern
import com.github.tomakehurst.wiremock.recording.RecordSpecBuilder
import com.github.tomakehurst.wiremock.stubbing.StubMapping

fun configureFor(port: Int) = WireMock.configureFor(port)

fun configureFor(host: String, port: Int) = WireMock.configureFor(host, port)

fun configureFor(host: String, port: Int, urlPathPrefix: String) = WireMock.configureFor(host, port, urlPathPrefix)

fun configureFor(scheme: String, host: String, port: Int, urlPathPrefix: String) =
    WireMock.configureFor(scheme, host, port, urlPathPrefix)

fun configureFor(scheme: String, host: String, port: Int) = WireMock.configureFor(scheme, host, port)

fun configureFor(scheme: String, host: String, port: Int, proxyHost: String, proxyPort: Int) =
    WireMock.configureFor(scheme, host, port, proxyHost, proxyPort)

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
    countMatchingStrategy: CountMatchingStrategy,
    requestPatternBuilder: RequestPatternBuilder
) = WireMock.verify(countMatchingStrategy, requestPatternBuilder)

fun Stubbing.verify(vararg requestPatternBuilders: RequestPatternBuilder) = requestPatternBuilders.map {
    verify(it)
}

fun recordSpec(block: RecordSpecBuilder.() -> Unit): RecordSpecBuilder =
    WireMock.recordSpec().apply(block)

fun containing(value: String): StringValuePattern = WireMock.containing(value)

fun equalTo(value: String): StringValuePattern = WireMock.equalTo(value)

fun matchingXPath(value: String, namespaces: Map<String, String>): StringValuePattern =
    WireMock.matchingXPath(value, namespaces)

fun matchingXPath(value: String, valuePattern: StringValuePattern): StringValuePattern =
    WireMock.matchingXPath(value, valuePattern)

fun matching(regex: String): StringValuePattern = WireMock.matching(regex)

fun notMatching(regex: String): StringValuePattern = WireMock.notMatching(regex)

fun absent(): StringValuePattern = WireMock.absent()

fun equalToXml(value: String): StringValuePattern = WireMock.equalToXml(value)

fun binaryEqualTo(content: ByteArray): BinaryEqualToPattern = WireMock.binaryEqualTo(content)

fun binaryEqualTo(content: String): BinaryEqualToPattern = WireMock.binaryEqualTo(content)

fun equalToIgnoreCase(value: String): StringValuePattern = WireMock.equalToIgnoreCase(value)

fun equalToJson(value: String): StringValuePattern = WireMock.equalToJson(value)

fun equalToJson(value: String, ignoreArrayOrder: Boolean, ignoreExtraElements: Boolean): StringValuePattern =
    WireMock.equalToJson(value, ignoreArrayOrder, ignoreExtraElements)

fun matchingJsonPath(value: String): StringValuePattern = WireMock.matchingJsonPath(value)

fun matchingJsonPath(value: String, valuePattern: StringValuePattern): StringValuePattern =
    WireMock.matchingJsonPath(value, valuePattern)

fun equalToXml(value: String, enablePlaceholders: Boolean): EqualToXmlPattern =
    WireMock.equalToXml(value, enablePlaceholders)

fun equalToXml(
    value: String,
    enablePlaceholders: Boolean,
    placeholderOpeningDelimiterRegex: String,
    placeholderClosingDelimiterRegex: String
): EqualToXmlPattern = WireMock.equalToXml(
    value,
    enablePlaceholders,
    placeholderOpeningDelimiterRegex,
    placeholderClosingDelimiterRegex
)

fun matchingXPath(value: String): MatchesXPathPattern = WireMock.matchingXPath(value)