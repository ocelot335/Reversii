package org.example.Game;

/**
 * Класс игрового поля
 */
public class Table {
    /**
     * Поле хранящее доску
     */
    int[][] board = new int[8][8];
    /**
     * Поле хранящее x-координату последней поставленной фишки
     */
    int lastX = -1;
    /**
     * Поле хранящее y-координату последней поставленной фишки
     */
    int lastY = -1;

    /**
     * Конструктор по умолчанию
     */
    Table() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = 0;
            }
        }
        //Задача изначальной позиции
        board[3][3] = 2;
        board[4][4] = 2;
        board[4][3] = 1;
        board[3][4] = 1;
    }

    /**
     * Конструктор копирования
     * @param table Оная игровая доска
     */
    public Table(Table table) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = table.getValue(i, j);
            }
        }
        lastX = table.lastX;
        lastY = table.lastY;
    }

    /**
     * Метод возвращающий x-координату последней поставленной фишки
     * @return x-координата последней поставленной фишки
     */
    public int getLastX() {
        return lastX;
    }

    /**
     * Метод возвращающий y-координату последней поставленной фишки
     * @return y-координата последней поставленной фишки
     */
    public int getLastY() {
        return lastY;
    }

    /**
     * Метод для получения номера игрока чья фишка в клетке или ноль, если клетка пустая
     * @param x x-координата клетки
     * @param y y-координата клетки
     * @return номера игрока чья фишка в клетке или ноль, если клетка пустая
     */
    public int getValue(int x, int y) {
        return board[x][y];
    }

    /**
     * Метод указывающий может ли игрок поставить фишку на данную клетку
     * @param isFirst Параметр отвечающий за то первый ли игрок ходит
     * @param x x-координата клетки
     * @param y y-координата клетки
     * @return Вердикт по вопросу о том может ли игрок поставить фишку на данную клетку
     */
    public boolean canPlayerPutDisk(boolean isFirst, int x, int y) {
        if (board[x][y] != 0) {
            return false;
        }
        return checkForRow(isFirst, x, y, 0, -1) ||
                checkForRow(isFirst, x, y, 1, -1) ||
                checkForRow(isFirst, x, y, 1, 0) ||
                checkForRow(isFirst, x, y, 1, 1) ||
                checkForRow(isFirst, x, y, 0, 1) ||
                checkForRow(isFirst, x, y, -1, 1) ||
                checkForRow(isFirst, x, y, -1, 0) ||
                checkForRow(isFirst, x, y, -1, -1);
    }

    /**
     * Метод проверяющий есть ли 'замыкание в заданном направлении
     * @param isFirst Параметр отвечающий за то первый ли игрок ходит
     * @param x x-координата клетки начальной клетки в 'замыкании'
     * @param y y-координата клетки начальной клетки в 'замыкании'
     * @param deltaX x-координата направления
     * @param deltaY y-координата направления
     * @return Вердикт
     */
    private boolean checkForRow(boolean isFirst, int x, int y, int deltaX, int deltaY) {
        //(deltaX,deltaY) - вектор направления
        boolean canThey = false;
        x += deltaX;
        y += deltaY;
        int valueOnBoardCommonForCurrPlayer;
        if (isFirst) {
            valueOnBoardCommonForCurrPlayer = 1;
        } else {
            valueOnBoardCommonForCurrPlayer = 2;
        }
        while (x >= 0 && x < 8 && y >= 0 && y < 8) {
            if (board[x][y] != valueOnBoardCommonForCurrPlayer && board[x][y] != 0) {
                canThey = true;
            } else return canThey && board[x][y] == valueOnBoardCommonForCurrPlayer;
            x += deltaX;
            y += deltaY;
        }
        return false;
    }

    /**
     * Метод для ставящий фишку на заданную клетку
     * @param isFirst Параметр отвечающий за то первый ли игрок ходит
     * @param x x-координата клетки
     * @param y y-координата клетки
     */
    public void putDisk(boolean isFirst, int x, int y) {
        lastX = x;
        lastY = y;
        int valueOnBoardCommonForCurrPlayer;
        if (isFirst) {
            valueOnBoardCommonForCurrPlayer = 1;
        } else {
            valueOnBoardCommonForCurrPlayer = 2;
        }
        board[x][y] = valueOnBoardCommonForCurrPlayer;
        changeForRow(isFirst, x, y, 0, -1);
        changeForRow(isFirst, x, y, 1, -1);
        changeForRow(isFirst, x, y, 1, 0);
        changeForRow(isFirst, x, y, 1, 1);
        changeForRow(isFirst, x, y, 0, 1);
        changeForRow(isFirst, x, y, -1, 1);
        changeForRow(isFirst, x, y, -1, 0);
        changeForRow(isFirst, x, y, -1, -1);
    }

    /**
     * Метод меняющий цвет фишек в замыкании на правильный
     * @param isFirst Параметр отвечающий за то первый ли игрок ходит
     * @param x x-координата клетки начальной клетки в 'замыкании'
     * @param y y-координата клетки начальной клетки в 'замыкании'
     * @param deltaX x-координата направления
     * @param deltaY y-координата направления
     */
    private void changeForRow(boolean isFirst, int x, int y, int deltaX, int deltaY) {
        if (!checkForRow(isFirst, x, y, deltaX, deltaY)) {
            return;
        }
        x += deltaX;
        y += deltaY;
        int valueOnBoardCommonForCurrPlayer;
        if (isFirst) {
            valueOnBoardCommonForCurrPlayer = 1;
        } else {
            valueOnBoardCommonForCurrPlayer = 2;
        }
        while (x >= 0 && x < 8 && y >= 0 && y < 8) {
            if (board[x][y] != valueOnBoardCommonForCurrPlayer) {
                board[x][y] = valueOnBoardCommonForCurrPlayer;
            } else {
                changeForRow(isFirst, x, y, deltaX, deltaY);
                return;
            }
            x += deltaX;
            y += deltaY;
        }
    }
}
