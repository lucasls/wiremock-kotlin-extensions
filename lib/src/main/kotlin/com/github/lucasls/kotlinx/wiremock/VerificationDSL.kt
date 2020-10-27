package com.github.lucasls.kotlinx.wiremock

import com.github.tomakehurst.wiremock.client.CountMatchingStrategy
import com.github.tomakehurst.wiremock.junit.Stubbing
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder

object GlobalVerificationScope {
    class N {
        operator fun times(requestPatternBuilder: RequestPatternBuilder) {
            verify(requestPatternBuilder)
        }
    }
    val n: N = N()

    operator fun Int.times(requestPatternBuilder: RequestPatternBuilder) {
        verify(this, requestPatternBuilder)
    }

    operator fun CountMatchingStrategy.times(requestPatternBuilder: RequestPatternBuilder) {
        verify(this, requestPatternBuilder)
    }
}

class StubbingVerificationScope(private val stubbing: Stubbing) {
    class N {
        operator fun times(requestPatternBuilder: RequestPatternBuilder) {
            verify(requestPatternBuilder)
        }
    }
    val n: N = N()

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