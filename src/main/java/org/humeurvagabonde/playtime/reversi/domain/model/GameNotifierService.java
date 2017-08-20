package org.humeurvagabonde.playtime.reversi.domain.model;

import java.util.UUID;

public interface GameNotifierService {

    void notifierFinDeTour(String gameId);

}
