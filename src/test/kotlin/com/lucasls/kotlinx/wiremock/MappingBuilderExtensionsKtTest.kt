package com.lucasls.kotlinx.wiremock

import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder
import com.github.tomakehurst.wiremock.client.ScenarioMappingBuilder
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.extension.Parameters
import com.github.tomakehurst.wiremock.matching.MultipartValuePatternBuilder
import com.github.tomakehurst.wiremock.matching.RequestMatcherExtension
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import helpers.hasSameFieldsOf
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import com.nhaarman.mockitokotlin2.any as mockito_any

@ExtendWith(MockitoExtension::class)
internal class MappingBuilderExtensionsKtTest {

    @TestFactory
    fun `extension with block should match original`(): List<DynamicTest> {
        // GIVEN
        val urlPattern = WireMock.anyUrl()
        val url = "/test/url"
        val reqMatcher = "req matcher"
        val parameters = Parameters.empty()
        val reqMatcherExt = RequestMatcherExtension.ALWAYS

        val block: MappingBuilder.() -> Unit = {
            withQueryParam("q", WireMock.equalTo("test"))
            withHeader("h", WireMock.equalTo("test"))
        }

        return listOf<Triple<String, () -> MappingBuilder, () -> MappingBuilder>>(
            Triple("get UrlPattern", { WireMock.get(urlPattern) }, { get(urlPattern, block) }),
            Triple("post UrlPattern", { WireMock.post(urlPattern) }, { post(urlPattern, block) }),
            Triple("put UrlPattern", { WireMock.put(urlPattern) }, { put(urlPattern, block) }),
            Triple("delete UrlPattern", { WireMock.delete(urlPattern) }, { delete(urlPattern, block) }),
            Triple("patch UrlPattern", { WireMock.patch(urlPattern) }, { patch(urlPattern, block) }),
            Triple("head UrlPattern", { WireMock.head(urlPattern) }, { head(urlPattern, block) }),
            Triple("options UrlPattern", { WireMock.options(urlPattern) }, { options(urlPattern, block) }),
            Triple("trace UrlPattern", { WireMock.trace(urlPattern) }, { trace(urlPattern, block) }),
            Triple("any UrlPattern", { WireMock.any(urlPattern) }, { any(urlPattern, block) }),

            Triple("get String", { WireMock.get(url) }, { get(url, block) }),
            Triple("post String", { WireMock.post(url) }, { post(url, block) }),
            Triple("put String", { WireMock.put(url) }, { put(url, block) }),
            Triple("delete String", { WireMock.delete(url) }, { delete(url, block) }),
            Triple("proxyAllTo String", { WireMock.proxyAllTo(url) }, { proxyAllTo(url, block) }),

            Triple(
                "request UrlPattern", { WireMock.request("GET", urlPattern) }, { request("GET", urlPattern, block) }),
            Triple(
                "requestMatching String",
                { WireMock.requestMatching(reqMatcher) }, { requestMatching(reqMatcher, block) }),
            Triple(
                "requestMatching String Parameters",
                { WireMock.requestMatching(reqMatcher, parameters) },
                { requestMatching(reqMatcher, parameters, block) }),
            Triple(
                "requestMatching ValueMatcher",
                { WireMock.requestMatching(reqMatcherExt) }, { requestMatching(reqMatcherExt, block) })

        ).map { (name, originalFactory, extensionFactory) ->
            dynamicTest(name) {
                // WHEN
                val orig = originalFactory().also(block)
                val ext = extensionFactory()

                // THEN
                assertThat(ext) hasSameFieldsOf orig
            }
        }
    }

