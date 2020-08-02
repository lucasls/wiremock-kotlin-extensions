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

    data class OriginalExtension<T>(
        val name: String,
        val original: (T) -> MappingBuilder,
        val extension: (T, MappingBuilder.() -> Unit) -> MappingBuilder
    )

    data class OriginalExtensionNoBuilder<T>(
        val name: String,
        val original: (T) -> MappingBuilder,
        val extension: (T) -> MappingBuilder
    )

    fun `extension should match original with UrlPattern`(
        origFactory: (urlPattern: UrlPattern) -> MappingBuilder,
        extFactory: (urlPattern: UrlPattern, MappingBuilder.() -> Unit) -> MappingBuilder
    ) {
        val orig = origFactory(WireMock.anyUrl())
            .withQueryParam("q", WireMock.equalTo("test"))
            .withHeader("h", WireMock.equalTo("test"))

        val ext = extFactory(WireMock.anyUrl()) {
            withQueryParam("q", WireMock.equalTo("test"))
            withHeader("h", WireMock.equalTo("test"))
        }

        assertThat(ext) hasSameFieldsOf orig
    }

    @TestFactory
    fun `extension should match original with UrlPattern`() = listOf(
        dynamicTest("get(UrlPattern)") {
            `extension should match original with UrlPattern`(WireMock::get, ::get)
        },
        dynamicTest("post(UrlPattern)") {
            `extension should match original with UrlPattern`(WireMock::post, ::post)
        },
        dynamicTest("put(UrlPattern)") {
            `extension should match original with UrlPattern`(WireMock::put, ::put)
        },
        dynamicTest("delete(UrlPattern)") {
            `extension should match original with UrlPattern`(WireMock::delete, ::delete)
        },
        dynamicTest("patch(UrlPattern)") {
            `extension should match original with UrlPattern`(WireMock::patch, ::patch)
        },
        dynamicTest("head(UrlPattern)") {
            `extension should match original with UrlPattern`(WireMock::head, ::head)
        },
        dynamicTest("options(UrlPattern)") {
            `extension should match original with UrlPattern`(WireMock::options, ::options)
        },
        dynamicTest("trace(UrlPattern)") {
            `extension should match original with UrlPattern`(WireMock::trace, ::trace)
        },
        dynamicTest("any(UrlPattern)") {
            `extension should match original with UrlPattern`(WireMock::any, ::any)
        }
    )

    @Test
    fun `request extension should match original with UrlPattern`() {
        val orig = WireMock.request("GET", WireMock.anyUrl())
            .withQueryParam("q", WireMock.equalTo("test"))
            .withHeader("h", WireMock.equalTo("test"))

        val ext = request("GET", WireMock.anyUrl()) {
            withQueryParam("q", WireMock.equalTo("test"))
            withHeader("h", WireMock.equalTo("test"))
        }

        assertThat(ext) hasSameFieldsOf orig
    }

    @TestFactory
    fun `extension should match original with UrlPattern with no builder`() =
        listOf<OriginalExtensionNoBuilder<UrlPattern>>(
            OriginalExtensionNoBuilder("get", WireMock::get, { get(it) }),
            OriginalExtensionNoBuilder("post", WireMock::post, { post(it) }),
            OriginalExtensionNoBuilder("put", WireMock::put, { put(it) }),
            OriginalExtensionNoBuilder("delete", WireMock::delete, { delete(it) }),
            OriginalExtensionNoBuilder("patch", WireMock::patch, { patch(it) }),
            OriginalExtensionNoBuilder("head", WireMock::head, { head(it) }),
            OriginalExtensionNoBuilder("options", WireMock::options, { options(it) }),
            OriginalExtensionNoBuilder("trace", WireMock::trace, { trace(it) }),
            OriginalExtensionNoBuilder("any", WireMock::any, { any(it) }),
            OriginalExtensionNoBuilder("request", { WireMock.request("GET", it) }, { request("GET", it) })
        ).map { (name, original, extension) ->
            dynamicTest("$name(UrlPattern)") {
                val orig = original(WireMock.anyUrl())
                val ext = extension(WireMock.anyUrl())
                assertThat(ext) hasSameFieldsOf orig
            }
        }

    @TestFactory
    fun `extension should match original with String`() = listOf<OriginalExtension<String>>(
        OriginalExtension("get", WireMock::get, ::get),
        OriginalExtension("post", WireMock::post, ::post),
        OriginalExtension("put", WireMock::put, ::put),
        OriginalExtension("delete", WireMock::delete, ::delete)
    ).map { (name, original, extension) ->
        dynamicTest("$name(String)") {
            val orig = original("/test")
                .withQueryParam("q", WireMock.equalTo("test"))
                .withHeader("h", WireMock.equalTo("test"))

            val ext = extension("/test") {
                withQueryParam("q", WireMock.equalTo("test"))
                withHeader("h", WireMock.equalTo("test"))
            }

            assertThat(ext) hasSameFieldsOf orig
        }
    }

    @TestFactory
    fun `extension should match original with String with no builder`() = listOf<OriginalExtensionNoBuilder<String>>(
        OriginalExtensionNoBuilder("get", WireMock::get, { get(it) }),
        OriginalExtensionNoBuilder("post", WireMock::post, { post(it) }),
        OriginalExtensionNoBuilder("put", WireMock::put, { put(it) }),
        OriginalExtensionNoBuilder("delete", WireMock::delete, { delete(it) })
    ).map { (name, original, extension) ->
        dynamicTest("$name(String)") {
            val orig = original("/test")
            val ext = extension("/test")
            assertThat(ext) hasSameFieldsOf orig
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
}