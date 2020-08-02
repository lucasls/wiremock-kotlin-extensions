package com.lucasls.kotlinx.wiremock

import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.ScenarioMappingBuilder
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.matching.MultipartValuePatternBuilder
import com.github.tomakehurst.wiremock.matching.UrlPattern
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import helpers.hasSameFieldsOf
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import com.nhaarman.mockitokotlin2.any as mockito_any

@ExtendWith(MockitoExtension::class)
internal class MappingBuilderExtensionsKtTest {

    private fun `dynamicTest - extension should match original with UrlPattern`(
        testName: String,
        origFunction: (UrlPattern) -> MappingBuilder,
        extFunction: (UrlPattern, MappingBuilder.() -> Unit) -> MappingBuilder
    ) = dynamicTest(testName) {
        val orig = origFunction(WireMock.anyUrl())
            .withQueryParam("q", WireMock.equalTo("test"))
            .withHeader("h", WireMock.equalTo("test"))

        val ext = extFunction(WireMock.anyUrl()) {
            withQueryParam("q", WireMock.equalTo("test"))
            withHeader("h", WireMock.equalTo("test"))
        }

        assertThat(ext) hasSameFieldsOf orig
    }

    @TestFactory
    fun `extension should match original with UrlPattern`() = listOf(
        `dynamicTest - extension should match original with UrlPattern`(
            "get", { WireMock.get(it) }, { p, b -> get(p, b) }),
        `dynamicTest - extension should match original with UrlPattern`(
            "post", { WireMock.post(it) }, { p, b -> post(p, b) }),
        `dynamicTest - extension should match original with UrlPattern`(
            "put", { WireMock.put(it) }, { p, b -> put(p, b) }),
        `dynamicTest - extension should match original with UrlPattern`(
            "delete", { WireMock.delete(it) }, { p, b -> delete(p, b) }),
        `dynamicTest - extension should match original with UrlPattern`(
            "patch", { WireMock.patch(it) }, { p, b -> patch(p, b) }),
        `dynamicTest - extension should match original with UrlPattern`(
            "head", { WireMock.head(it) }, { p, b -> head(p, b) }),
        `dynamicTest - extension should match original with UrlPattern`(
            "options", { WireMock.options(it) }, { p, b -> options(p, b) }),
        `dynamicTest - extension should match original with UrlPattern`(
            "trace", { WireMock.trace(it) }, { p, b -> trace(p, b) }),
        `dynamicTest - extension should match original with UrlPattern`(
            "any", { WireMock.any(it) }, { p, b -> any(p, b) }),
        `dynamicTest - extension should match original with UrlPattern`(
            "request", { WireMock.request("GET", it) }, { p, b -> request("GET", p, b) }
        )
    )

    private fun `dynamicTest - extension should match original with UrlPattern with no builder`(
        testName: String, origFunction: (UrlPattern) -> MappingBuilder, extFunction: (UrlPattern) -> MappingBuilder
    ) = dynamicTest(testName) {
        val orig = origFunction(WireMock.anyUrl())
        val ext = extFunction(WireMock.anyUrl())
        assertThat(ext) hasSameFieldsOf orig
    }

    @TestFactory
    fun `extension should match original with UrlPattern with no builder`() = listOf(
        `dynamicTest - extension should match original with UrlPattern with no builder`(
            "get", { WireMock.get(it) }, { get(it) }
        ),
        `dynamicTest - extension should match original with UrlPattern with no builder`(
            "post", { WireMock.post(it) }, { post(it) }
        ),
        `dynamicTest - extension should match original with UrlPattern with no builder`(
            "put", { WireMock.put(it) }, { put(it) }
        ),
        `dynamicTest - extension should match original with UrlPattern with no builder`(
            "delete", { WireMock.delete(it) }, { delete(it) }
        ),
        `dynamicTest - extension should match original with UrlPattern with no builder`(
            "patch", { WireMock.patch(it) }, { patch(it) }
        ),
        `dynamicTest - extension should match original with UrlPattern with no builder`(
            "head", { WireMock.head(it) }, { head(it) }
        ),
        `dynamicTest - extension should match original with UrlPattern with no builder`(
            "options", { WireMock.options(it) }, { options(it) }
        ),
        `dynamicTest - extension should match original with UrlPattern with no builder`(
            "trace", { WireMock.trace(it) }, { trace(it) }
        ),
        `dynamicTest - extension should match original with UrlPattern with no builder`(
            "any", { WireMock.any(it) }, { any(it) }
        ),
        `dynamicTest - extension should match original with UrlPattern with no builder`(
            "request", { WireMock.request("GET", it) }, { request("GET", it) }
        )
    )

    private fun `dynamicTest - extension should match original with String`(
        testName: String,
        origFunction: (String) -> MappingBuilder,
        extFunction: (String, MappingBuilder.() -> Unit) -> MappingBuilder
    ) = dynamicTest(testName) {
        val orig = origFunction("/test")
            .withQueryParam("q", WireMock.equalTo("test"))
            .withHeader("h", WireMock.equalTo("test"))

        val ext = extFunction("/test") {
            withQueryParam("q", WireMock.equalTo("test"))
            withHeader("h", WireMock.equalTo("test"))
        }

        assertThat(ext) hasSameFieldsOf orig
    }

    @TestFactory
    fun `extension should match original with String`() = listOf(
        `dynamicTest - extension should match original with String`(
            "get", { WireMock.get(it) }, { u, b -> get(u, b) }),
        `dynamicTest - extension should match original with String`(
            "post", { WireMock.post(it) }, { u, b -> post(u, b) }
        ),
        `dynamicTest - extension should match original with String`(
            "put", { WireMock.put(it) }, { u, b -> put(u, b) }
        ),
        `dynamicTest - extension should match original with String`(
            "delete", { WireMock.delete(it) }, { u, b -> delete(u, b) }
        ),
        `dynamicTest - extension should match original with String`(
            "requestMatching", { WireMock.requestMatching(it) }, { u, b -> requestMatching(u, b) }
        )
    )

    private fun `dynamicTest - extension should match original with String with no builder`(
        testName: String,
        origFunction: (String) -> MappingBuilder,
        extFunction: (String) -> MappingBuilder
    ) = dynamicTest(testName) {
        val orig = origFunction("/test")
        val ext = extFunction("/test")
        assertThat(ext) hasSameFieldsOf orig
    }

    @TestFactory
    fun `extension should match original with String with no builder`() = listOf(
        `dynamicTest - extension should match original with String with no builder`(
            "get", { WireMock.get(it) }, { get(it) }
        ),
        `dynamicTest - extension should match original with String with no builder`(
            "post", { WireMock.post(it) }, { post(it) }
        ),
        `dynamicTest - extension should match original with String with no builder`(
            "put", { WireMock.put(it) }, { put(it) }
        ),
        `dynamicTest - extension should match original with String with no builder`(
            "delete", { WireMock.delete(it) }, { delete(it) }
        ),
        `dynamicTest - extension should match original with String with no builder`(
            "requestMatching", { WireMock.requestMatching(it) }, { requestMatching(it) }
        )
    )

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
}