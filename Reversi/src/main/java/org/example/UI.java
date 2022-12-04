package org.example;

import org.example.Game.Table;

import static org.example.ColorsForConsole.*;

/**
 * Класс пользовательского интерфейса
 */
public class UI {
    /**
     * Метод печатающий приветственное сообщение
     */
    static void printStartingMessage() {
        System.out.println("Приветствуем в игре Реверси!");
    }

    /**
     * Метод печатающий прощальное сообщение
     */
    static void printByeMessage() {
        System.out.println("Спасибо, что сыграли в игру, удачи!");
    }

    /**
     * Метод печатающий меню
     */
    static void printMenu() {
        System.out.println("""
                 1 - Играть против компьютера на лёгком уровне сложности
                 2 - Играть против компьютера на продвинутом уровне сложности
                 3 - Играть против игрока
                 4 - Лучший результат
                 5 - Выйти
                \s""".indent(3));
    }

    /**
     * Метод печатающий лучший результат
     */
    static void printStatistics() {
        System.out.println("Наибольшее количество очков в сессии: " + Manager.getBestScore());
    }

    /**
     * Метод отвечающий за пользовательский интерфейс хода
     *
     * @param table   Игровое поле
     * @param isFirst Параметр отвечающий за то первый ли игрок ходит
     */
    public static void usersTurnInterface(Table table, boolean isFirst) {
        //Заголовок хода
        if (isFirst) {
            System.out.println(ANSI_RED + "----------1-ый----------" + ANSI_RESET);
        } else {
            System.out.println(ANSI_RED + "----------2-ой----------" + ANSI_RESET);
        }
        //Вывод поля
        System.out.println("Текущее поле:");
        int countOfFree = printTable(table, isFirst);
        System.out.println("Выберите номер клеите куда хотите поставить фишку либо наберите 0 для возврата на " +
                "предыдущий ход, если это возможно либо -1 для выхода:");
        while (true) {
            int num = getNumber();
            if (num == -1) {
                //Пользователь захотел закончить партию
                Manager.getCurrentParty().stopParty();
                return;
            } else if (num == 0) {
                //Пользователь захотел вернуться на ход назад
                Manager.getCurrentParty().retake();
                return;
            } else if (num < 1 || num > countOfFree) {
                System.out.println("Нет свободной клетки с таким номером");
            } else {
                //Поиск выбранной клетки
                //Исходя из всего смысла программы это уместно
                int counter = 0;
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (table.canPlayerPutDisk(isFirst, i, j)) {
                            counter++;
                        }
                        if (counter == num) {
                            table.putDisk(isFirst, i, j);
                            return;
                        }
                    }
                }
            }
        }
    }

    /**
     * Метод печатающий доску и возвращающий количество клеток куда можно поставить фишку
     *
     * @param table   Игровое поле
     * @param isFirst Параметр отвечающий за то первый ли игрок ходит
     * @return Количество клеток куда можно поставить фишку
     */
    private static int printTable(Table table, boolean isFirst) {
        //Программа делает два не очень связанных дела
        //Это не очень хорошо
        //Но в такой частности можно положить уместным
        //Всё-таки было бы хорошо переписать получше
        System.out.print(ANSI_GREEN_BACKGROUND);
        for (int j = 0; j < 8; j++) {
            System.out.print("|---");
        }
        System.out.println("|" + ANSI_RESET);
        int countOfFree = 0;
        int countOfBlack = 0;
        int countOfWhite = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(ANSI_GREEN_BACKGROUND + "|");
                if (table.getValue(i, j) == 0) {
                    if (table.canPlayerPutDisk(isFirst, i, j)) {
                        countOfFree++;
                        if (countOfFree >= 10) {
                            System.out.print(ANSI_CYAN_BACKGROUND + " " + countOfFree + ANSI_RESET + ANSI_GREEN_BACKGROUND);
                        } else {
                            System.out.print(ANSI_CYAN_BACKGROUND + " " + countOfFree + " " + ANSI_RESET + ANSI_GREEN_BACKGROUND);
                        }

                    } else {
                        System.out.print("   ");
                    }
                } else if (table.getValue(i, j) != 0) {
                    if (table.getValue(i, j) == 1) {
                        countOfBlack++;
                        System.out.print(ANSI_BLACK_BACKGROUND);
                    } else {
                        countOfWhite++;
                        System.out.print(ANSI_WHITE_BACKGROUND);
                    }
                    if (i == table.getLastX() && j == table.getLastY()) {
                        System.out.print(ANSI_RED + " * " + ANSI_RESET + ANSI_GREEN_BACKGROUND);
                    } else {
                        System.out.print("   " + ANSI_RESET + ANSI_GREEN_BACKGROUND);
                    }
                }
            }
            System.out.println("|" + ANSI_RESET);
            System.out.print(ANSI_GREEN_BACKGROUND);
            for (int j = 0; j < 8; j++) {
                System.out.print("|---");
            }
            System.out.println("|" + ANSI_RESET);
        }
        System.out.println("Количество чёрных: " + countOfBlack);
        System.out.println("Количество белых: " + countOfWhite);
        return countOfFree;
    }

    /**
     * Метод совершающий обработку ситуации, когда никто не может сходить
     *
     * @param table Игровая доска
     */
    public static void nobodyMessage(Table table) {
        int firstScore = 0;
        int secondScore = 0;
        int disk;
        //Подсчёт количества чёрных и белых фишек
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                disk = table.getValue(i, j);
                if (disk == 1) {
                    firstScore++;
                } else if (disk == 2) {
                    secondScore++;
                }
            }
        }
        Manager.updateBestScore(Math.max(firstScore, secondScore));
        if (firstScore > secondScore) {
            System.out.println(ANSI_RED + "Первый игрок победил со счётом: " + firstScore + ANSI_RESET);
        } else if (firstScore < secondScore) {
            System.out.println(ANSI_RED + "Второй игрок победил со счётом: " + secondScore + ANSI_RESET);
        } else {
            System.out.println(ANSI_RED + "Никто не может сделать ход! Объявляется ничья" + ANSI_RESET);
        }
    }

    /**
     * Метод печатающий сообщение о том, что игрок не может сделать ход
     */
    public static void passMessage() {
        System.out.println(ANSI_RED + "Извините, вы не можете сделать ход и соответственно пропускаете его" + ANSI_RESET);
    }

    /**
     * Метод печатающий сообщение об ошибке
     */
    static void printUnknownErrorMessage() {
        System.out.println("Произошла непредвиденная ошибка!\nИгра будет перезапущена!");

    }

    /**
     * Метод позволяющий получить число от пользователя
     *
     * @return Введённое число
     */
    private static Integer getNumber() {
        while (true) {
            String numString = Manager.getScanner().nextLine();
            try {
                return Integer.parseInt(numString);
            } catch (NumberFormatException e) {
                printNumberFormatExceptionMessage();
            }
        }
    }

    /**
     * Метод печатающий сообщение о некорректном числе
     */
    static void printBadNumberMessage() {
        System.out.println("Введено некорректное число!");
    }

    /**
     * Метод печатающий сообщение о том, что было введено не число
     */
    static void printNumberFormatExceptionMessage() {
        System.out.println("Вы ввели не число!");
    }
}
