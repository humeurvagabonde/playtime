package org.humeurvagabonde.playtime.reversi.port.adapter.rest;

import org.humeurvagabonde.playtime.reversi.support.GameChannelsRegistry;
import org.springframework.http.MediaType;
import org.springframework.messaging.MessageHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.UUID;

@Named
@Singleton
public class ReversiResource {

    @Inject
    private GameChannelsRegistry channelsRegistry;

    @GetMapping(value = "/games/reversi/{gameId}/actions", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<String> gameActions(@PathVariable String uuidString) {
        return Flux.create(sink -> {
            UUID uuid = UUID.fromString(uuidString);
            MessageHandler handler = msg -> sink.next(String.class.cast(msg.getPayload()));
            channelsRegistry.resolve(uuid).subscribe(handler);
        });
    }

}
