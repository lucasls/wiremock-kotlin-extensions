package com.lucasls.kotlinx.wiremock

import com.github.tomakehurst.wiremock.client.CountMatchingStrategy
import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMockBuilder
import com.github.tomakehurst.wiremock.extension.Parameters
import com.github.tomakehurst.wiremock.http.Request
import com.github.tomakehurst.wiremock.matching.UrlPathPattern
import com.github.tomakehurst.wiremock.matching.UrlPattern
import com.github.tomakehurst.wiremock.matching.ValueMatcher
import com.github.tomakehurst.wiremock.stubbing.StubMapping

inline fun createWireMock(block: WireMockBuilder.() -> Unit = {}): WireMock =
    WireMock.create()
        .also(block)
        .build()

inline fun givenThat(block: () -> MappingBuilder): StubMapping =
    WireMock.givenThat(block())

inline fun stubFor(block: () -> MappingBuilder): StubMapping = WireMock.stubFor(block())

inline fun editStub(block: () -> MappingBuilder) = WireMock.editStub(block())

inline fun removeStub(block: () -> MappingBuilder) = WireMock.removeStub(block())


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
): MappingBuilder = WireMock.requestMatching(customRequestMatcherName, parameters).also(block)

inline fun requestMatching(requestMatcher: ValueMatcher<Request>, block: MappingBuilder.() -> Unit = {}):
        MappingBuilder =
    WireMock.requestMatching(requestMatcher).also(block)

inline fun aResponse(block: ResponseDefinitionBuilder.() -> Unit = {}): ResponseDefinitionBuilder =
    WireMock.aResponse().also(block)

inline fun ok(block: ResponseDefinitionBuilder.() -> Unit = {}): ResponseDefinitionBuilder =
    WireMock.ok().also(block)

inline fun ok(body: String, block: ResponseDefinitionBuilder.() -> Unit = {}): ResponseDefinitionBuilder =
    WireMock.ok(body).also(block)

inline fun okForContentType(
    contentType: String, body: String, block: ResponseDefinitionBuilder.() -> Unit = {}
): ResponseDefinitionBuilder = WireMock.okForContentType(contentType, body).also(block)

inline fun okJson(body: String, block: ResponseDefinitionBuilder.() -> Unit = {}): ResponseDefinitionBuilder =
    WireMock.okJson(body).also(block)

inline fun okXml(body: String, block: ResponseDefinitionBuilder.() -> Unit = {}): ResponseDefinitionBuilder =
    WireMock.okXml(body).also(block)

inline fun okTextXml(body: String, block: ResponseDefinitionBuilder.() -> Unit = {}): ResponseDefinitionBuilder =
    WireMock.okTextXml(body).also(block)


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


inline fun created(block: ResponseDefinitionBuilder.() -> Unit = {}): ResponseDefinitionBuilder =
    WireMock.created().also(block)

inline fun noContent(block: ResponseDefinitionBuilder.() -> Unit = {}): ResponseDefinitionBuilder =
    WireMock.noContent().also(block)

inline fun permanentRedirect(
    location: String, block: ResponseDefinitionBuilder.() -> Unit = {}
): ResponseDefinitionBuilder = WireMock.permanentRedirect(location).also(block)

inline fun temporaryRedirect(
    location: String, block: ResponseDefinitionBuilder.() -> Unit = {}
): ResponseDefinitionBuilder = WireMock.temporaryRedirect(location).also(block)

inline fun seeOther(
    location: String, block: ResponseDefinitionBuilder.() -> Unit = {}
): ResponseDefinitionBuilder = WireMock.seeOther(location).also(block)

inline fun badRequest(block: ResponseDefinitionBuilder.() -> Unit = {}): ResponseDefinitionBuilder =
    WireMock.badRequest().also(block)

inline fun badRequestEntity(block: ResponseDefinitionBuilder.() -> Unit = {}): ResponseDefinitionBuilder =
    WireMock.badRequestEntity().also(block)

inline fun unauthorized(block: ResponseDefinitionBuilder.() -> Unit = {}): ResponseDefinitionBuilder =
    WireMock.unauthorized().also(block)

inline fun forbidden(block: ResponseDefinitionBuilder.() -> Unit = {}): ResponseDefinitionBuilder =
    WireMock.forbidden().also(block)

inline fun notFound(block: ResponseDefinitionBuilder.() -> Unit = {}): ResponseDefinitionBuilder =
    WireMock.notFound().also(block)

inline fun serverError(block: ResponseDefinitionBuilder.() -> Unit = {}): ResponseDefinitionBuilder =
    WireMock.serverError().also(block)

inline fun serviceUnavailable(block: ResponseDefinitionBuilder.() -> Unit = {}): ResponseDefinitionBuilder =
    WireMock.serviceUnavailable().also(block)

inline fun status(status: Int, block: ResponseDefinitionBuilder.() -> Unit = {}): ResponseDefinitionBuilder =
    WireMock.status(status).also(block)