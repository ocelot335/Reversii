package org.example.Game;

import org.example.UI;

import java.util.Stack;

/**
 * Класс партии
 */
public class Party {
    /**
     * Доска данной партии
     */
    private Table table;
    /**
     * Стек истории игровых полей
     */
    private final Stack<Table> historyOfParty = new Stack<Table>();
    /**
     * Поле хранящее первого игрока
     */
    private final Player firstPlayer;
    /**
     * Поле хранящее второго игрока
     */
    private final Player secondPlayer;

    private boolean partyIsOn = true;
    private boolean firstMove = true;

    public Party(boolean secondIsComputer) {
        this(secondIsComputer, false);
    }

    public Party(boolean secondIsComputer, boolean isHardMode) {
        table = new Table();
        firstPlayer = new User();
        if (secondIsComputer) {
            secondPlayer = new Computer(isHardMode);
        } else {
            secondPlayer = new User();
        }
    }

    public void startParty() {
        while (partyIsOn) {
            historyOfParty.push(new Table(table));
            boolean firstCan = checkCanDoTurn(table, true);
            boolean secondCan = checkCanDoTurn(table, false);
            if (!firstCan && !secondCan) {
                UI.nobodyMessage(table);
                partyIsOn = false;
                break;
            } else if (firstMove && !firstCan || !firstMove && !secondCan) {
                if (firstMove) {
                    firstPlayer.pass();
                } else {
                    secondPlayer.pass();
                }
            } else {
                if (firstMove) {
                    firstPlayer.doMove(table, true);
                } else {
                    secondPlayer.doMove(table, false);
                }
            }

            firstMove ^= true;
        }
    }

    public void retake() {
        if (historyOfParty.size() >= 3) {
            historyOfParty.pop();
            historyOfParty.pop();
            table = historyOfParty.peek();
        }
        historyOfParty.pop();
        firstMove ^= true;
    }

    public void stopParty() {
        partyIsOn = false;
    }

    private boolean checkCanDoTurn(Table table, boolean isFirst) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (table.canPlayerPutDisk(isFirst, i, j)) {
                    return true;
                }
            }
        }
        return false;
    }

}
