package ru.otus.atm;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class AtmImplTest {
    private final Atm atm = new AtmImpl();

    @Test
    void totalAmount() {
        assertEquals(0, atm.totalAmount());

        atm.put(Map.of(10, 100, 50, 5, 1000, 3));
        assertEquals(4250, atm.totalAmount());
    }

    @ParameterizedTest
    @MethodSource("testData")
    void get(TestData data) {
        atm.put(data.put);

        if (data.result == null) {
            assertThrows(InsufficientFundsException.class, () -> atm.get(data.get));
        } else {
            assertEquals(data.result, atm.get(data.get));
        }

        assertEquals(data.rest, atm.totalAmount());
    }


    record TestData(
        Map<Integer, Integer> put,
        int get,
        Map<Integer, Integer> result,
        int rest
    ) {}

    static List<TestData> testData() {
        return List.of(
            new TestData(Map.of(), 0, Map.of(),0),
            new TestData(Map.of(10, 50, 100, 1), 250, Map.of(100, 1, 10, 15), 350),
            new TestData(Map.of(10, 50, 100, 2), 250, Map.of(100, 2, 10, 5), 450),
            new TestData(Map.of(10, 50, 100, 2), 700, Map.of(100, 2, 10, 50), 0),
            new TestData(Map.of(10, 50, 100, 2), 750, null, 700),
            new TestData(Map.of(10, 50, 100, 2), 105, null, 700)
        );

    }
}