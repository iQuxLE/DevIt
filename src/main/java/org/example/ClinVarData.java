/*
 * The Exomiser - A tool to annotate and prioritize genomic variants
 *
 * Copyright (c) 2016-2021 Queen Mary University of London.
 * Copyright (c) 2012-2016 Charité Universitätsmedizin Berlin and Genome Research Ltd.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.example;

//package org.monarchinitiative.exomiser.core.model.pathogenicity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;

import java.util.*;

/**
 * Immutable data class representing data from the <a href=https://www.ncbi.nlm.nih.gov/clinvar/>ClinVar resource</a>, with
 * explanation of the data from <a href=https://www.ncbi.nlm.nih.gov/clinvar/docs/clinsig/>https://www.ncbi.nlm.nih.gov/clinvar/docs/clinsig/</a>
 *
 * @author Jules Jacobsen <j.jacobsen@qmul.ac.uk>
 * @since 10.1.0
 */
public class ClinVarData {

    private static final ClinVarData EMPTY = new Builder().build();

    public enum ClinSig {
        // ACMG/AMP-based
        BENIGN,
        BENIGN_OR_LIKELY_BENIGN,
        LIKELY_BENIGN,
        UNCERTAIN_SIGNIFICANCE,
        LIKELY_PATHOGENIC,
        PATHOGENIC_OR_LIKELY_PATHOGENIC,
        PATHOGENIC,
        CONFLICTING_PATHOGENICITY_INTERPRETATIONS,
        //Non-ACMG-based
        AFFECTS,
        ASSOCIATION,
        DRUG_RESPONSE,
        NOT_PROVIDED,
        OTHER,
        PROTECTIVE,
        RISK_FACTOR
    }

    //https://www.ncbi.nlm.nih.gov/clinvar/?term=99222[alleleid]
    private final String alleleId;
    private final ClinSig primaryInterpretation;
    private final Set<ClinSig> secondaryInterpretations;

    private final String reviewStatus;
    private final Map<String, ClinSig> includedAlleles;

    private final String type;
    private final String hgnc_id;
    private final String gene_id;
    private final String name;
    private final String geneSymbol;
    private final String proteinChange;
    private final String cdnaChange;
    private final String genomicChange;

//    private final String proteinChange;
//    private final String proteinExpression;
//
//    private final String rcvAccession;



