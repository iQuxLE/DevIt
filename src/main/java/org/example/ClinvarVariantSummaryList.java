package org.example;

import java.util.ArrayList;
import java.util.List;

public class ClinvarVariantSummaryList {
    private List<ClinvarVariantSummary> variantSummaries;

    public ClinvarVariantSummaryList() {
        variantSummaries = new ArrayList<>();
    }

    public void addVariantSummary(ClinvarVariantSummary variantSummary) {
        variantSummaries.add(variantSummary);
    }

    public List<ClinvarVariantSummary> getVariantSummaries() {
        return variantSummaries;
    }

    public int size() {
        return variantSummaries.size();
    }

    public ClinvarVariantSummary get(int index) {
        return variantSummaries.get(index);
    }

}

