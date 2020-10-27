package com.github.lucasls.kotlinx.wiremock

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.stubbing.Scenario.STARTED
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class WireMockExamplesIT {

    private val wireMockServer = WireMockServer(0)

    private val host: String
        get() = "http://localhost:${wireMockServer.port()}"

    @BeforeEach
    internal fun setUp() {
        wireMockServer.start()
        configureFor(wireMockServer.port())
    }

    @AfterEach
    internal fun tearDown() {
        wireMockServer.stop()
    }

    @Test
    fun exactUrlOnly() {
        stubFor(
            get(urlEqualTo("/some/thing")) {
                willReturnAResponse {
                    withHeader("Content-Type", "text/plain")
                    withBody("Hello world!")
                }
            }
        )

        assertThat(khttp.get("$host/some/thing").statusCode).isEqualTo(200)
        assertThat(khttp.get("$host/some/thing/else").statusCode).isEqualTo(404)
    }

    @Test
    fun toDoListScenario() {
        stubFor(
            get(urlEqualTo("/todo/items")) {
                inScenario("To do list") {
                    whenScenarioStateIs(STARTED)
                    willReturnAResponse {
                        withBody("""<items><item>Buy milk</item></items>""")
                    }
                }
            },

            post(urlEqualTo("/todo/items")) {
                inScenario("To do list") {
                    whenScenarioStateIs(STARTED)
                    withRequestBody(containing("Cancel newspaper subscription"))
                    willReturnAResponse {
                        withStatus(201)
                    }
                    willSetStateTo("Cancel newspaper item added")
                }
            },

            get(urlEqualTo("/todo/items")) {
                inScenario("To do list") {
                    whenScenarioStateIs("Cancel newspaper item added")
                    willReturnAResponse {
                        withBody("""<items<item>Buy milk</item><item>Cancel newspaper subscription</item></items>""")
                    }
                }
            }
        )

        run {
            val response = khttp.get("$host/todo/items")
            assertThat(response.text).contains("Buy milk")
            assertThat(response.text).doesNotContain("Cancel newspaper subscription")
        }

        run {
            val response = khttp.post(
                "$host/todo/items",
                data = "Cancel newspaper subscription",
                headers = mapOf("Content-Type" to "text/plain; charset=utf-8")
            )
            assertThat(response.statusCode).isEqualTo(201)
        }

        run {
            val response = khttp.get("$host/todo/items")
            assertThat(response.text).contains("Buy milk")
            assertThat(response.text).contains("Cancel newspaper subscription")
        }
    }
}