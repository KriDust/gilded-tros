package com.gildedtros;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Gilded Tros inventory")
class GildedTrosTest {

    private static final String NORMAL_ITEM = "Ring of Cleansening Code";
    private static final String GOOD_WINE = "Good Wine";
    private static final String LEGENDARY_ITEM = "B-DAWG Keychain";
    private static final String BACKSTAGE_PASS = "Backstage passes for Re:Factor";

    @Test
    @DisplayName("every item in the inventory is updated")
    void updatesEveryItem() {
        Item[] items = {
                new Item(NORMAL_ITEM, 5, 10),
                new Item(GOOD_WINE, 5, 10),
                new Item(BACKSTAGE_PASS, 5, 10)
        };

        new GildedTros(items).updateQuality();

        assertEquals(9, items[0].quality);
        assertEquals(11, items[1].quality);
        assertEquals(13, items[2].quality);
    }

    @Nested
    @DisplayName("a normal item")
    class NormalItems {

        @Test
        @DisplayName("loses 1 quality and 1 day before the sell-by date")
        void degradesByOne() {
            Item item = updateOneDay(new Item(NORMAL_ITEM, 5, 10));
            assertEquals(9, item.quality);
            assertEquals(4, item.sellIn);
        }

        @Test
        @DisplayName("degrades twice as fast once the sell-by date has passed")
        void degradesTwiceAsFastWhenExpired() {
            assertEquals(8, updateOneDay(new Item(NORMAL_ITEM, 0, 10)).quality);
        }

        @Test
        @DisplayName("keeps degrading at the faster rate well past the sell-by date")
        void keepsDegradingTwiceAsFastWhenLongExpired() {
            assertEquals(8, updateOneDay(new Item(NORMAL_ITEM, -3, 10)).quality);
        }
    }

    @Nested
    @DisplayName("Good Wine")
    class GoodWine {

        @Test
        @DisplayName("increases in quality the older it gets")
        void improvesWithAge() {
            Item item = updateOneDay(new Item(GOOD_WINE, 5, 10));
            assertEquals(11, item.quality);
            assertEquals(4, item.sellIn);
        }

        @Test
        @DisplayName("improves twice as fast once the sell-by date has passed")
        void improvesTwiceAsFastWhenExpired() {
            assertEquals(12, updateOneDay(new Item(GOOD_WINE, 0, 10)).quality);
        }

        @Test
        @DisplayName("stops at a quality of 50")
        void neverExceedsFifty() {
            assertEquals(50, updateOneDay(new Item(GOOD_WINE, 0, 49)).quality);
        }
    }

    @Nested
    @DisplayName("a backstage pass")
    class BackstagePasses {

        @ParameterizedTest(name = "gains {1} quality at {0} days out")
        @CsvSource({
                "15, 1",
                "11, 1",
                "10, 2",
                "6,  2",
                "5,  3",
                "1,  3"
        })
        @DisplayName("increases in quality as the conference approaches")
        void increasesAsTheConferenceApproaches(int sellIn, int expectedIncrease) {
            assertEquals(20 + expectedIncrease, updateOneDay(new Item(BACKSTAGE_PASS, sellIn, 20)).quality);
        }

        @ParameterizedTest(name = "is worthless at a sellIn of {0}")
        @ValueSource(ints = {0, -1, -10})
        @DisplayName("drops to a quality of 0 after the conference")
        void isWorthlessAfterTheConference(int sellIn) {
            assertEquals(0, updateOneDay(new Item(BACKSTAGE_PASS, sellIn, 20)).quality);
        }

        @Test
        @DisplayName("never climbs above a quality of 50")
        void neverExceedsFifty() {
            assertEquals(50, updateOneDay(new Item(BACKSTAGE_PASS, 5, 49)).quality);
        }

        @Test
        @DisplayName("applies to any conference, not just the two in the fixture")
        void appliesToAnyConference() {
            assertEquals(23, updateOneDay(new Item("Backstage passes for HAXX", 5, 20)).quality);
        }
    }

    @Nested
    @DisplayName("a legendary item")
    class LegendaryItems {

        @Test
        @DisplayName("never has to be sold and never degrades")
        void neverChanges() {
            Item item = updateOneDay(new Item(LEGENDARY_ITEM, 5, 80));
            assertEquals(80, item.quality);
            assertEquals(5, item.sellIn);
        }

        @Test
        @DisplayName("keeps its quality of 80 even past the sell-by date")
        void keepsQualityEightyWhenExpired() {
            Item item = updateOneDay(new Item(LEGENDARY_ITEM, -1, 80));
            assertEquals(80, item.quality);
            assertEquals(-1, item.sellIn);
        }
    }

    @Nested
    @DisplayName("a smelly item")
    class SmellyItems {

        @ParameterizedTest(name = "{0} loses 2 quality a day before the sell-by date")
        @ValueSource(strings = {"Duplicate Code", "Long Methods", "Ugly Variable Names"})
        @DisplayName("degrades twice as fast as a normal item")
        void degradesTwiceAsFastAsNormalItems(String name) {
            assertEquals(8, updateOneDay(new Item(name, 5, 10)).quality);
        }

        @ParameterizedTest(name = "{0} loses 4 quality a day once the sell-by date has passed")
        @ValueSource(strings = {"Duplicate Code", "Long Methods", "Ugly Variable Names"})
        @DisplayName("degrades twice as fast again once the sell-by date has passed")
        void degradesTwiceAsFastAgainAfterTheSellByDate(String name) {
            assertEquals(6, updateOneDay(new Item(name, 0, 10)).quality);
        }
    }

    @Nested
    @DisplayName("quality bounds")
    class QualityBounds {

        @ParameterizedTest(name = "{0} never drops below 0")
        @ValueSource(strings = {NORMAL_ITEM, BACKSTAGE_PASS, "Duplicate Code", "Long Methods", "Ugly Variable Names"})
        @DisplayName("quality is never negative")
        void qualityIsNeverNegative(String name) {
            assertEquals(0, updateOneDay(new Item(name, 0, 1)).quality);
        }

        @ParameterizedTest(name = "{0} never rises above 50")
        @ValueSource(strings = {GOOD_WINE, BACKSTAGE_PASS})
        @DisplayName("quality never exceeds 50")
        void qualityNeverExceedsFifty(String name) {
            assertEquals(50, updateOneDay(new Item(name, 5, 50)).quality);
        }
    }

    private static Item updateOneDay(Item item) {
        new GildedTros(new Item[]{item}).updateQuality();
        return item;
    }
}
