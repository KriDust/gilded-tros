package com.gildedtros.updater;

import com.gildedtros.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("the updater chosen for an item")
class ItemUpdaterFactoryTest {

    @ParameterizedTest(name = "\"{0}\" is handled by {1}")
    @CsvSource({
            "Ring of Cleansening Code,NormalItemUpdater",
            "Elixir of the SOLID,NormalItemUpdater",
            "Anything Unrecognised,NormalItemUpdater",
            "Good Wine,GoodWineUpdater",
            "B-DAWG Keychain,LegendaryItemUpdater",
            "Backstage passes for Re:Factor,BackstagePassUpdater",
            "Backstage passes for HAXX,BackstagePassUpdater",
            "Duplicate Code,SmellyItemUpdater",
            "Long Methods,SmellyItemUpdater",
            "Ugly Variable Names,SmellyItemUpdater"
    })
    void dispatchesOnItemName(String itemName, String expectedUpdaterName) {
        assertEquals(expectedUpdaterName, updaterNameFor(itemName));
    }

    @ParameterizedTest(name = "\"{0}\" is recognised as a backstage pass")
    @ValueSource(strings = {
            "Backstage passes for Re:Factor",
            "Backstage passes for HAXX",
            "Backstage passes for a conference that does not exist yet"
    })
    @DisplayName("backstage passes are matched as a category, not by exact name")
    void matchesAnyBackstagePass(String itemName) {
        assertEquals("BackstagePassUpdater", updaterNameFor(itemName));
    }

    private static String updaterNameFor(String itemName) {
        return ItemUpdaterFactory.forItem(new Item(itemName, 0, 0)).getClass().getSimpleName();
    }
}
