package org.humeurvagabonde.playtime.reversi.support;

import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.LinkedHashMap;
import java.util.UUID;

@Named
@Singleton
public class GameChannelsRegistry {

    private final LinkedHashMap<String, SubscribableChannel> channels = new LinkedHashMap<>();

    public SubscribableChannel resolve(String id) {
        SubscribableChannel channel = this.channels.get(id);
        if (channel == null) {
            channel = createNewChannel(id);
        }
        return channel;
    }

    private synchronized SubscribableChannel createNewChannel(String id) {
        SubscribableChannel channel = this.channels.get(id);
        if (channel == null) {
            channel = MessageChannels.publishSubscribe(id.toString())
                    .minSubscribers(1)
                    .get();

            this.channels.put(id, channel);
        }
        return channel;
    }

}
