package org.example;

import java.util.HashMap;
import java.util.Map;

public class ACMGService {

    // parse data and make new instance of geneTable here

    private Map<String, GeneTable> geneTables;
    private Map<String, Map<String, Integer>> dataCounts;

    // { geneName { full Table} )

    public ACMGService(){
        this.geneTables = new HashMap<>();
    }

    public void countData(GeneTable gene) {
        Map<String, Integer> consequenceCounts = dataCounts.get("consequences");
        consequenceCounts.put(gene.getConsequence(), consequenceCounts.getOrDefault(gene.getConsequence(), 0) + 1);

        Map<String, Integer> clinicalSignificanceCounts = dataCounts.get("clinicalSignificance");
        clinicalSignificanceCounts.put(gene.getClinicalSignificance(), clinicalSignificanceCounts.getOrDefault(gene.getClinicalSignificance(), 0) + 1);
    }

    public void countData(String consequence, String clinicalSignificance) {
        Map<String, Integer> consequenceCounts = dataCounts.get("consequences");
        consequenceCounts.put(consequence, consequenceCounts.getOrDefault(consequence, 0) + 1);

        Map<String, Integer> clinicalSignificanceCounts = dataCounts.get("clinicalSignificance");
        clinicalSignificanceCounts.put(clinicalSignificance, clinicalSignificanceCounts.getOrDefault(clinicalSignificance, 0) + 1);
    }



    public void addGeneData(GeneTable geneTable) {
        GeneTable existingGene = geneTables.get(geneTable.getGeneName());

        if (existingGene == null){

            geneTables.put(geneTable.getGeneName(), geneTable);
        }
        else {
            existingGene.combine(geneTable);
        }

    }
    public Map<String, GeneTable> getGeneTables() {
        return geneTables;
    }

}
