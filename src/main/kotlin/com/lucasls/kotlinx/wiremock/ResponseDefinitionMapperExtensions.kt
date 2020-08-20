package com.lucasls.kotlinx.wiremock

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder
import com.github.tomakehurst.wiremock.client.WireMock

fun aResponse(block: ResponseDefinitionBuilder.() -> Unit = {}): ResponseDefinitionBuilder =
    WireMock.aResponse().also(block)

fun ok(block: ResponseDefinitionBuilder.() -> Unit = {}): ResponseDefinitionBuilder =
    WireMock.ok().also(block)

fun ok(body: String, block: ResponseDefinitionBuilder.() -> Unit = {}): ResponseDefinitionBuilder =
    WireMock.ok(body).also(block)

fun okForContentType(
    contentType: String, body: String, block: ResponseDefinitionBuilder.() -> Unit = {}
): ResponseDefinitionBuilder =
    WireMock.okForContentType(contentType, body).also(block)

fun okJson(body: String, block: ResponseDefinitionBuilder.() -> Unit = {}): ResponseDefinitionBuilder =
    WireMock.okJson(body).also(block)

fun okXml(body: String, block: ResponseDefinitionBuilder.() -> Unit = {}): ResponseDefinitionBuilder =
    WireMock.okXml(body).also(block)

fun okTextXml(body: String, block: ResponseDefinitionBuilder.() -> Unit = {}): ResponseDefinitionBuilder =
    WireMock.okTextXml(body).also(block)

fun created(block: ResponseDefinitionBuilder.() -> Unit = {}): ResponseDefinitionBuilder =
    WireMock.created().also(block)

fun noContent(block: ResponseDefinitionBuilder.() -> Unit = {}): ResponseDefinitionBuilder =
    WireMock.noContent().also(block)

fun permanentRedirect(
    location: String, block: ResponseDefinitionBuilder.() -> Unit = {}
): ResponseDefinitionBuilder =
    WireMock.permanentRedirect(location).also(block)

fun temporaryRedirect(
    location: String, block: ResponseDefinitionBuilder.() -> Unit = {}
): ResponseDefinitionBuilder =
    WireMock.temporaryRedirect(location).also(block)

fun seeOther(
    location: String, block: ResponseDefinitionBuilder.() -> Unit = {}
): ResponseDefinitionBuilder =
    WireMock.seeOther(location).also(block)

fun badRequest(block: ResponseDefinitionBuilder.() -> Unit = {}): ResponseDefinitionBuilder =
    WireMock.badRequest().also(block)

fun badRequestEntity(block: ResponseDefinitionBuilder.() -> Unit = {}): ResponseDefinitionBuilder =
    WireMock.badRequestEntity().also(block)

fun unauthorized(block: ResponseDefinitionBuilder.() -> Unit = {}): ResponseDefinitionBuilder =
    WireMock.unauthorized().also(block)

fun forbidden(block: ResponseDefinitionBuilder.() -> Unit = {}): ResponseDefinitionBuilder =
    WireMock.forbidden().also(block)

fun notFound(block: ResponseDefinitionBuilder.() -> Unit = {}): ResponseDefinitionBuilder =
    WireMock.notFound().also(block)

fun serverError(block: ResponseDefinitionBuilder.() -> Unit = {}): ResponseDefinitionBuilder =
    WireMock.serverError().also(block)

fun serviceUnavailable(block: ResponseDefinitionBuilder.() -> Unit = {}): ResponseDefinitionBuilder =
    WireMock.serviceUnavailable().also(block)

fun status(status: Int, block: ResponseDefinitionBuilder.() -> Unit = {}): ResponseDefinitionBuilder =
    WireMock.status(status).also(block)