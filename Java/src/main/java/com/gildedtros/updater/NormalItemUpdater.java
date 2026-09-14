package com.gildedtros.updater;

import com.gildedtros.Item;

class NormalItemUpdater extends AbstractItemUpdater {

    @Override
    protected int qualityDelta(Item item) {
        return isPastSellByDate(item) ? -2 : -1;
    }
}
