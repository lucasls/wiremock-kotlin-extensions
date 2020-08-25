package com.github.lucasls.kotlinx.wiremock.experimental

import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder
import com.github.tomakehurst.wiremock.junit.Stubbing
import com.github.tomakehurst.wiremock.stubbing.StubMapping
import com.github.lucasls.kotlinx.wiremock.aResponse
import com.github.lucasls.kotlinx.wiremock.givenThat
import com.github.lucasls.kotlinx.wiremock.request
import com.github.lucasls.kotlinx.wiremock.stubFor
import com.github.lucasls.kotlinx.wiremock.urlEqualTo
import com.github.tomakehurst.wiremock.matching.UrlPattern


class StubbingScope(private val stubFunction: (MappingBuilder) -> StubMapping) {

    inner class MappingBuilderScope(private val mappingBuilder: MappingBuilder) {
        operator fun invoke(block: MappingBuilder.() -> Unit) {
            mappingBuilder.apply(block).also { stubFunction(it) }
        }
    }

    infix fun String.to(url: String) = MappingBuilderScope(request(this, urlEqualTo(url)))
    infix fun String.to(urlPattern: UrlPattern) = MappingBuilderScope(request(this, urlPattern))

}

fun givenThat(block: StubbingScope.() -> Unit) {
    StubbingScope(::givenThat).block()
}

fun stubFor(block: StubbingScope.() -> Unit) {
    StubbingScope(::stubFor).block()
}

fun Stubbing.givenThat(block: StubbingScope.() -> Unit) {
    StubbingScope(this::givenThat).block()
}

fun Stubbing.stubFor(block: StubbingScope.() -> Unit) {
    StubbingScope(this::stubFor).block()
}