package ru.otus.atm;

import java.util.Map;

public interface Atm {

    /**
     * Внести купюры
     * @param banknoteDenominations словарь номинал:кол-во принятых купюр
     */
    void put(Map<Integer, Integer> banknoteDenominations);

    /**
     * Получить указанную сумму
     * @param amount сумма к получению
     * @return номинал:кол-во выданных купюр
     * @throws InsufficientFundsException если недостаточно купюр
     */
    Map<Integer, Integer> get(int amount);

    /**
     * Сумма денег в банкомате
     */
    int totalAmount();

}
