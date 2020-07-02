@file:Suppress("unused")
package com.lucasls.kotlinx.wiremock

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder
import com.github.tomakehurst.wiremock.client.WireMock

inline fun aResponse(block: ResponseDefinitionBuilder.() -> Unit = {}): ResponseDefinitionBuilder =
    WireMock.aResponse().also(block)

inline fun ok(block: ResponseDefinitionBuilder.() -> Unit = {}): ResponseDefinitionBuilder =
    WireMock.ok().also(block)

inline fun ok(body: String, block: ResponseDefinitionBuilder.() -> Unit = {}): ResponseDefinitionBuilder =
    WireMock.ok(body).also(block)

inline fun okForContentType(
    contentType: String, body: String, block: ResponseDefinitionBuilder.() -> Unit = {}
): ResponseDefinitionBuilder =
    WireMock.okForContentType(contentType, body).also(block)

inline fun okJson(body: String, block: ResponseDefinitionBuilder.() -> Unit = {}): ResponseDefinitionBuilder =
    WireMock.okJson(body).also(block)

inline fun okXml(body: String, block: ResponseDefinitionBuilder.() -> Unit = {}): ResponseDefinitionBuilder =
    WireMock.okXml(body).also(block)

inline fun okTextXml(body: String, block: ResponseDefinitionBuilder.() -> Unit = {}): ResponseDefinitionBuilder =
    WireMock.okTextXml(body).also(block)

inline fun created(block: ResponseDefinitionBuilder.() -> Unit = {}): ResponseDefinitionBuilder =
    WireMock.created().also(block)

inline fun noContent(block: ResponseDefinitionBuilder.() -> Unit = {}): ResponseDefinitionBuilder =
    WireMock.noContent().also(block)

inline fun permanentRedirect(
    location: String, block: ResponseDefinitionBuilder.() -> Unit = {}
): ResponseDefinitionBuilder =
    WireMock.permanentRedirect(location).also(block)

inline fun temporaryRedirect(
    location: String, block: ResponseDefinitionBuilder.() -> Unit = {}
): ResponseDefinitionBuilder =
    WireMock.temporaryRedirect(location).also(block)

inline fun seeOther(
    location: String, block: ResponseDefinitionBuilder.() -> Unit = {}
): ResponseDefinitionBuilder =
    WireMock.seeOther(location).also(block)

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