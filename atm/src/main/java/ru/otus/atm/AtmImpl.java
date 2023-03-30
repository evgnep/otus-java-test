package ru.otus.atm;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class AtmImpl implements Atm {
    // ключ - номинал, значение - кол-во
    private final Map<Integer, Integer> banknotes = new TreeMap<>(Comparator.reverseOrder());

    @Override
    public void put(Map<Integer, Integer> banknotes) {
        banknotes.forEach((denomination, count) -> {
           if (denomination == null || count == null || denomination <= 0 || count < 0) {
               throw new IllegalArgumentException("Denomination must be > 0 and count must be >= 0");
           }
           this.banknotes.compute(denomination,
               (ignored, prevCount) -> prevCount == null ? count : prevCount + count);
        });
    }

    @Override
    public Map<Integer, Integer> get(int amount) {
        if (amount == 0) return Map.of();
        if (amount < 0) throw new IllegalArgumentException("amount < 0");

        var result = find(amount);
        result.forEach((denomination, count) ->
            banknotes.compute(denomination,
                (ignored, currentCount) -> currentCount > count ? currentCount - count : null)
        );
        return result;
    }

    /**
     * Пытается выдать amount из имеющихся купюр, используя жадный алгоритм
     */
    private Map<Integer, Integer> find(int amount) {
        var result = new HashMap<Integer, Integer>();
        var rest = amount;

        for (var denominationCount: banknotes.entrySet()) {
            int denomination = denominationCount.getKey();
            var used = Math.min(rest / denomination, denominationCount.getValue());
            if (used > 0) {
                result.put(denomination, used);
                rest -= used * denomination;
                if (rest == 0)
                    return result;
            }
        }
        throw new InsufficientFundsException();
    }

    @Override
    public int totalAmount() {
        return banknotes.entrySet().stream().reduce(0,
            (current, entry) -> current + entry.getKey() * entry.getValue(),
            Integer::sum);
    }
}
