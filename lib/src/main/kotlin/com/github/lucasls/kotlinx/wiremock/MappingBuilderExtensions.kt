package com.github.lucasls.kotlinx.wiremock

import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder
import com.github.tomakehurst.wiremock.client.ScenarioMappingBuilder
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.extension.Parameters
import com.github.tomakehurst.wiremock.http.Request
import com.github.tomakehurst.wiremock.matching.MultipartValuePatternBuilder
import com.github.tomakehurst.wiremock.matching.UrlPattern
import com.github.tomakehurst.wiremock.matching.ValueMatcher

// Creators

fun get(urlPattern: UrlPattern, block: MappingBuilder.() -> Unit = {}): MappingBuilder =
    WireMock.get(urlPattern).also(block)

fun get(url: String, block: MappingBuilder.() -> Unit = {}): MappingBuilder =
    WireMock.get(url).also(block)

fun post(urlPattern: UrlPattern, block: MappingBuilder.() -> Unit = {}): MappingBuilder =
    WireMock.post(urlPattern).also(block)

fun post(url: String, block: MappingBuilder.() -> Unit = {}): MappingBuilder =
    WireMock.post(url).also(block)

fun put(urlPattern: UrlPattern, block: MappingBuilder.() -> Unit = {}): MappingBuilder =
    WireMock.put(urlPattern).also(block)

fun put(url: String, block: MappingBuilder.() -> Unit = {}): MappingBuilder =
    WireMock.put(url).also(block)

fun delete(urlPattern: UrlPattern, block: MappingBuilder.() -> Unit = {}): MappingBuilder =
    WireMock.delete(urlPattern).also(block)

fun patch(urlPattern: UrlPattern, block: MappingBuilder.() -> Unit = {}): MappingBuilder =
    WireMock.patch(urlPattern).also(block)

fun head(urlPattern: UrlPattern, block: MappingBuilder.() -> Unit = {}): MappingBuilder =
    WireMock.head(urlPattern).also(block)

fun options(urlPattern: UrlPattern, block: MappingBuilder.() -> Unit = {}): MappingBuilder =
    WireMock.options(urlPattern).also(block)

fun trace(urlPattern: UrlPattern, block: MappingBuilder.() -> Unit = {}): MappingBuilder =
    WireMock.trace(urlPattern).also(block)

fun any(urlPattern: UrlPattern, block: MappingBuilder.() -> Unit = {}): MappingBuilder =
    WireMock.any(urlPattern).also(block)

fun request(method: String, urlPattern: UrlPattern, block: MappingBuilder.() -> Unit = {}): MappingBuilder =
    WireMock.request(method, urlPattern).also(block)

fun requestMatching(customRequestMatcherName: String, block: MappingBuilder.() -> Unit = {}): MappingBuilder =
    WireMock.requestMatching(customRequestMatcherName).also(block)

fun requestMatching(
    customRequestMatcherName: String, parameters: Parameters, block: MappingBuilder.() -> Unit = {}
): MappingBuilder =
    WireMock.requestMatching(customRequestMatcherName, parameters).also(block)

fun requestMatching(
    requestMatcher: ValueMatcher<Request>, block: MappingBuilder.() -> Unit = {}
): MappingBuilder =
    WireMock.requestMatching(requestMatcher).also(block)

fun proxyAllTo(url: String, block: MappingBuilder.() -> Unit = {}): MappingBuilder =
    WireMock.proxyAllTo(url).also(block)

fun delete(url: String, block: MappingBuilder.() -> Unit = {}): MappingBuilder =
    WireMock.delete(url).also(block)

// Extensions

fun MappingBuilder.inScenario(
    scenarioName: String, block: ScenarioMappingBuilder.() -> Unit
): ScenarioMappingBuilder =
    this.inScenario(scenarioName).also(block)

fun MappingBuilder.withMultipartRequestBody(
    block: MultipartValuePatternBuilder.() -> Unit
): MappingBuilder =
    this.withMultipartRequestBody(aMultipart().also(block))

fun MappingBuilder.willReturnAResponse(block: ResponseDefinitionBuilder.() -> Unit = {}): MappingBuilder =
    this.willReturn(aResponse(block))
