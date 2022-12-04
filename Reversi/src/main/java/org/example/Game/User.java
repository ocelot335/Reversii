package org.example.Game;

import org.example.UI;

public class User implements Player {

    @Override
    public void doMove(Table table, boolean isFirst) {
        UI.usersTurnInterface(table, isFirst);
    }

    @Override
    public void pass() {
        UI.passMessage();
    }
}
