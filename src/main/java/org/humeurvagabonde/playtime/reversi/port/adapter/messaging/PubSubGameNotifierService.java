package org.humeurvagabonde.playtime.reversi.port.adapter.messaging;

import org.humeurvagabonde.playtime.reversi.support.GameChannelsRegistry;
import org.humeurvagabonde.playtime.reversi.domain.model.GameNotifierService;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.UUID;

@Named
@Singleton
public class PubSubGameNotifierService implements GameNotifierService {

    @Inject
    private GameChannelsRegistry channelsRegistry;

    public void notifierFinDeTour(String gameId) {
        MessageChannel channel = channelsRegistry.resolve(gameId);
        channel.send(new GenericMessage<String>("finDeTour"));
    }

}