    // https://www.medschool.umaryland.edu/Genetic_Variant_Interpretation_Tool1.html/
    // BP1, Missense variant in a gene for which primarily truncating variants are known to cause disease
    // BP2, Observed in trans with a pathogenic variant for a fully penetrant dominant gene/disorder or observed in cis with a pathogenic variant in any inheritance pattern
    // BP6, * Reputable source recently reports variant as benign, but the evidence is not available to the laboratory to perform an independent evaluation
    // BS1, Allele frequency is greater than expected for disorder
    // ---
    // PS1, Same amino acid change as a previously established pathogenic variant regardless of nucleotide change
    // PM5, Novel missense change at an amino acid residue where a different missense change determined to be pathogenic has been seen before
    // PP2, Missense variant in a gene that has a low rate of benign missense variation and in which missense variants are a common mechanism of disease
    // PP5, * Reputable source recently reports variant as pathogenic, but the evidence is not available to the laboratory to perform an independent evaluation
    //
    // - needs link to gene, disease and amino acid change on variant. Could run 2*+ through Jannovar and annotation pipeline.
    // Need to collect hets and homs for variant in gene
    // TODO: add GENEINFO (geneSymbol:ncbiGeneId) and
    //  CLNDISDB=MedGen:C0006142,OMIM:114480,Orphanet:ORPHA227535,SNOMED_CT:254843006|MedGen:C0027672,SNOMED_CT:699346009
    //  22	29130684	460820	G	C	.	.	ALLELEID=471879;CLNDISDB=MedGen:C0006142,OMIM:114480,Orphanet:ORPHA227535,SNOMED_CT:254843006|MedGen:C0027672,SNOMED_CT:699346009;CLNDN=Familial_cancer_of_breast|Hereditary_cancer-predisposing_syndrome;CLNHGVS=NC_000022.10:g.29130684G>C;CLNREVSTAT=criteria_provided,_multiple_submitters,_no_conflicts;CLNSIG=Uncertain_significance;CLNVC=single_nucleotide_variant;CLNVCSO=SO:0001483;GENEINFO=CHEK2:11200;MC=SO:0001583|missense_variant,SO:0001623|5_prime_UTR_variant;ORIGIN=1;RS=1456931393
    //  10	123357783	299024	G	T	.	.	AF_TGP=0.01717;ALLELEID=320947;CLNDISDB=Human_Phenotype_Ontology:HP:0001363,MedGen:C1849943|Human_Phenotype_Ontology:HP:0004439,MeSH:D003394,MedGen:C2931196,OMIM:123500,Orphanet:ORPHA207,SNOMED_CT:28861008|MedGen:C0001193,OMIM:101200,Orphanet:ORPHA87,SNOMED_CT:205258009|MedGen:C0175699,OMIM:101400,Orphanet:ORPHA794,SNOMED_CT:83015004|MedGen:C0220658,OMIM:101600,Orphanet:ORPHA710,Orphanet:ORPHA93258,Orphanet:ORPHA93259|MedGen:C0265269,OMIM:149730,Orphanet:ORPHA2363,SNOMED_CT:23817003|MedGen:C0795998,OMIM:123150,Orphanet:ORPHA1540|MedGen:C1852406,OMIM:123790,Orphanet:ORPHA1555|MedGen:CN043619;CLNDN=Craniosynostosis|Crouzon_syndrome|Acrocephalosyndactyly_type_I|Saethre-Chotzen_syndrome|Pfeiffer_syndrome|Levy-Hollister_syndrome|Jackson-Weiss_syndrome|Cutis_Gyrata_syndrome_of_Beare_and_Stevenson|Isolated_coronal_synostosis;CLNHGVS=NC_000010.10:g.123357783G>T;CLNREVSTAT=criteria_provided,_single_submitter;CLNSIG=Likely_benign;CLNVC=single_nucleotide_variant;CLNVCSO=SO:0001483;CLNVI=Illumina_Clinical_Services_Laboratory,Illumina:288098;GENEINFO=FGFR2:2263;MC=SO:0001619|non-coding_transcript_variant,SO:0001623|5_prime_UTR_variant;ORIGIN=1;RS=41301043
    //  ##fileformat=VCFv4.1
    //##fileDate=2020-03-02
    //##source=ClinVar
    //##reference=GRCh37
    //##ID=<Description="ClinVar Variation ID">
    //##INFO=<ID=AF_ESP,Number=1,Type=Float,Description="allele frequencies from GO-ESP">
    //##INFO=<ID=AF_EXAC,Number=1,Type=Float,Description="allele frequencies from ExAC">
    //##INFO=<ID=AF_TGP,Number=1,Type=Float,Description="allele frequencies from TGP">
    //##INFO=<ID=ALLELEID,Number=1,Type=Integer,Description="the ClinVar Allele ID">
    //##INFO=<ID=CLNDN,Number=.,Type=String,Description="ClinVar's preferred disease name for the concept specified by disease identifiers in CLNDISDB">
    //##INFO=<ID=CLNDNINCL,Number=.,Type=String,Description="For included Variant : ClinVar's preferred disease name for the concept specified by disease identifiers in CLNDISDB">
    //##INFO=<ID=CLNDISDB,Number=.,Type=String,Description="Tag-value pairs of disease database name and identifier, e.g. OMIM:NNNNNN">
    //##INFO=<ID=CLNDISDBINCL,Number=.,Type=String,Description="For included Variant: Tag-value pairs of disease database name and identifier, e.g. OMIM:NNNNNN">
    //##INFO=<ID=CLNHGVS,Number=.,Type=String,Description="Top-level (primary assembly, alt, or patch) HGVS expression.">
    //##INFO=<ID=CLNREVSTAT,Number=.,Type=String,Description="ClinVar review status for the Variation ID">
    //##INFO=<ID=CLNSIG,Number=.,Type=String,Description="Clinical significance for this single variant">
    //##INFO=<ID=CLNSIGCONF,Number=.,Type=String,Description="Conflicting clinical significance for this single variant">
    //##INFO=<ID=CLNSIGINCL,Number=.,Type=String,Description="Clinical significance for a haplotype or genotype that includes this variant. Reported as pairs of VariationID:clinical significance.">
    //##INFO=<ID=CLNVC,Number=1,Type=String,Description="Variant type">
    //##INFO=<ID=CLNVCSO,Number=1,Type=String,Description="Sequence Ontology id for variant type">
    //##INFO=<ID=CLNVI,Number=.,Type=String,Description="the variant's clinical sources reported as tag-value pairs of database and variant identifier">
    //##INFO=<ID=DBVARID,Number=.,Type=String,Description="nsv accessions from dbVar for the variant">
    //##INFO=<ID=GENEINFO,Number=1,Type=String,Description="Gene(s) for the variant reported as gene symbol:gene id. The gene symbol and id are delimited by a colon (:) and each pair is delimited by a vertical bar (|)">
    //##INFO=<ID=MC,Number=.,Type=String,Description="comma separated list of molecular consequence in the form of Sequence Ontology ID|molecular_consequence">
    //##INFO=<ID=ORIGIN,Number=.,Type=String,Description="Allele origin. One or more of the following values may be added: 0 - unknown; 1 - germline; 2 - somatic; 4 - inherited; 8 - paternal; 16 - maternal; 32 - de-novo; 64 - biparental; 128 - uniparental; 256 - not-tested; 512 - tested-inconclusive; 1073741824 - other">
    //##INFO=<ID=RS,Number=.,Type=String,Description="dbSNP ID (i.e. rs number)">
    //##INFO=<ID=SSR,Number=1,Type=Integer,Description="Variant Suspect Reason Codes. One or more of the following values may be added: 0 - unspecified, 1 - Paralog, 2 - byEST, 4 - oldAlign, 8 - Para_EST, 16 - 1kg_failed, 1024 - other">


