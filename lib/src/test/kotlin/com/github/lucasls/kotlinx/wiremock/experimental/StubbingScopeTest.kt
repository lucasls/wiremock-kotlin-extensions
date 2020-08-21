package com.github.lucasls.kotlinx.wiremock.experimental

import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.stubbing.StubMapping
import com.github.lucasls.kotlinx.wiremock.experimental.StubbingScope
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import helpers.hasSameFieldsOf
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
internal class StubbingScopeTest {
    private val stubFunction: (MappingBuilder) -> StubMapping = mock()
    private val stubbingScope = StubbingScope(stubFunction)

    @Test
    fun `willReturnAResponse should call original and apply block then call stubFunction`() {
        // GIVEN
        val body = "body"
        val mappingBuilder: MappingBuilder = mock {
            on { willReturn(any()) }.then { it.mock }
        }

        // WHEN
        with(stubbingScope) {
            mappingBuilder willReturnAResponse {
                withBody(body)
            }
        }

        // THEN
        val ext = argumentCaptor<ResponseDefinitionBuilder> {
            verify(mappingBuilder).willReturn(capture())
        }.firstValue

        verify(stubFunction).invoke(mappingBuilder)

        val orig = WireMock.aResponse()
            .withBody(body)

        assertThat(ext) hasSameFieldsOf orig
    }
}