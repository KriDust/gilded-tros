package com.gildedtros.updater;

import com.gildedtros.Item;

class GoodWineUpdater extends AbstractItemUpdater {

    @Override
    protected int dailyQualityChange(Item item) {
        return isPastSellByDate(item) ? 2 : 1;
    }
}
