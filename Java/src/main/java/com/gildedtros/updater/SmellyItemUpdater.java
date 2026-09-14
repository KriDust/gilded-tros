package com.gildedtros.updater;

import com.gildedtros.Item;

class SmellyItemUpdater extends AbstractItemUpdater {

    @Override
    protected int dailyQualityChange(Item item) {
        return isPastSellByDate(item) ? -4 : -2;
    }
}