    private ClinVarData(Builder builder) {
        this.alleleId = builder.alleleId;
        this.primaryInterpretation = builder.primaryInterpretation;
        this.secondaryInterpretations = Sets.immutableEnumSet(builder.secondaryInterpretations);
        this.reviewStatus = builder.reviewStatus.replace("_", " ");
        this.includedAlleles = ImmutableMap.copyOf(builder.includedAlleles);
        this.type = builder.type;
        this.hgnc_id = builder.hgnc_id;
        this.name = builder.name;
        this.gene_id = builder.geneId;
        this.geneSymbol = builder.geneSymbol;
        this.proteinChange = builder.proteinChange;
        this.genomicChange = builder.genomicChange;
        this.cdnaChange= builder.cdnaChange;
    }

//    public static List<ClinVarData> findPathogenicVariantsByGene(String geneId, List<ClinVarData> clinVarDataList) {
//        return clinVarDataList.stream()
//                .filter(data -> data.geneId.equalsIgnoreCase(geneId))
//                .filter(data -> data.primaryInterpretation == ClinSig.PATHOGENIC || data.primaryInterpretation == ClinSig.LIKELY_PATHOGENIC)
//                .collect(Collectors.toList());
//    }

    public String getProteinChange() {
        return proteinChange;
    }

    public String getCdnaChange() {
        return cdnaChange;
    }

    public String getGenomicChange() {
        return genomicChange;
    }

    public static ClinVarData empty() {
        return EMPTY;
    }

    @JsonIgnore
    public boolean isEmpty() {
        return this.equals(EMPTY);
    }

    public String getAlleleId() {
        return alleleId;
    }


    public ClinSig getPrimaryInterpretation() {
        return primaryInterpretation;
    }

