package org.humeurvagabonde.playtime.reversi.application;

import org.humeurvagabonde.playtime.reversi.domain.model.GameNotifierService;
import org.humeurvagabonde.playtime.reversi.domain.model.ReversiBoardGame;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.transaction.Transactional;

@Named
@Singleton
@Transactional
public class ReversiApplicationService {

    @Inject
    private GameNotifierService notifierService;

    public String createGame() {
        ReversiBoardGame game = new ReversiBoardGame();
        return game.getId();
    }

    public void play(String gameId) {
        notifierService.notifierFinDeTour(gameId);
    }


}
