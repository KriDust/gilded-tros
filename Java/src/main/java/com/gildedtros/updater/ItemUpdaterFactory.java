package com.gildedtros.updater;

import com.gildedtros.Item;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class ItemUpdaterFactory {

    private static final String GOOD_WINE = "Good Wine";
    private static final String BACKSTAGE_PASS_PREFIX = "Backstage passes";

    private static final Set<String> LEGENDARY_ITEMS = setOf("B-DAWG Keychain");

    private static final ItemUpdater NORMAL_ITEM = new NormalItemUpdater();
    private static final ItemUpdater GOOD_WINE_ITEM = new GoodWineUpdater();
    private static final ItemUpdater BACKSTAGE_PASS = new BackstagePassUpdater();
    private static final ItemUpdater LEGENDARY_ITEM = new LegendaryItemUpdater();

    private ItemUpdaterFactory() {
    }

    public static ItemUpdater forItem(Item item) {
        String name = item.name;
        if (LEGENDARY_ITEMS.contains(name)) {
            return LEGENDARY_ITEM;
        }
        if (GOOD_WINE.equals(name)) {
            return GOOD_WINE_ITEM;
        }
        if (name.startsWith(BACKSTAGE_PASS_PREFIX)) {
            return BACKSTAGE_PASS;
        }
        return NORMAL_ITEM;
    }

    private static Set<String> setOf(String... names) {
        return Collections.unmodifiableSet(new HashSet<String>(Arrays.asList(names)));
    }
}
