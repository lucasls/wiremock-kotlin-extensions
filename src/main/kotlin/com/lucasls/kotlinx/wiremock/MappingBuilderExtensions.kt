@file:Suppress("unused")

package com.lucasls.kotlinx.wiremock

import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder
import com.github.tomakehurst.wiremock.client.ScenarioMappingBuilder
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.extension.Parameters
import com.github.tomakehurst.wiremock.http.Request
import com.github.tomakehurst.wiremock.matching.UrlPattern
import com.github.tomakehurst.wiremock.matching.ValueMatcher

// Creators

inline fun get(urlPattern: UrlPattern, block: MappingBuilder.() -> Unit = {}): MappingBuilder =
    WireMock.get(urlPattern).also(block)

inline fun post(urlPattern: UrlPattern, block: MappingBuilder.() -> Unit = {}): MappingBuilder =
    WireMock.post(urlPattern).also(block)

inline fun put(urlPattern: UrlPattern, block: MappingBuilder.() -> Unit = {}): MappingBuilder =
    WireMock.put(urlPattern).also(block)

inline fun delete(urlPattern: UrlPattern, block: MappingBuilder.() -> Unit = {}): MappingBuilder =
    WireMock.delete(urlPattern).also(block)

inline fun patch(urlPattern: UrlPattern, block: MappingBuilder.() -> Unit = {}): MappingBuilder =
    WireMock.patch(urlPattern).also(block)

inline fun head(urlPattern: UrlPattern, block: MappingBuilder.() -> Unit = {}): MappingBuilder =
    WireMock.head(urlPattern).also(block)

inline fun options(urlPattern: UrlPattern, block: MappingBuilder.() -> Unit = {}): MappingBuilder =
    WireMock.options(urlPattern).also(block)

inline fun trace(urlPattern: UrlPattern, block: MappingBuilder.() -> Unit = {}): MappingBuilder =
    WireMock.trace(urlPattern).also(block)

inline fun any(urlPattern: UrlPattern, block: MappingBuilder.() -> Unit = {}): MappingBuilder =
    WireMock.any(urlPattern).also(block)

inline fun request(method: String, urlPattern: UrlPattern, block: MappingBuilder.() -> Unit = {}): MappingBuilder =
    WireMock.request(method, urlPattern).also(block)

inline fun requestMatching(customRequestMatcherName: String, block: MappingBuilder.() -> Unit = {}): MappingBuilder =
    WireMock.requestMatching(customRequestMatcherName).also(block)

inline fun requestMatching(
    customRequestMatcherName: String, parameters: Parameters, block: MappingBuilder.() -> Unit = {}
): MappingBuilder =
    WireMock.requestMatching(customRequestMatcherName, parameters).also(block)

inline fun requestMatching(
    requestMatcher: ValueMatcher<Request>, block: MappingBuilder.() -> Unit = {}
): MappingBuilder =
    WireMock.requestMatching(requestMatcher).also(block)

inline fun proxyAllTo(url: String, block: MappingBuilder.() -> Unit = {}): MappingBuilder =
    WireMock.proxyAllTo(url).also(block)

inline fun get(url: String, block: MappingBuilder.() -> Unit = {}): MappingBuilder =
    WireMock.get(url).also(block)

inline fun post(url: String, block: MappingBuilder.() -> Unit = {}): MappingBuilder =
    WireMock.post(url).also(block)

inline fun put(url: String, block: MappingBuilder.() -> Unit = {}): MappingBuilder =
    WireMock.put(url).also(block)

inline fun delete(url: String, block: MappingBuilder.() -> Unit = {}): MappingBuilder =
    WireMock.delete(url).also(block)

// Extensions

inline fun MappingBuilder.inScenario(
    scenarioName: String, block: ScenarioMappingBuilder.() -> Unit
): ScenarioMappingBuilder =
    this.inScenario(scenarioName).also(block)

infix fun MappingBuilder.willReturn(responseDefBuilder: ResponseDefinitionBuilder): MappingBuilder =
    this.willReturn(responseDefBuilder)

inline infix fun MappingBuilder.willReturn(block: ResponseDefinitionBuilder.() -> Unit): MappingBuilder =
    this.willReturn(aResponse(block))
