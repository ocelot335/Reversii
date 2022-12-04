package org.example;

import org.example.Game.Party;

import java.util.Scanner;

/**
 * Класс менеджера
 */
public class Manager {
    /**
     * Сканер для получения данных от пользователя
     */
    private static final Scanner scanner = new Scanner(System.in);
    /**
     * Поле отвечающие тому, что игра должна продолжаться
     */
    private static boolean isGameShouldBeOn = true;
    /**
     * Поле соответсвующее текущей партии
     */
    private static Party currentParty;
    /*
    1 - против компьютера на лёгком уровне сложности
    2 - против компьютера на продвинутом уровне сложности
    3 - против игрока
    4 - Статистика
    5 - Выйти
    */
    /**
     * Поле соответсвующее выбору пользователя
     */
    static Integer currentOption;
    /**
     * Поле соответсвующее наибольшему значению очков
     */
    static Integer bestScore = 0;

    /**
     * Метод начала новой игры
     */
    public static void startNewGame() {
        while (isGameShouldBeOn) {
            //Вывод меню
            UI.printMenu();
            //Ввод выюранного числа
            try {
                currentOption = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException exception) {
                UI.printNumberFormatExceptionMessage();
                continue;
            }
            //Реализация выбранного действия
            switch (currentOption) {
                case 1:
                    currentParty = new Party(true, false);
                    currentParty.startParty();
                    break;
                case 2:
                    currentParty = new Party(true, true);
                    currentParty.startParty();
                    break;
                case 3:
                    currentParty = new Party(false);
                    currentParty.startParty();
                    break;
                case 4:
                    UI.printStatistics();
                    break;
                case 5:
                    isGameShouldBeOn = false;
                    Main.disableProgram();
                    UI.printByeMessage();
                    break;
                default:
                    UI.printBadNumberMessage();
                    break;
            }
        }
    }

    /**
     * Обновление лучшего результата
     * @param score потенциальный лучший результат
     */
    public static void updateBestScore(Integer score) {
        bestScore = score > bestScore ? score : bestScore;
    }

    /**
     * Метод возвращающий лучший результат
     * @return Лучший результат
     */
    public static Integer getBestScore() {
        return bestScore;
    }

    /**
     * Метод возврающий текущую партию
     * @return Текущая партия
     */
    public static Party getCurrentParty() {
        return currentParty;
    }

    /**
     * Метод возвращающий сканер
     * @return Сканер программы
     */
    public static Scanner getScanner() {
        return scanner;
    }
}
