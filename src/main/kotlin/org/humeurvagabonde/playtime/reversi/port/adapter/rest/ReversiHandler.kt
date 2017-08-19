package org.humeurvagabonde.playtime.reversi.port.adapter.rest

import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.*
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Flux

class ReversiHandler {

    private val userStream = Flux.just("francois", "veronique", "raphael")

    // TODO sur master on a acces a une methode bodyToServerSentEvents qui remplace .contentType(MediaType.TEXT_EVENT_STREAM).body()
    fun stream(req: ServerRequest) =
            ok().contentType(MediaType.TEXT_EVENT_STREAM).body(userStream)

}