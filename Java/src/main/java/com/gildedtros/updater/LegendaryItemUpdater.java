package com.gildedtros.updater;

import com.gildedtros.Item;

class LegendaryItemUpdater implements ItemUpdater {

    @Override
    public void update(Item item) {
        // Legendary items never have to be sold and never degrade, so neither
        // sellIn nor quality ages. They also sit outside the 0-50 quality range,
        // which is why this deliberately bypasses the template in AbstractItemUpdater.
    }
}
