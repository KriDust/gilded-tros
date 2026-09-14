package com.gildedtros.updater;

import com.gildedtros.Item;

class BackstagePassUpdater extends AbstractItemUpdater {

    private static final int DAYS_LEFT_FOR_TRIPLE_INCREASE = 5;
    private static final int DAYS_LEFT_FOR_DOUBLE_INCREASE = 10;

    @Override
    protected int dailyQualityChange(Item item) {
        if (isPastSellByDate(item)) {
            return loseAllQuality(item);
        }
        if (item.sellIn <= DAYS_LEFT_FOR_TRIPLE_INCREASE) {
            return 3;
        }
        if (item.sellIn <= DAYS_LEFT_FOR_DOUBLE_INCREASE) {
            return 2;
        }
        return 1;
    }

    private static int loseAllQuality(Item item) {
        return -item.quality;
    }
}
