package org.humeurvagabonde.playtime.reversi.domain.model;

import java.util.UUID;

public interface NotificateurTourService {

    void notifierFinDeTour(UUID gameId);

}
