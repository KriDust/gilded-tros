package com.gildedtros;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Pins the observable behaviour of updateQuality() across the whole legal input space,
 * so the refactoring underneath it is provably behaviour-preserving.
 *
 * Regenerate the approved file with:
 *   mvn -q test-compile
 *   java -cp target/classes:target/test-classes com.gildedtros.GildedTrosGoldenMasterTest \
 *        > src/test/resources/com/gildedtros/golden-master.txt
 */
class GildedTrosGoldenMasterTest {

    private static final String APPROVED_RESOURCE = "/com/gildedtros/golden-master.txt";

    private static final String LEGENDARY = "B-DAWG Keychain";

    private static final String[] NAMES = {
            "Ring of Cleansening Code",
            "Elixir of the SOLID",
            "Good Wine",
            LEGENDARY,
            "Backstage passes for Re:Factor",
            "Backstage passes for HAXX",
            "Duplicate Code",
            "Long Methods",
            "Ugly Variable Names"
    };

    private static final int MIN_SELL_IN = -5;
    private static final int MAX_SELL_IN = 15;

    /** Quality is never above 50 per the spec, so inputs above it are out of contract. */
    private static final int[] QUALITIES = {0, 1, 2, 49, 50};

    private static final int[] LEGENDARY_QUALITIES = {0, 80};

    @Test
    @DisplayName("a single day applied across the full input sweep matches the approved output")
    void matchesApprovedOutput() throws IOException {
        assertEquals(readApproved(), generateReport());
    }

    static String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("AXXES CODE KATA - GILDED TROS / golden master\n");
        report.append("one updateQuality() applied to each item\n");
        report.append("before  ->  after   (name, sellIn, quality)\n");

        for (String name : NAMES) {
            report.append("\n-------- ").append(name).append(" --------\n");
            int[] qualities = LEGENDARY.equals(name) ? LEGENDARY_QUALITIES : QUALITIES;
            for (int quality : qualities) {
                for (int sellIn = MIN_SELL_IN; sellIn <= MAX_SELL_IN; sellIn++) {
                    report.append(updateOneDay(name, sellIn, quality)).append('\n');
                }
            }
        }
        return report.toString();
    }

    private static String updateOneDay(String name, int sellIn, int quality) {
        Item item = new Item(name, sellIn, quality);
        String before = item.toString();
        new GildedTros(new Item[]{item}).updateQuality();
        return before + "  ->  " + item.toString();
    }

    private static String readApproved() throws IOException {
        InputStream in = GildedTrosGoldenMasterTest.class.getResourceAsStream(APPROVED_RESOURCE);
        assertNotNull(in, "missing approved file " + APPROVED_RESOURCE);

        StringBuilder content = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append('\n');
            }
        } finally {
            reader.close();
        }
        return content.toString();
    }

    public static void main(String[] args) {
        System.out.print(generateReport());
    }
}
