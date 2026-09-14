package com.gildedtros.updater;

import com.gildedtros.Item;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class ItemUpdaterFactory {

    private static final String GOOD_WINE_NAME = "Good Wine";
    private static final String BACKSTAGE_PASS_NAME_PREFIX = "Backstage passes";

    private static final Set<String> LEGENDARY_ITEM_NAMES = setOf("B-DAWG Keychain");

    private static final Set<String> SMELLY_ITEM_NAMES =
            setOf("Duplicate Code", "Long Methods", "Ugly Variable Names");

    private static final ItemUpdater NORMAL_ITEM_UPDATER = new NormalItemUpdater();
    private static final ItemUpdater GOOD_WINE_UPDATER = new GoodWineUpdater();
    private static final ItemUpdater BACKSTAGE_PASS_UPDATER = new BackstagePassUpdater();
    private static final ItemUpdater LEGENDARY_ITEM_UPDATER = new LegendaryItemUpdater();
    private static final ItemUpdater SMELLY_ITEM_UPDATER = new SmellyItemUpdater();

    private ItemUpdaterFactory() {
    }

    public static ItemUpdater forItem(Item item) {
        String name = item.name;
        if (LEGENDARY_ITEM_NAMES.contains(name)) {
            return LEGENDARY_ITEM_UPDATER;
        }
        if (GOOD_WINE_NAME.equals(name)) {
            return GOOD_WINE_UPDATER;
        }
        if (name.startsWith(BACKSTAGE_PASS_NAME_PREFIX)) {
            return BACKSTAGE_PASS_UPDATER;
        }
        if (SMELLY_ITEM_NAMES.contains(name)) {
            return SMELLY_ITEM_UPDATER;
        }
        return NORMAL_ITEM_UPDATER;
    }

    private static Set<String> setOf(String... names) {
        return new HashSet<String>(Arrays.asList(names));
    }
}
