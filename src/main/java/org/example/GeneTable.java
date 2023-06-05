package org.example;

import java.util.HashMap;
import java.util.Map;

public class GeneTable {
    private String geneName;
    private Map<String, Integer> consequenceCounts;
    private Map<String, Map<String, Integer>> consequenceClinicalSignificanceCounts;


    public GeneTable(String geneName) {
        this.geneName = geneName;
        this.consequenceCounts = new HashMap<>();
        this.consequenceClinicalSignificanceCounts = new HashMap<>();

    }

    public String getGeneName() {
        return geneName;
    }

    public void countConsequence(String consequence, String clinicalSignificance) {
        consequenceCounts.put(consequence, consequenceCounts.getOrDefault(consequence, 0) + 1);
        Map<String, Integer> clinicalSignificanceCounts =
                consequenceClinicalSignificanceCounts.getOrDefault(consequence, new HashMap<>());
        clinicalSignificanceCounts.put(clinicalSignificance, clinicalSignificanceCounts.getOrDefault(clinicalSignificance, 0) + 1);
        consequenceClinicalSignificanceCounts.put(consequence, clinicalSignificanceCounts);
    }

    public void combine(GeneTable other) {
        for (Map.Entry<String, Integer> entry : other.consequenceCounts.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();
            consequenceCounts.put(key, consequenceCounts.getOrDefault(key, 0) + value);
        }
        for (Map.Entry<String, Integer> entry : other.consequenceClinicalSignificanceCounts.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();
            consequenceClinicalSignificanceCounts.put(key, consequenceClinicalSignificanceCounts.getOrDefault(key, 0) + value);
        }
    }


    public void printStatistics() {
        int total = consequenceCounts.values().stream().reduce(0, Integer::sum);
        System.out.println("Statistics for gene: " + geneName);
        for (Map.Entry<String, Integer> entry : consequenceCounts.entrySet()) {
            double percentage = 100.0 * entry.getValue() / total;
            System.out.printf("Consequence: %s, Count: %d, Percentage: %.2f%%\n", entry.getKey(), entry.getValue(), percentage);
        }
    }
}


