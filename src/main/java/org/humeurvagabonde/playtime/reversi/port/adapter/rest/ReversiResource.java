package org.humeurvagabonde.playtime.reversi.port.adapter.rest;

import org.humeurvagabonde.playtime.reversi.application.ReversiApplicationService;
import org.humeurvagabonde.playtime.reversi.domain.model.ReversiBoardGame;
import org.humeurvagabonde.playtime.reversi.support.GameChannelsRegistry;
import org.springframework.http.MediaType;
import org.springframework.messaging.MessageHandler;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.UUID;

@RestController
@RequestMapping("/boardgames/reversi")
public class ReversiResource {

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
