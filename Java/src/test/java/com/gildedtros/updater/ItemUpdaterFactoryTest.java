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
    void dispatchesOnItemName(String name, String expectedUpdater) {
        assertEquals(expectedUpdater, updaterFor(name));
    }

    @ParameterizedTest(name = "\"{0}\" is recognised as a backstage pass")
    @ValueSource(strings = {
            "Backstage passes for Re:Factor",
            "Backstage passes for HAXX",
            "Backstage passes for a conference that does not exist yet"
    })
    @DisplayName("backstage passes are matched as a category, not by exact name")
    void matchesAnyBackstagePass(String name) {
        assertEquals("BackstagePassUpdater", updaterFor(name));
    }

    private static String updaterFor(String name) {
        return ItemUpdaterFactory.forItem(new Item(name, 0, 0)).getClass().getSimpleName();
    }
}
