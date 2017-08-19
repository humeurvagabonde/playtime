package org.humeurvagabonde.playtime

import org.humeurvagabonde.playtime.reversi.port.adapter.rest.ReversiHandler
import org.humeurvagabonde.playtime.reversi.port.adapter.rest.Routes
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.context.support.beans
import org.springframework.web.reactive.function.server.HandlerStrategies
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.server.WebHandler

fun beans() = beans {
    bean<ReversiHandler>()
    bean {
        Routes(it.ref(), it.ref())
    }
    bean<WebHandler>("webHandler") {
        RouterFunctions.toWebHandler(it.ref<Routes>().router(), HandlerStrategies.builder().viewResolver(it.ref()).build())
    }
    bean("messageSource") {
        ReloadableResourceBundleMessageSource().apply {
            setBasename("messages")
            setDefaultEncoding("UTF-8")
        }
    }
}