package org.example.Game;

public interface Player {
    void doMove(Table table, boolean isFirst);

    void pass();
}
