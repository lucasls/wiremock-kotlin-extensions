package com.github.lucasls.kotlinx.wiremock

import com.github.tomakehurst.wiremock.client.CountMatchingStrategy
import com.github.tomakehurst.wiremock.junit.Stubbing
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder

object GlobalVerificationScope {
    operator fun Int.times(requestPatternBuilder: RequestPatternBuilder) {
        verify(this, requestPatternBuilder)
    }

    operator fun CountMatchingStrategy.times(requestPatternBuilder: RequestPatternBuilder) {
        verify(this, requestPatternBuilder)
    }
}

class StubbingVerificationScope(private val stubbing: Stubbing) {
    operator fun Int.times(requestPatternBuilder: RequestPatternBuilder) {
        stubbing.verify(this, requestPatternBuilder)
    }

}

fun verify(block: GlobalVerificationScope.() -> Unit) {
    GlobalVerificationScope.block()
}

fun Stubbing.verify(block: StubbingVerificationScope.() -> Unit) {
    StubbingVerificationScope(this).block()
}