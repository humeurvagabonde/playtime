package org.humeurvagabonde.playtime.reversi.domain.model;

import java.util.UUID;

public class ReversiBoardGame {

    private String id;

    public ReversiBoardGame() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReversiBoardGame that = (ReversiBoardGame) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