    @TestFactory
    fun `extension without block should match original`(): List<DynamicTest> {
        // GIVEN
        val urlPattern = WireMock.anyUrl()
        val url = "/test/url"
        val reqMatcher = "req matcher"
        val parameters = Parameters.empty()
        val reqMatcherExt = RequestMatcherExtension.ALWAYS

        return listOf<Triple<String, () -> MappingBuilder, () -> MappingBuilder>>(
            Triple("get UrlPattern", { WireMock.get(urlPattern) }, { get(urlPattern) }),
            Triple("post UrlPattern", { WireMock.post(urlPattern) }, { post(urlPattern) }),
            Triple("put UrlPattern", { WireMock.put(urlPattern) }, { put(urlPattern) }),
            Triple("delete UrlPattern", { WireMock.delete(urlPattern) }, { delete(urlPattern) }),
            Triple("patch UrlPattern", { WireMock.patch(urlPattern) }, { patch(urlPattern) }),
            Triple("head UrlPattern", { WireMock.head(urlPattern) }, { head(urlPattern) }),
            Triple("options UrlPattern", { WireMock.options(urlPattern) }, { options(urlPattern) }),
            Triple("trace UrlPattern", { WireMock.trace(urlPattern) }, { trace(urlPattern) }),
            Triple("any UrlPattern", { WireMock.any(urlPattern) }, { any(urlPattern) }),
            Triple("get String", { WireMock.get(url) }, { get(url) }),

            Triple("post String", { WireMock.post(url) }, { post(url) }),
            Triple("put String", { WireMock.put(url) }, { put(url) }),
            Triple("delete String", { WireMock.delete(url) }, { delete(url) }),
            Triple("proxyAllTo String", { WireMock.proxyAllTo(url) }, { proxyAllTo(url) }),

            Triple("request UrlPattern", { WireMock.request("GET", urlPattern) }, { request("GET", urlPattern) }),

            Triple(
                "requestMatching String",
                { WireMock.requestMatching(reqMatcher) }, { requestMatching(reqMatcher) }),
            Triple(
                "requestMatching String Parameters",
                { WireMock.requestMatching(reqMatcher, parameters) },
                { requestMatching(reqMatcher, parameters) }),
            Triple(
                "requestMatching ValueMatcher",
                { WireMock.requestMatching(reqMatcherExt) }, { requestMatching(reqMatcherExt) })

        ).map { (name, originalFactory, extensionFactory) ->
            dynamicTest(name) {
                // WHEN
                val orig = originalFactory()
                val ext = extensionFactory()

                // THEN
                assertThat(ext) hasSameFieldsOf orig
            }
        }
    }

    @Test
    fun `inScenario should call original and apply block`() {
        // GIVEN
        val scenarioName = "scenarioName"
        val stateName = "stateName"

        val scenarioMappingBuilder: ScenarioMappingBuilder = mock {
            on { willSetStateTo(stateName) }.then { it.mock }
        }
        val mappingBuilder: MappingBuilder = mock {
            on { inScenario(scenarioName) }.thenReturn(scenarioMappingBuilder)
        }

        // WHEN
        val result = mappingBuilder.inScenario(scenarioName) {
            willSetStateTo(stateName)
        }

        // THEN
        assertThat(result).isSameAs(scenarioMappingBuilder)
    }

    @Test
    fun `withMultipartRequestBody should call original and apply block`() {
        // GIVEN
        val name = "name"

        val mappingBuilder: MappingBuilder = mock {
            on { withMultipartRequestBody(mockito_any()) }.then { it.mock }
        }

        // WHEN
        mappingBuilder.withMultipartRequestBody {
            withName(name)
        }

        // THEN
        val ext = argumentCaptor<MultipartValuePatternBuilder> {
            verify(mappingBuilder).withMultipartRequestBody(capture())
        }.firstValue

        val orig = WireMock.aMultipart()
            .withName(name)

        assertThat(ext) hasSameFieldsOf orig
    }

    @Test
    fun `willReturn should call original and apply block`() {
        // GIVEN
        val body = "body"

        val mappingBuilder: MappingBuilder = mock {
            on { willReturn(mockito_any()) }.then { it.mock }
        }

        // WHEN
        mappingBuilder.willReturnAResponse {
            withBody(body)
        }

        // THEN
        val ext = argumentCaptor<ResponseDefinitionBuilder> {
            verify(mappingBuilder).willReturn(capture())
        }.firstValue

        val orig = WireMock.aResponse()
            .withBody(body)

        assertThat(ext) hasSameFieldsOf orig
    }
}