package org.humeurvagabonde.playtime.reversi.port.adapter.rest

import org.springframework.context.MessageSource
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType.*
import org.springframework.web.reactive.function.server.RenderingResponse
import org.springframework.web.reactive.function.server.router
import org.springframework.web.reactive.function.server.ServerResponse.*
import reactor.core.publisher.toMono
import java.util.*

class Routes(val userHandler: ReversiHandler, val messageSource: MessageSource) {

    fun router() = router {
//        accept(TEXT_HTML).nest {
//            GET("/") { ok().render("index") }
//            GET("/sse") { ok().render("sse") }
//            GET("/users", userHandler::findAllView)
//        }
        "/api".nest {
//            accept(APPLICATION_JSON).nest {
//                GET("/users", userHandler::findAll)
//            }
            accept(TEXT_EVENT_STREAM).nest {
                GET("/users", userHandler::stream)
            }

        }
    }
}