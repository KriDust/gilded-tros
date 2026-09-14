package com.gildedtros.updater;

import com.gildedtros.Item;

class BackstagePassUpdater extends AbstractItemUpdater {

    private static final int TRIPLE_INCREASE_THRESHOLD = 5;
    private static final int DOUBLE_INCREASE_THRESHOLD = 10;

    @Override
    protected int qualityDelta(Item item) {
        if (isPastSellByDate(item)) {
            return -item.quality;
        }
        if (item.sellIn <= TRIPLE_INCREASE_THRESHOLD) {
            return 3;
        }
        if (item.sellIn <= DOUBLE_INCREASE_THRESHOLD) {
            return 2;
        }
        return 1;
    }
}
