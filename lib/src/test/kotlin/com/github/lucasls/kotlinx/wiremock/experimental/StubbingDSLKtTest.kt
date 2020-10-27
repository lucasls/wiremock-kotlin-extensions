package com.github.lucasls.kotlinx.wiremock.experimental

import com.github.lucasls.kotlinx.wiremock.urlPathEqualTo
import com.github.lucasls.kotlinx.wiremock.willReturnAResponse
import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.junit.Stubbing
import com.github.tomakehurst.wiremock.stubbing.StubMapping
import com.nhaarman.mockitokotlin2.check
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import helpers.hasSameFieldsOf
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.test.assertFailsWith

@ExtendWith(MockitoExtension::class)
internal class StubbingDSLKtTest {
    private val stubFunction: (MappingBuilder) -> StubMapping = mock()
    private val stubbingScope = StubbingScope(stubFunction)

    @Test
    fun `String to String should call original and apply block then call stubFunction`() {
        // GIVEN
        val body = "body"

        // WHEN
        with(stubbingScope) {
            ("POST" to "/test/url") {
                willReturnAResponse {
                    withBody(body)
                }
            }
        }

        // THEN
        verify(stubFunction).invoke(
            check { ext ->
                val orig = WireMock.post("/test/url")
                    .willReturn(
                        WireMock.aResponse()
                            .withBody(body)
                    )

                assertThat(ext) hasSameFieldsOf orig
            }
        )
    }

    @Test
    fun `String to UrlPattern should call original and apply block then call stubFunction`() {
        // GIVEN
        val body = "body"

        // WHEN
        with(stubbingScope) {
            ("POST" to urlPathEqualTo("/test/url")) {
                willReturnAResponse {
                    withBody(body)
                }
            }
        }

        // THEN
        verify(stubFunction).invoke(
            check { ext ->
                val orig = WireMock.post(urlPathEqualTo("/test/url"))
                    .willReturn(
                        WireMock.aResponse()
                            .withBody(body)
                    )

                assertThat(ext) hasSameFieldsOf orig
            }
        )
    }

    @Test
    fun `givenThat should call original method and apply builders`() {
        // GIVEN
        val body = "body"
        val stubbing: Stubbing = mock()

        // WHEN
        stubbing.givenThat {
            ("POST" to "/test/url") {
                willReturnAResponse {
                    withBody(body)
                }
            }
        }

        // THEN
        verify(stubbing).givenThat(
            check { ext ->
                val orig = WireMock.post("/test/url")
                    .willReturn(
                        WireMock.aResponse()
                            .withBody(body)
                    )

                assertThat(ext) hasSameFieldsOf orig
            }
        )
    }

    @Test
    fun `static givenThat should apply builders`() {
        // GIVEN
        val body = "body"

        class TestEndedEx : Exception()

        assertFailsWith<TestEndedEx> {
            givenThat {
                // WHEN
                ("POST" to "/test/url") {
                    willReturnAResponse {
                        withBody(body)
                    }

                    // THEN
                    val ext = this

                    val orig = WireMock.post("/test/url")
                        .willReturn(WireMock.aResponse().withBody(body))

                    assertThat(ext) hasSameFieldsOf orig

                    // Necessary to prevent starting the real WireMock client (can't mock a static method)
                    throw TestEndedEx()
                }
            }
        }
    }

    @Test
    fun `stubFor should call original method and apply builders`() {
        // GIVEN
        val body = "body"
        val stubbing: Stubbing = mock()

        // WHEN
        stubbing.stubFor {
            ("POST" to "/test/url") {
                willReturnAResponse {
                    withBody(body)
                }
            }
        }

        // THEN
        verify(stubbing).stubFor(
            check { ext ->
                val orig = WireMock.post("/test/url")
                    .willReturn(WireMock.aResponse().withBody(body))

                assertThat(ext) hasSameFieldsOf orig
            }
        )
    }

    @Test
    fun `static stubFor apply builders`() {
        // GIVEN
        val body = "body"

        class TestEndedEx : Exception()

        assertFailsWith<TestEndedEx> {
            stubFor {
                // WHEN
                ("POST" to "/test/url") {
                    willReturnAResponse {
                        withBody(body)
                    }

                    // THEN
                    val ext = this

                    val orig = WireMock.post("/test/url")
                        .willReturn(WireMock.aResponse().withBody(body))

                    assertThat(ext) hasSameFieldsOf orig

                    // Necessary to prevent starting the real WireMock client (can't mock a static method)
                    throw TestEndedEx()
                }
            }
        }
    }
}