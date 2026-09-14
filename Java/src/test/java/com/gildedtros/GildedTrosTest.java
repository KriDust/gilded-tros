package com.gildedtros;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedTrosTest {

    @ParameterizedTest(name = "{0} loses 2 quality a day before the sell-by date")
    @ValueSource(strings = {"Duplicate Code", "Long Methods", "Ugly Variable Names"})
    void smellyItemsDegradeTwiceAsFastAsNormalItems(String name) {
        assertEquals(8, updateOneDay(new Item(name, 5, 10)).quality);
    }

    @ParameterizedTest(name = "{0} loses 4 quality a day once the sell-by date has passed")
    @ValueSource(strings = {"Duplicate Code", "Long Methods", "Ugly Variable Names"})
    void smellyItemsDegradeTwiceAsFastAgainAfterTheSellByDate(String name) {
        assertEquals(6, updateOneDay(new Item(name, 0, 10)).quality);
    }

    @ParameterizedTest(name = "{0} never degrades below a quality of 0")
    @ValueSource(strings = {"Duplicate Code", "Long Methods", "Ugly Variable Names"})
    void smellyItemQualityIsNeverNegative(String name) {
        assertEquals(0, updateOneDay(new Item(name, 0, 3)).quality);
    }

    private static Item updateOneDay(Item item) {
        new GildedTros(new Item[]{item}).updateQuality();
        return item;
    }
}
