package com.gildedtros;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Pins the observable behaviour of updateQuality() across the whole legal input space,
 * so the refactoring underneath it is provably behaviour-preserving.
 *
 * Regenerate the approved file with:
 *   mvn -q test-compile
 *   java -cp target/classes:target/test-classes com.gildedtros.GildedTrosGoldenMasterTest \
 *        > src/test/resources/com/gildedtros/golden-master.txt
 *
 * Always review the resulting diff: regenerating without reading it turns the safety
 * net into a rubber stamp.
 */
class GildedTrosGoldenMasterTest {

    private static final String APPROVED_REPORT_RESOURCE = "/com/gildedtros/golden-master.txt";

    private static final String LEGENDARY_ITEM_NAME = "B-DAWG Keychain";

    private static final String[] ITEM_NAMES = {
            "Ring of Cleansening Code",
            "Elixir of the SOLID",
            "Good Wine",
            LEGENDARY_ITEM_NAME,
            "Backstage passes for Re:Factor",
            "Backstage passes for HAXX",
            "Duplicate Code",
            "Long Methods",
            "Ugly Variable Names"
    };

    private static final int MIN_SELL_IN = -5;
    private static final int MAX_SELL_IN = 15;

    /** Quality is never above 50 per the spec, so inputs above it are out of contract. */
    private static final int[] QUALITY_VALUES = {0, 1, 2, 49, 50};

    private static final int[] LEGENDARY_QUALITY_VALUES = {0, 80};

    @Test
    @DisplayName("a single day applied across the full input sweep matches the approved output")
    void matchesApprovedReport() throws IOException {
        assertLinesMatch(readApprovedReportLines(), linesOf(generateReport()));
    }

    static String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("AXXES CODE KATA - GILDED TROS / golden master\n");
        report.append("one updateQuality() applied to each item\n");
        report.append("before  ->  after   (name, sellIn, quality)\n");

        for (String name : ITEM_NAMES) {
            report.append("\n-------- ").append(name).append(" --------\n");
            int[] qualityValues = qualityValuesFor(name);
            for (int quality : qualityValues) {
                for (int sellIn = MIN_SELL_IN; sellIn <= MAX_SELL_IN; sellIn++) {
                    report.append(renderOneDayFor(name, sellIn, quality)).append('\n');
                }
            }
        }
        return report.toString();
    }

    private static int[] qualityValuesFor(String name) {
        return LEGENDARY_ITEM_NAME.equals(name) ? LEGENDARY_QUALITY_VALUES : QUALITY_VALUES;
    }

    private static String renderOneDayFor(String name, int sellIn, int quality) {
        Item item = new Item(name, sellIn, quality);
        String before = item.toString();
        new GildedTros(new Item[]{item}).updateQuality();
        return before + "  ->  " + item.toString();
    }

    private static List<String> linesOf(String report) {
        return Arrays.asList(report.split("\n"));
    }

    private static List<String> readApprovedReportLines() throws IOException {
        InputStream approvedReport =
                GildedTrosGoldenMasterTest.class.getResourceAsStream(APPROVED_REPORT_RESOURCE);
        assertNotNull(approvedReport, "missing approved file " + APPROVED_REPORT_RESOURCE);

        List<String> lines = new ArrayList<String>();
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(approvedReport, StandardCharsets.UTF_8));
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } finally {
            reader.close();
        }
        return lines;
    }

    public static void main(String[] args) {
        System.out.print(generateReport());
    }
}