    public Set<ClinSig> getSecondaryInterpretations() {
        return secondaryInterpretations;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public Map<String, ClinSig> getIncludedAlleles() {
        return includedAlleles;
    }

    /**
     * @return true if the secondary CLNSIG contains one of 'affects', 'other', 'association', 'risk factor' or
     * 'protective'. These are considered unimportant from the mendelian disease perspective. The category 'drug response'
     * is *not* included here as these are also associated with CFTR alleles known to be pathogenic/likely pathogenic
     * for CF.
     * @since 13.0.0
     */
    @JsonIgnore
    public boolean isSecondaryAssociationRiskFactorOrOther() {
        for (ClinSig secondaryClinSig : secondaryInterpretations) {
            switch (secondaryClinSig) {
                case AFFECTS:
                case OTHER:
                case ASSOCIATION:
                case RISK_FACTOR:
                case PROTECTIVE:
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }

    /**
     * Returns the ClinVar star rating according to the criteria provided at
     * https://www.ncbi.nlm.nih.gov/clinvar/docs/review_status/#revstat_def
     * <p>
     * In the VCF CLNREVSTAT the start ratings are mapped as follows:
     * <p>
     * 1* criteria_provided,_conflicting_interpretations
     * 1* criteria_provided,_single_submitter
     * 2* criteria_provided,_multiple_submitters,_no_conflicts
     * 3* reviewed_by_expert_panel
     * 4* practice_guideline
     *
     * @return an integer value between 0 (worst) and 4 (best)
     * @since 13.0.0
     */
    public int starRating() {
        switch (reviewStatus) {
            case "criteria provided, single submitter":
            case "criteria provided, conflicting interpretations":
                return 1;
            case "criteria provided, multiple submitters, no conflicts":
                return 2;
            case "reviewed by expert panel":
                return 3;
            case "practice guideline":
                return 4;
            default:
                return 0;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClinVarData that = (ClinVarData) o;
        return Objects.equals(alleleId, that.alleleId) &&
                primaryInterpretation == that.primaryInterpretation &&
                Objects.equals(secondaryInterpretations, that.secondaryInterpretations) &&
                Objects.equals(reviewStatus, that.reviewStatus) &&
                Objects.equals(includedAlleles, that.includedAlleles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alleleId, primaryInterpretation, secondaryInterpretations, reviewStatus, includedAlleles);
    }

    @Override
    public String toString() {
        return "ClinVarData{" +
                "alleleId='" + alleleId + '\'' +
                ", primaryInterpretation=" + primaryInterpretation +
                ", secondaryInterpretations=" + secondaryInterpretations +
                ", reviewStatus='" + reviewStatus + '\'' +
                ", includedAlleles=" + includedAlleles +
                ", type='" + type + '\'' +
                ", hgnc_id='" + hgnc_id + '\'' +
                ", name='" + name + '\'' +
                ", gene_id='" + gene_id + '\'' +
                ", geneSymbol='" + geneSymbol + '\'' +
                ", proteinChange='" + proteinChange + '\'' +
                ", genomicChange='" + genomicChange + '\'' +
                ", cdnaChange='" + cdnaChange + '\'' +
                '}';
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String alleleId = "";
        //  dont change empty String is better than 0
        private String type = "";
        private String name = "";
        private String geneId = "";

        private String geneSymbol = "";
        private String hgnc_id = "";
        private String rcvaccession = "";
        private String assembly = "";
        private String chromosome = "";
        private String start = "";

        private String stop = "";
        private String referenceAllele = "";
        private String alternateAllele = "";
        private String cytogenetic = "";
        private String variationID = "";
        private String positionVCF = "";
        private String referenceAlleleVCF = "";
        private String alternateAlleleVCF = "";
        private String proteinChange = "";
        private String genomicChange  = "";
        private String cdnaChange = "";


        private ClinSig primaryInterpretation = ClinSig.NOT_PROVIDED;
        private Set<ClinSig> secondaryInterpretations = EnumSet.noneOf(ClinSig.class);

        private String reviewStatus = "";
        private Map<String, ClinSig> includedAlleles = Collections.emptyMap();

        public Builder setProteinChange(String proteinChangeValue) {
            Objects.requireNonNull(proteinChangeValue);
            this.proteinChange = proteinChangeValue;
            return this;
        }

        public Builder setCdnaChange(String cdnaChangeValue) {
            Objects.requireNonNull(cdnaChangeValue);
            this.cdnaChange = cdnaChangeValue;
            return this;
        }

        public Builder setGenomicChange(String genomicChangeValue) {
            Objects.requireNonNull(genomicChangeValue);
            this.genomicChange = genomicChangeValue;
            return this;
        }

        public Builder proteinChange(String proteinChange) {
            Objects.requireNonNull(proteinChange);
            this.proteinChange = proteinChange;
            return this;
        }

        public Builder cdnaChange(String cdnaChange) {
            Objects.requireNonNull(cdnaChange);
            this.cdnaChange = cdnaChange;
            return this;
        }

        public Builder genomicChange(String genomicChange) {
            Objects.requireNonNull(genomicChange);
            this.genomicChange = genomicChange;
            return this;
        }

        public Builder alleleId(String alleleId) {
            Objects.requireNonNull(alleleId);
            this.alleleId = alleleId;
            return this;
        }

        public Builder type(String type) {
            Objects.requireNonNull(type);
            this.type = type;
            return this;
        }

        public Builder name(String name) {
            Objects.requireNonNull(name);
            this.name = name;
            return this;
        }

        public Builder geneId(String geneId) {
            Objects.requireNonNull(geneId);
            this.geneId = geneId;
            return this;
        }

        public Builder hgnc_id(String hgnc_id) {
            Objects.requireNonNull(hgnc_id);
            this.hgnc_id = hgnc_id;
            return this;
        }

        public Builder rcvaccession(String rcvaccession) {
            Objects.requireNonNull(rcvaccession);
            this.rcvaccession = rcvaccession;
            return this;
        }

        public Builder assembly(String assembly) {
            Objects.requireNonNull(assembly);
            this.assembly = assembly;
            return this;
        }

        public Builder chromosome(String chromosome) {
            Objects.requireNonNull(chromosome);
            this.chromosome = chromosome;
            return this;
        }

        public Builder start(String start) {
            Objects.requireNonNull(start);
            this.start = start;
            return this;
        }

        public Builder stop(String stop) {
            Objects.requireNonNull(stop);
            this.stop = stop;
            return this;
        }

        public Builder geneSymbol(String geneSymbol) {
            Objects.requireNonNull(geneSymbol);
            this.geneSymbol = geneSymbol;
            return this;
        }

        public Builder cytogenetic(String cytogenetic){
            Objects.requireNonNull(cytogenetic);
            this.cytogenetic = cytogenetic;
            return this;
        }

        public Builder alternateAllele(String alternateAllele){
            Objects.requireNonNull(alternateAllele);
            this.alternateAllele = alternateAllele;
            return this;
        }
//
//        public Builder VariationID(String VariationID){
//            Objects.requireNonNull(VariationID);
//            this.VariationID = VariationID;
//            return this;
//        }
//        public Builder PositionVCF(String PositionVCF){
//            Objects.requireNonNull(PositionVCF);
//            this.PositionVCF = PositionVCF;
//            return this;
//        }
//
//        public Builder primaryInterpretation(ClinSig primaryInterpretation) {
//            Objects.requireNonNull(primaryInterpretation);
//            this.primaryInterpretation = primaryInterpretation;
//            return this;
//        }
//
//        public Builder ReferenceAlleleVCF(String ReferenceAlleleVCF){
//            Objects.requireNonNull(ReferenceAlleleVCF);
//            this.ReferenceAlleleVCF = ReferenceAlleleVCF;
//            return this;
//        }
//
//        public Builder AlternateAlleleVCF(String AlternateAlleleVCF){
//            Objects.requireNonNull(AlternateAlleleVCF);
//            this.AlternateAlleleVCF = AlternateAlleleVCF;
//            return this;
//        }

        public Builder secondaryInterpretations(Set<ClinSig> secondaryInterpretations) {
            Objects.requireNonNull(secondaryInterpretations);
            this.secondaryInterpretations = secondaryInterpretations;
            return this;
        }

        public Builder reviewStatus(String reviewStatus) {
            Objects.requireNonNull(reviewStatus);
            this.reviewStatus = reviewStatus;
            return this;
        }

        public Builder includedAlleles(Map<String, ClinSig> includedAlleles) {
            Objects.requireNonNull(includedAlleles);
            this.includedAlleles = includedAlleles;
            return this;
        }

        public ClinVarData build() {
            return new ClinVarData(this);
        }

    }


//    private ClinSig parseClinSig(String clinsig) {
//        // Unique CLNSIG counts
//        // Affects=100, Benign=23963, Benign/Likely_benign=10827, Conflicting_interpretations_of_pathogenicity=12784,
//        // Likely_benign=52064, Likely_pathogenic=15127, Pathogenic=46803, Pathogenic/Likely_pathogenic=3278,
//        // Uncertain_significance=120418, association=148, drug_response=290, not_provided=10980, other=1796, protective=30,
//        // risk_factor=411
//        switch (clinsig) {
//            case "Uncertain_significance":
//                return UNCERTAIN_SIGNIFICANCE;
//            case "Benign":
//                return BENIGN;
//            case "Benign/Likely_benign":
//                return BENIGN_OR_LIKELY_BENIGN;
//            case "Likely_benign":
//                return LIKELY_BENIGN;
//            case "Conflicting_interpretations_of_pathogenicity":
//                return CONFLICTING_PATHOGENICITY_INTERPRETATIONS;
//            case "Likely_pathogenic":
//                return LIKELY_PATHOGENIC;
//            case "Pathogenic/Likely_pathogenic":
//                return PATHOGENIC_OR_LIKELY_PATHOGENIC;
//            case "Pathogenic":
//                return PATHOGENIC;
//            case "Affects":
//                return AFFECTS;
//            case "association":
//                return ASSOCIATION;
//            case "drug_response":
//                return DRUG_RESPONSE;
//            case "other":
//                return OTHER;
//            case "protective":
//                return PROTECTIVE;
//            case "risk_factor":
//                return RISK_FACTOR;
//            case "not_provided":
//            default:
//                return NOT_PROVIDED;
//        }
//    }
}

