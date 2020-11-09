[ ![JCenter](https://api.bintray.com/packages/lucasls/oss/wiremock-kotlin-extensions/images/download.svg) ](https://bintray.com/lucasls/oss/wiremock-kotlin-extensions/_latestVersion)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.lucasls.kotlinx.wiremock/wiremock-kotlin-extensions/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.lucasls.kotlinx.wiremock/wiremock-kotlin-extensions)

# WireMock Kotlin Extensions
[WireMock](http://wiremock.org/) is awesome, using Java DSLs on Kotlin not that much. This lib aims to help with that. Making use of Kotlin's powerful Extension Functions, this lib enhances WireMock API without losing any existing functionality.

# Getting Started

WireMock Kotlin Extensions is published to Maven Central and JCenter.

## Gradle
```groovy
testCompile "com.github.lucasls.kotlinx.wiremock:wiremock-kotlin-extensions:0.1.2"
testCompile "com.github.tomakehurst:wiremock-jre8:2.27.2"
```

## Maven

```xml
<dependency>
    <groupId>com.github.lucasls.kotlinx.wiremock</groupId>
    <artifactId>wiremock-kotlin-extensions</artifactId>
    <version>0.10.0</version>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>com.github.tomakehurst</groupId>
    <artifactId>wiremock-jre8</artifactId>
    <version>2.27.2</version>
    <scope>test</scope>
</dependency>
```


## Stubbing

```kotlin
stubFor(
    get(urlEqualTo("/my/resource")) {
        withHeader("Accept", equalTo("text/xml"))

        willReturnAResponse {
            withStatus(200)
            withHeader("Content-Type", "text/xml")
            withBody("<response>Some content</response>")
        }
    },

    post(urlEqualTo("/my/resource")) {
        withHeader("Accept", equalTo("text/xml"))

        willReturnAResponse {
            withStatus(200)
            withHeader("Content-Type", "text/xml")
            withBody("<response>Some content</response>")
        }
    }
)
```

## Verifying

```kotlin
verify {
    n * postRequestedFor(urlMatching("/my/resource/[a-z0-9]+")) {
        withRequestBody(matching(".*<message>1234</message>.*"))
        withHeader("Content-Type", notMatching("application/json"))
    }

    1 * getRequestedFor(urlMatching("/my/resource/[a-z0-9]+")) {
        withRequestBody(matching(".*<message>1234</message>.*"))
        withHeader("Content-Type", notMatching("application/json"))
    }
}
```

## Experimental Stubbing DSL

```kotlin
stubFor {
    ("GET" to "/my/resource") {
        withHeader("Accept", equalTo("text/xml"))

        willReturnAResponse {
            withStatus(200)
            withHeader("Content-Type", "text/xml")
            withBody("<response>Some content</response>")
        }
    }
    
    ("POST" to "/my/resource") {
        withHeader("Accept", equalTo("text/xml"))

        willReturnAResponse {
            withStatus(200)
            withHeader("Content-Type", "text/xml")
            withBody("<response>Some content</response>")
        }
    }
}
```

WireMock reference: http://wiremock.org/docs/getting-started/


# WireMock Standalone, Java 7, Java 8, ...

The WireMock Kotlin Extension dependency can be used alongside all different flavours of WireMock, namely:

- **Java 7:** `com.github.tomakehurst:wiremock:2.x.x`
- **Java 7 Standalone:** `com.github.tomakehurst:wiremock-standalone:2.x.x`
- **Java 8:** `com.github.tomakehurst:wiremock-jre8:2.x.x`
- **Java 8 Standalone:** `com.github.tomakehurst:wiremock-jre8-standalone:2.x.x`

WireMock won't be automatically included as a transitive dependency, so please make sure to include it.  

WireMock reference: http://wiremock.org/docs/download-and-installation/


# How does it compare

WireMock is perfectly functional with Kotlin, but feels dated, because Java was not built for DSLs.

Here are some examples adapted from WireMock docs, comparing usages with both DSLs.

## Stubbing

**Original WireMock Java DSL**

```kotlin
stubFor(
    get(urlEqualTo("/some/thing"))
        .willReturn(
            aResponse()
                .withHeader("Content-Type", "text/plain")
                .withBody("Hello world!")))

stubFor(get(urlEqualTo("/body"))
    .willReturn(aResponse()
        .withBody("Literal text to put in the body")))

stubFor(any(anyUrl())
    .atPriority(10)
    .willReturn(aResponse()
        .withStatus(404)
        .withBody("""{"status":"Error","message":"Endpoint not found"}""")))

stubFor(get(urlMatching("/api/.*")).atPriority(5)
    .willReturn(aResponse().withStatus(401)))

stubFor(get(urlEqualTo("/todo/items")).inScenario("To do list")
    .whenScenarioStateIs(STARTED)
    .willReturn(aResponse()
        .withBody("""<items><item>Buy milk</item></items>""")))
``` 

**WireMock Kotlin Extensions (Stable)**

```kotlin
stubFor(
    get(urlEqualTo("/some/thing")) {
        willReturnAResponse {
            withHeader("Content-Type", "text/plain")
            withBody("Hello world!")
        }
    },

    get(urlEqualTo("/body")) {
        willReturnAResponse {
            withBody("Literal text to put in the body")
        }
    },

     any(anyUrl) {
        atPriority(10)
        willReturnAResponse {
            withStatus(404)
            withBody("""{"status":"Error","message":"Endpoint not found"}""")
        }
    },

    get(urlMatching("/api/.*")) {
        atPriority(5)
        willReturnAResponse {
            withStatus(401)
        }
    },

    get(urlEqualTo("/todo/items")) {
        inScenario("To do list") {
            whenScenarioStateIs(STARTED)
            willReturnAResponse {
                withBody("""<items><item>Buy milk</item></items>""")
            }
        }
    }
)
```

**WireMock Kotlin Extensions (Experimental)**

```kotlin
stubFor {
    ("GET" to "/some/thing") {
        willReturnAResponse {
            withHeader("Content-Type", "text/plain")
            withBody("Hello world!")
        }
    }

    ("GET" to "/body") {
        willReturnAResponse {
            withBody("Literal text to put in the body")
        }
    }

    ("ANY" to anyUrl) {
        atPriority(10)
        willReturnAResponse {
            withStatus(404)
            withBody("""{"status":"Error","message":"Endpoint not found"}""")
        }
    }

    ("GET" to urlMatching("/api/.*")) {
        atPriority(5)
        willReturnAResponse {
            withStatus(401)
        }
    }

    ("GET" to "/todo/items") {
        inScenario("To do list") {
            whenScenarioStateIs(STARTED)
            willReturnAResponse {
                withBody("""<items><item>Buy milk</item></items>""")
            }
        }
    }
}
```

WireMock reference
- http://wiremock.org/docs/stubbing/
- http://wiremock.org/docs/stateful-behaviour/

## Verifying

**Original WireMock Java DSL**

````kotlin
verify(postRequestedFor(urlEqualTo("/verify/this"))
            .withHeader("Content-Type", equalTo("text/xml")))

verify(3, postRequestedFor(urlEqualTo("/three/times")))

verify(lessThan(5), postRequestedFor(urlEqualTo("/many")))

verify(lessThanOrExactly(5), postRequestedFor(urlEqualTo("/many")))

verify(exactly(5), postRequestedFor(urlEqualTo("/many")))

verify(moreThanOrExactly(5), postRequestedFor(urlEqualTo("/many")))

verify(moreThan(5), postRequestedFor(urlEqualTo("/many")))
````


**WireMock Kotlin Extensions**

```kotlin
verify {
    n * postRequestedFor(urlEqualTo("/verify/this")) {
        withHeader("Content-Type", equalTo("text/xml"))
    }
    
    3 * postRequestedFor(urlEqualTo("/three/times"))
    
    lessThan(5) * postRequestedFor(urlEqualTo("/many"))
    
    lessThanOrExactly(5) * postRequestedFor(urlEqualTo("/many"))
    
    exactly(5) * postRequestedFor(urlEqualTo("/many"))
    
    moreThanOrExactly(5) * postRequestedFor(urlEqualTo("/many"))
    
    moreThan(5) * postRequestedFor(urlEqualTo("/many"))
}
```

WireMock reference: http://wiremock.org/docs/verifying/

## Request Matching

**Original WireMock Java DSL**

```kotlin
stubFor(any(urlPathEqualTo("/everything"))
    .withHeader("Accept", containing("xml"))
    .withCookie("session", matching(".*12345.*"))
    .withQueryParam("search_term", equalTo("WireMock"))
    .withBasicAuth("jeff@example.com", "jeffteenjefftyjeff")
    .withRequestBody(equalToXml("<search-results />"))
    .withRequestBody(matchingXPath("//search-results"))
    .withMultipartRequestBody(
        aMultipart()
            .withName("info")
            .withHeader("Content-Type", containing("charset"))
            .withBody(equalToJson("{}"))
    )
    .willReturn(aResponse()))
``` 

**WireMock Kotlin Extensions**

```kotlin
stubFor(
    any(urlPathEqualTo("/everything")) {
        withHeader("Accept", containing("xml"))
        withCookie("session", matching(".*12345.*"))
        withQueryParam("search_term", equalTo("WireMock"))
        withBasicAuth("jeff@example.com", "jeffteenjefftyjeff")
        withRequestBody(equalToXml("<search-results />"))
        withRequestBody(matchingXPath("//search-results"))

        withMultipartRequestBody {
            withName("info")
            withHeader("Content-Type", containing("charset"))
            withBody(equalToJson("{}"))
        }

        willReturnAResponse()
    }
)
```

WireMock reference: http://wiremock.org/docs/request-matching/

# Compatibility

WireMock Kotlin Extensions is 100% compatible with the original WireMock API.
In fact, most of the time while using the DSL you'll be using WireMock directly.

That's because this project is powered by Kotlin's extension functions, and inspired
by projects like [Mockito Kotlin](https://github.com/nhaarman/mockito-kotlin) and 
[Kotlin Logging](https://github.com/MicroUtils/kotlin-logging), which manage to enable
developers to write idiomatic Kotlin code without breaking compatibility with the 
existing Java ecosystem.

Some the advantages of this approach are:
- There's virtually no limitation to using this library compared to using WireMock directly: any
unimplemented functionality can be called using the existing Java API
- Existing projects making use of WireMock can incrementally adopt this library, including
projects which mixed Java and Kotlin source codes
- Existing WIreMock documentation, third party help articles, Stackoverflow questions still apply most 
of the time.

Of course there are disadvantages, like losing null safety and being restricted to the JVM.

# Contributing

This library is in very early stages, and there's a lot to improve.
Feel free to contact me if you would like to contribute in any way.
