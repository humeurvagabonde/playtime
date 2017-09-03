package org.humeurvagabonde.playtime.reversi.port.adapter.web;

import org.humeurvagabonde.playtime.reversi.application.ReversiApplicationService;
import org.humeurvagabonde.playtime.reversi.support.GameChannelsRegistry;
import org.springframework.http.MediaType;
import org.springframework.messaging.MessageHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Named
@Singleton
public class ReversiResourceHandler {

    @Inject
    private ReversiApplicationService reversiAppSrv;

    @Inject
    private GameChannelsRegistry channelsRegistry;

    // TODO il manque surement une brique de routage qui prend n'importe quel message evoyé par les notificateur et les redirige vers la bonne chaine
    // http://docs.spring.io/spring-integration/reference/html/messaging-routing-chapter.html

    public Mono<ServerResponse> create(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON).body(fromObject(reversiAppSrv.createGame()));
    }

    public Mono<ServerResponse> play(ServerRequest request) {
        String uuid = request.pathVariable("gameId");
        reversiAppSrv.play(uuid);
        return ok().build();
    }

    public Mono<ServerResponse> gameActions(ServerRequest request) {
        String uuid = request.pathVariable("gameId");
        Flux<String> actions = Flux.create(sink -> {
            MessageHandler handler = msg -> sink.next(String.class.cast(msg.getPayload()));
            channelsRegistry.resolve(uuid).subscribe(handler);

            sink.onDispose(() -> {
                System.out.println("kjlkzjdlkz");
            });
        });
        // TODO tester .body(BodyInserters.fromServerSideEvent());
        return ok().contentType(TEXT_EVENT_STREAM).body(actions, String.class);
    }
}

/*
@RestController
@RequestMapping("/boardgames/reversi")
public class ReversiResourceHandler {

    @Inject
    private ReversiApplicationService reversiAppSrv;

    @Inject
    private GameChannelsRegistry channelsRegistry;

    // TODO il manque surement une brique de routage qui prend n'importe quel message evoyé par les notificateur et les redirige vers la bonne chaine
    // http://docs.spring.io/spring-integration/reference/html/messaging-routing-chapter.html

    @PostMapping("/games")
    String create() {
        return reversiAppSrv.createGame();
    }

    @PostMapping("/games/{gameId}/action")
    void play(@PathVariable("gameId") String uuid) {
        reversiAppSrv.play(uuid);
    }

    @GetMapping(value = "/games/{gameId}/actions", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<String> gameActions(@PathVariable("gameId") String uuid) {
        return Flux.create(sink -> {
            MessageHandler handler = msg -> sink.next(String.class.cast(msg.getPayload()));
            channelsRegistry.resolve(uuid).subscribe(handler);

            sink.onDispose(() -> {
                System.out.println("kjlkzjdlkz");
            });
        });
    }

}
 */