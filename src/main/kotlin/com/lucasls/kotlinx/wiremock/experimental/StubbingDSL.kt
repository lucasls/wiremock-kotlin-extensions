package com.lucasls.kotlinx.wiremock.experimental

import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder
import com.github.tomakehurst.wiremock.junit.Stubbing
import com.github.tomakehurst.wiremock.stubbing.StubMapping
import com.lucasls.kotlinx.wiremock.aResponse
import com.lucasls.kotlinx.wiremock.givenThat
import com.lucasls.kotlinx.wiremock.stubFor


class StubbingScope(private val stubFunction: (MappingBuilder) -> StubMapping) {
    infix fun MappingBuilder.willReturnAResponse(block: ResponseDefinitionBuilder.() -> Unit): MappingBuilder =
        this.willReturn(aResponse(block)).also { stubFunction.invoke(it) }
}

inline fun givenThat(block: StubbingScope.() -> Unit) {
    StubbingScope(::givenThat).block()
}

inline fun stubFor(block: StubbingScope.() -> Unit) {
    StubbingScope(::stubFor).block()
}

inline fun Stubbing.givenThat(block: StubbingScope.() -> Unit) {
    StubbingScope(this::givenThat).block()
}

inline fun Stubbing.stubFor(block: StubbingScope.() -> Unit) {
    StubbingScope(this::stubFor).block()
}