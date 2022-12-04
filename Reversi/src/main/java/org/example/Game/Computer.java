package org.example.Game;

public class Computer implements Player {
    private final boolean hardModeOn;

    Computer(boolean isHardMode) {
        hardModeOn = isHardMode;
    }

    @Override
    public void doMove(Table table, boolean isFirst) {
        int x = 0, y = 0;
        double maxGrade = -10000d;
        double tempGrade = 0d;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (table.canPlayerPutDisk(isFirst, i, j)) {
                    if (hardModeOn) {
                        tempGrade = advancedGrade(table, isFirst, i, j);
                    } else {
                        tempGrade = simpleGrade(table, isFirst, i, j);
                    }

                    if (tempGrade > maxGrade) {
                        maxGrade = tempGrade;
                        x = i;
                        y = j;
                    }
                }
            }
        }

        table.putDisk(isFirst, x, y);
    }

    private Double advancedGrade(Table table, boolean isFirst, int x, int y) {
        Table simulatedTable = new Table(table);
        simulatedTable.putDisk(isFirst, x, y);
        Double preGrade = simpleGrade(table, isFirst, x, y);
        double maxForOtherPlayer = 0d;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (simulatedTable.canPlayerPutDisk(!isFirst, i, j)) {
                    maxForOtherPlayer = Math.max(maxForOtherPlayer, simpleGrade(simulatedTable, !isFirst, i, j));
                }
            }
        }
        return preGrade - maxForOtherPlayer;
    }

    private Double simpleGrade(Table table, boolean isFirst, int x, int y) {
        Double grade = 0d;
        if ((x == 0 && y == 0) || (x == 0 && y == 7) || (x == 7 && y == 0) || (x == 7 && y == 7)) {
            grade = 0.8;
        } else if (x == 0 || y == 0 || x == 7 || y == 7) {
            grade = 0.4;
        }

        grade += gradeRow(table, isFirst, x, y, 0, -1);
        grade += gradeRow(table, isFirst, x, y, 1, -1);
        grade += gradeRow(table, isFirst, x, y, 1, 0);
        grade += gradeRow(table, isFirst, x, y, 1, 1);
        grade += gradeRow(table, isFirst, x, y, 0, 1);
        grade += gradeRow(table, isFirst, x, y, -1, 1);
        grade += gradeRow(table, isFirst, x, y, -1, 0);
        grade += gradeRow(table, isFirst, x, y, -1, -1);

        return grade;
    }

    private Double gradeRow(Table table, boolean isFirst, int x, int y, int deltaX, int deltaY) {
        x += deltaX;
        y += deltaY;
        int valueOnBoardCommonForCurrPlayer;
        if (isFirst) {
            valueOnBoardCommonForCurrPlayer = 1;
        } else {
            valueOnBoardCommonForCurrPlayer = 2;
        }
        double gradeForRow = 0d;
        while (x >= 0 && x < 8 && y >= 0 && y < 8) {
            if (table.getValue(x, y) != valueOnBoardCommonForCurrPlayer) {
                if (x == 0 || y == 0 || x == 7 || y == 7) {
                    gradeForRow += 2;
                } else {
                    gradeForRow += 1;
                }
            } else {
                return gradeForRow + gradeRow(table, isFirst, x, y, deltaX, deltaY);
            }
            x += deltaX;
            y += deltaY;
        }
        return gradeForRow;
    }

    @Override
    public void pass() {
    }
}
