package com.gildedtros.updater;

import com.gildedtros.Item;

abstract class AbstractItemUpdater implements ItemUpdater {

    private static final int MIN_QUALITY = 0;
    private static final int MAX_QUALITY = 50;

    @Override
    public final void update(Item item) {
        // The quality change is based on the sell-by date at the start of the day,
        // which is why sellIn is only advanced afterwards.
        item.quality = enforceQualityBounds(item.quality + dailyQualityChange(item));
        item.sellIn--;
    }

    protected abstract int dailyQualityChange(Item item);

    protected final boolean isPastSellByDate(Item item) {
        return item.sellIn <= 0;
    }

    private static int enforceQualityBounds(int quality) {
        return Math.min(MAX_QUALITY, Math.max(MIN_QUALITY, quality));
    }
}
