package com.lucasls.kotlinx.wiremock

import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.extension.Parameters
import com.github.tomakehurst.wiremock.matching.MultipartValuePatternBuilder
import com.github.tomakehurst.wiremock.matching.RequestMatcherExtension
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import helpers.hasSameFieldsOf
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

internal class RequestPatternBuilderExtensionsKtTest {
    @TestFactory
    fun `extension with block should match original`(): List<DynamicTest> {
        // GIVEN
        val urlPattern = WireMock.anyUrl()
        val reqMatcher = "req matcher"
        val parameters = Parameters.empty()
        val reqMatcherExt = RequestMatcherExtension.ALWAYS

        val block: RequestPatternBuilder.() -> Unit = {
            withQueryParam("q", WireMock.equalTo("test"))
            withHeader("h", WireMock.equalTo("test"))
        }

        return listOf<Triple<String, () -> RequestPatternBuilder, () -> RequestPatternBuilder>>(
            Triple("getRequestedFor",
                { WireMock.getRequestedFor(urlPattern) }, { getRequestedFor(urlPattern, block) }),
            Triple("postRequestedFor",
                { WireMock.postRequestedFor(urlPattern) }, { postRequestedFor(urlPattern, block) }),
            Triple("putRequestedFor",
                { WireMock.putRequestedFor(urlPattern) }, { putRequestedFor(urlPattern, block) }),
            Triple("deleteRequestedFor",
                { WireMock.deleteRequestedFor(urlPattern) }, { deleteRequestedFor(urlPattern, block) }),
            Triple("patchRequestedFor",
                { WireMock.patchRequestedFor(urlPattern) }, { patchRequestedFor(urlPattern, block) }),
            Triple("headRequestedFor",
                { WireMock.headRequestedFor(urlPattern) }, { headRequestedFor(urlPattern, block) }),
            Triple("optionsRequestedFor",
                { WireMock.optionsRequestedFor(urlPattern) }, { optionsRequestedFor(urlPattern, block) }),
            Triple("traceRequestedFor",
                { WireMock.traceRequestedFor(urlPattern) }, { traceRequestedFor(urlPattern, block) }),
            Triple("anyRequestedFor",
                { WireMock.anyRequestedFor(urlPattern) }, { anyRequestedFor(urlPattern, block) }),
            Triple("requestMadeFor",
                { WireMock.requestMadeFor(reqMatcher, parameters) },
                { requestMadeFor(reqMatcher, parameters, block) }),
            Triple("requestMadeFor",
                { WireMock.requestMadeFor(reqMatcherExt) }, { requestMadeFor(reqMatcherExt, block) })

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
        val urlPattern = WireMock.anyUrl()
        val reqMatcher = "req matcher"
        val parameters = Parameters.empty()
        val reqMatcherExt = RequestMatcherExtension.ALWAYS

        return listOf<Triple<String, () -> RequestPatternBuilder, () -> RequestPatternBuilder>>(
            Triple("getRequestedFor",
                { WireMock.getRequestedFor(urlPattern) }, { getRequestedFor(urlPattern) }),
            Triple("postRequestedFor",
                { WireMock.postRequestedFor(urlPattern) }, { postRequestedFor(urlPattern) }),
            Triple("putRequestedFor",
                { WireMock.putRequestedFor(urlPattern) }, { putRequestedFor(urlPattern) }),
            Triple("deleteRequestedFor",
                { WireMock.deleteRequestedFor(urlPattern) }, { deleteRequestedFor(urlPattern) }),
            Triple("patchRequestedFor",
                { WireMock.patchRequestedFor(urlPattern) }, { patchRequestedFor(urlPattern) }),
            Triple("headRequestedFor",
                { WireMock.headRequestedFor(urlPattern) }, { headRequestedFor(urlPattern) }),
            Triple("optionsRequestedFor",
                { WireMock.optionsRequestedFor(urlPattern) }, { optionsRequestedFor(urlPattern) }),
            Triple("traceRequestedFor",
                { WireMock.traceRequestedFor(urlPattern) }, { traceRequestedFor(urlPattern) }),
            Triple("anyRequestedFor",
                { WireMock.anyRequestedFor(urlPattern) }, { anyRequestedFor(urlPattern) }),
            Triple("requestMadeFor",
                { WireMock.requestMadeFor(reqMatcher, parameters) },
                { requestMadeFor(reqMatcher, parameters) }),
            Triple("requestMadeFor",
                { WireMock.requestMadeFor(reqMatcherExt) }, { requestMadeFor(reqMatcherExt) })

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


    @Test
    fun `withAnyRequestBodyPart should call original and apply block`() {
        // GIVEN
        val name = "name"

        val requestPatternBuilder: RequestPatternBuilder = mock {
            on { withAnyRequestBodyPart(any()) }.then { it.mock }
        }

        // WHEN
        requestPatternBuilder.withAnyRequestBodyPart {
            withName(name)
        }

        // THEN
        val ext = argumentCaptor<MultipartValuePatternBuilder> {
            verify(requestPatternBuilder).withAnyRequestBodyPart(capture())
        }.firstValue

        val orig = WireMock.aMultipart()
            .withName(name)

        Assertions.assertThat(ext) hasSameFieldsOf orig
    }


    @Test
    fun `withAllRequestBodyParts should call original and apply block`() {
        // GIVEN
        val name = "name"

        val requestPatternBuilder: RequestPatternBuilder = mock {
            on { withAllRequestBodyParts(any()) }.then { it.mock }
        }

        // WHEN
        requestPatternBuilder.withAllRequestBodyParts {
            withName(name)
        }

        // THEN
        val ext = argumentCaptor<MultipartValuePatternBuilder> {
            verify(requestPatternBuilder).withAllRequestBodyParts(capture())
        }.firstValue

        val orig = WireMock.aMultipart()
            .withName(name)

        Assertions.assertThat(ext) hasSameFieldsOf orig
    }
}