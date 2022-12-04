package org.example;

/**
 * Главный класс программы
 */
public class Main {

    /**
     *Флаг соответсвующий работе программы
     */
    private static boolean isProgramShouldWork = true;

    /**
     * Метод отключения программы
     */
    public static void disableProgram() {
        isProgramShouldWork = false;
    }

    /**
     * Стартовая функция
     * @param args параметры запуска
     */
    public static void main(String[] args) {
        UI.printStartingMessage();
        while (isProgramShouldWork) {
            try {
                Manager.startNewGame();
            } catch (Exception notCachedException) {
                UI.printUnknownErrorMessage();
            }
        }
    }
}