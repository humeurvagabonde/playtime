package org.humeurvagabonde.playtime.reversi.support;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RequestPredicates.method;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RouterFunctions.toHttpHandler;

import org.humeurvagabonde.playtime.reversi.port.adapter.web.ReversiResourceHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import javax.inject.Inject;

@Configuration
public class ReversiConfig {

    @Bean
    public RouterFunction<ServerResponse> routerFunction(ReversiResourceHandler reversiResourceHandler) {
        RouterFunction<ServerResponse> createGameRoute =
                route(POST("/"), reversiResourceHandler::create);

        RouterFunction<ServerResponse> gameActionsRoutes =
                route(POST("/{gameId}/action").and(contentType(APPLICATION_JSON)), reversiResourceHandler::play)
                .andRoute(GET("/{gameId}/actions"), reversiResourceHandler::gameActions);


        return nest(path("/boardgames/reversi/games"),
                createGameRoute.and(gameActionsRoutes));

    }

    /*
        @Bean
    public RouterFunction<ServerResponse> routerFunction(ReversiResourceHandler reversiResourceHandler) {
        return nest(path("/boardgames/reversi/games"),
                    route(POST("/"), reversiResourceHandler::create)
                    .andRoute(POST("/{gameId}/action").and(contentType(APPLICATION_JSON)), reversiResourceHandler::play)
                ).andRoute(GET("/games/{gameId}/actions"), reversiResourceHandler::gameActions);

    }

     */
}
