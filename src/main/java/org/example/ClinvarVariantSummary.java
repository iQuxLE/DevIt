package org.example;

public class ClinvarVariantSummary {
    long alleleID;
    String type;
    String name;
    long variationID;
    long geneID;
    String geneSymbol;
    String chromosome;
    long start;
    long stop;
    String clinicalSignificance;

    String reviewStatus;
    String proteinChange;
    String cdnaChange;
    String genomicChange;

    public ClinvarVariantSummary() {

    }

//    ClinvarVariantSummary() {}

    // Getters
    public long getAlleleID() {
        return alleleID;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public long getVariationID() {
        return variationID;
    }

    public long getGeneID() {
        return geneID;
    }

    public String getGeneSymbol() {
        return geneSymbol;
    }

    public String getChromosome() {
        return chromosome;
    }

    public long getStart() {
        return start;
    }

    public long getStop() {
        return stop;
    }

    public String getClinicalSignificance() {
        return clinicalSignificance;
    }

    public String getReviewStatus() { return reviewStatus; }

    public String getProteinChange() {
        return proteinChange;
    }

    public String getCdnaChange() {
        return cdnaChange;
    }

    public String getGenomicChange() {
        return genomicChange;
    }


    public static class Builder {
        private Long alleleID;
        private String type;
        private String name;
        private Long variationID;
        private Long geneID;
        private String geneSymbol;
        private String chromosome;
        private Long start;
        private Long stop;
        private String clinicalSignificance;
        private String reviewStatus;

        private String proteinChange;
        private String cdnaChange;
        private String genomicChange;

        public Builder() {
        }

        public Builder setAlleleID(Long alleleID) {
            this.alleleID = alleleID;
            return this;
        }

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setVariationID(Long variationID) {
            this.variationID = variationID;
            return this;
        }

        public Builder setGeneID(Long geneID) {
            this.geneID = geneID;
            return this;
        }

        public Builder setGeneSymbol(String geneSymbol) {
            this.geneSymbol = geneSymbol;
            return this;
        }

        public Builder setChromosome(String chromosome) {
            this.chromosome = chromosome;
            return this;
        }

        public Builder setStart(Long start) {
            this.start = start;
            return this;
        }

        public Builder setStop(Long stop) {
            this.stop = stop;
            return this;
        }

        public Builder setClinicalSignificance(String clinicalSignificance) {
            this.clinicalSignificance = clinicalSignificance;
            return this;
        }

        public Builder setReviewStatus(String reviewStatus) {
            this.reviewStatus = reviewStatus;
            return this;
        }

        public Builder setProteinChange(String proteinChange) {
            this.proteinChange = proteinChange;
            return this;
        }

        public Builder setCdnaChange(String cdnaChange) {
            this.cdnaChange = cdnaChange;
            return this;
        }

        public Builder setGenomicChange(String genomicChange) {
            this.genomicChange = genomicChange;
            return this;
        }

        public ClinvarVariantSummary build() {
            ClinvarVariantSummary variant = new ClinvarVariantSummary();
            variant.alleleID = this.alleleID;
            variant.type = this.type;
            variant.name = this.name;
            variant.variationID = this.variationID;
            variant.geneID = this.geneID;
            variant.geneSymbol = this.geneSymbol;
            variant.chromosome = this.chromosome;
            variant.start = this.start;
            variant.stop = this.stop;
            variant.clinicalSignificance = this.clinicalSignificance;
            variant.proteinChange = this.proteinChange;
            variant.cdnaChange = this.cdnaChange;
            variant.genomicChange = this.genomicChange;
            return variant;
        }

    }


}



