package com.github.lucasls.kotlinx.wiremock

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder
import com.github.tomakehurst.wiremock.client.WireMock
import helpers.hasSameFieldsOf
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
internal class ResponseDefinitionMapperExtensionsKtTest {
    @TestFactory
    fun `extension with block should match original`(): List<DynamicTest> {
        // GIVEN
        val body = "body"
        val contentType = "contentType"
        val location = "foo.com"
        val status = 404

        val block: ResponseDefinitionBuilder.() -> Unit = {
            withBody("body")
            withHeader("key", "value")
        }

        return listOf<Triple<String, () -> ResponseDefinitionBuilder, () -> ResponseDefinitionBuilder>>(
            Triple("aResponse", { WireMock.aResponse() }, { aResponse(block) }),
            Triple("ok", { WireMock.ok() }, { ok(block) }),
            Triple("ok", { WireMock.ok(body) }, { ok(body, block) }),
            Triple("okForContentType", { WireMock.okForContentType(contentType, body) },
                { okForContentType(contentType, body, block) }),
            Triple("okJson", { WireMock.okJson(body) }, { okJson(body, block) }),
            Triple("okXml", { WireMock.okXml(body) }, { okXml(body, block) }),
            Triple("okTextXml", { WireMock.okTextXml(body) }, { okTextXml(body, block) }),
            Triple("created", { WireMock.created() }, { created(block) }),
            Triple("noContent", { WireMock.noContent() }, { noContent(block) }),
            Triple("permanentRedirect", { WireMock.permanentRedirect(location) },
                { permanentRedirect(location, block) }),
            Triple("temporaryRedirect", { WireMock.temporaryRedirect(location) },
                { temporaryRedirect(location, block) }),
            Triple("seeOther", { WireMock.seeOther(location) }, { seeOther(location, block) }),
            Triple("badRequest", { WireMock.badRequest() }, { badRequest(block) }),
            Triple("badRequestEntity", { WireMock.badRequestEntity() }, { badRequestEntity(block) }),
            Triple("unauthorized", { WireMock.unauthorized() }, { unauthorized(block) }),
            Triple("forbidden", { WireMock.forbidden() }, { forbidden(block) }),
            Triple("notFound", { WireMock.notFound() }, { notFound(block) }),
            Triple("serverError", { WireMock.serverError() }, { serverError(block) }),
            Triple("serviceUnavailable", { WireMock.serviceUnavailable() }, { serviceUnavailable(block) }),
            Triple("status", { WireMock.status(status) }, {
                status(
                    status,
                    block
                )
            })

        ).map { (name, originalFactory, extensionFactory) ->
            DynamicTest.dynamicTest(name) {
                // WHEN
                val orig = originalFactory().also(block)
                val ext = extensionFactory()

                // THEN
                Assertions.assertThat(ext) hasSameFieldsOf orig
            }
        }
    }

    @TestFactory
    fun `extension without block should match original`(): List<DynamicTest> {
        // GIVEN
        val body = "body"
        val contentType = "contentType"
        val location = "foo.com"
        val status = 404

        return listOf<Triple<String, () -> ResponseDefinitionBuilder, () -> ResponseDefinitionBuilder>>(
            Triple("aResponse", { WireMock.aResponse() }, { aResponse() }),
            Triple("ok", { WireMock.ok() }, { ok() }),
            Triple("ok", { WireMock.ok(body) }, { ok(body) }),
            Triple("okForContentType", { WireMock.okForContentType(contentType, body) },
                { okForContentType(contentType, body) }),
            Triple("okJson", { WireMock.okJson(body) }, { okJson(body) }),
            Triple("okXml", { WireMock.okXml(body) }, { okXml(body) }),
            Triple("okTextXml", { WireMock.okTextXml(body) }, { okTextXml(body) }),
            Triple("created", { WireMock.created() }, { created() }),
            Triple("noContent", { WireMock.noContent() }, { noContent() }),
            Triple("permanentRedirect", { WireMock.permanentRedirect(location) },
                { permanentRedirect(location) }),
            Triple("temporaryRedirect", { WireMock.temporaryRedirect(location) },
                { temporaryRedirect(location) }),
            Triple("seeOther", { WireMock.seeOther(location) }, { seeOther(location) }),
            Triple("badRequest", { WireMock.badRequest() }, { badRequest() }),
            Triple("badRequestEntity", { WireMock.badRequestEntity() }, { badRequestEntity() }),
            Triple("unauthorized", { WireMock.unauthorized() }, { unauthorized() }),
            Triple("forbidden", { WireMock.forbidden() }, { forbidden() }),
            Triple("notFound", { WireMock.notFound() }, { notFound() }),
            Triple("serverError", { WireMock.serverError() }, { serverError() }),
            Triple("serviceUnavailable", { WireMock.serviceUnavailable() }, { serviceUnavailable() }),
            Triple("status", { WireMock.status(status) }, {
                status(
                    status
                )
            })

        ).map { (name, originalFactory, extensionFactory) ->
            DynamicTest.dynamicTest(name) {
                // WHEN
                val orig = originalFactory()
                val ext = extensionFactory()

                // THEN
                Assertions.assertThat(ext) hasSameFieldsOf orig
            }
        }
    }
}