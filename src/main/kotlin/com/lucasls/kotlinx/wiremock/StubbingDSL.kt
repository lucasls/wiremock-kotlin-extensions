@file:Suppress("unused")
package com.lucasls.kotlinx.wiremock

import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder
import com.github.tomakehurst.wiremock.junit.Stubbing
import com.github.tomakehurst.wiremock.stubbing.StubMapping


class StubbingScope(private val stubFunction: (MappingBuilder) -> StubMapping) {
    infix fun MappingBuilder.willReturn(responseDefBuilder: ResponseDefinitionBuilder): MappingBuilder =
        this.willReturn(responseDefBuilder).also { stubFunction(it) }

    infix fun MappingBuilder.willReturn(block: ResponseDefinitionBuilder.() -> Unit): MappingBuilder =
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