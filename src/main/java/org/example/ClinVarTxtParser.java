package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClinVarTxtParser {

//    #AlleleID	Type	Name	GeneID	GeneSymbol	HGNC_ID	ClinicalSignificance	ClinSigSimple	LastEvaluated	RS# (dbSNP)	nsv/esv (dbVar)	RCVaccession	PhenotypeIDS	PhenotypeList	Origin	OriginSimple	Assembly	ChromosomeAccession	Chromosome	Start	Stop	ReferenceAllele	AlternateAllele	Cytogenetic	ReviewStatus	NumberSubmitters	Guidelines	TestedInGTR	OtherIDs	SubmitterCategories	VariationID	PositionVCF	ReferenceAlleleVCF	AlternateAlleleVCF
//15041	Indel	NM_014855.3(AP5Z1):c.80_83delinsTGCTGTAAACTGTAACTGTAAA (p.Arg27_Ile28delinsLeuLeuTer)	9907	AP5Z1	HGNC:22197	Pathogenic	1	-	397704705	-	RCV000000012	MONDO:MONDO:0013342,MedGen:C3150901,OMIM:613647,Orphanet:306511	Hereditary spastic paraplegia 48	germline;unknown	germline	GRCh37	NC_000007.13	7	4820844	4820847	na	na	7p22.1	criteria provided, single submitter	2	-	N	ClinGen:CA215070,OMIM:613653.0001	3	2	4820844	GGAT	TGCTGTAAACTGTAACTGTAAA
//15041	Indel	NM_014855.3(AP5Z1):c.80_83delinsTGCTGTAAACTGTAACTGTAAA (p.Arg27_Ile28delinsLeuLeuTer)	9907	AP5Z1	HGNC:22197	Pathogenic	1	-	397704705	-	RCV000000012	MONDO:MONDO:0013342,MedGen:C3150901,OMIM:613647,Orphanet:306511	Hereditary spastic paraplegia 48	germline;unknown	germline	GRCh38	NC_000007.14	7	4781213	4781216	na	na	7p22.1	criteria provided, single submitter	2	-	N	ClinGen:CA215070,OMIM:613653.0001	3	2	4781213	GGAT	TGCTGTAAACTGTAACTGTAAA
//15042	Deletion	NM_014855.3(AP5Z1):c.1413_1426del (p.Leu473fs)	9907	AP5Z1	HGNC:22197	Pathogenic	1	Jun 29, 2010	397704709	-	RCV000000013	MONDO:MONDO:0013342,MedGen:C3150901,OMIM:613647,Orphanet:306511	Hereditary spastic paraplegia 48	germline	germline	GRCh37	NC_000007.13	7	4827361	4827374	na	na	7p22.1	no assertion criteria provided	1	-	N	ClinGen:CA215072,OMIM:613653.0002	1	3	4827360	GCTGCTGGACCTGCC	G
//15042	Deletion	NM_014855.3(AP5Z1):c.1413_1426del (p.Leu473fs)	9907	AP5Z1	HGNC:22197	Pathogenic	1	Jun 29, 2010	397704709	-	RCV000000013	MONDO:MONDO:0013342,MedGen:C3150901,OMIM:613647,Orphanet:306511	Hereditary spastic paraplegia 48	germline	germline	GRCh38	NC_000007.14	7	4787730	4787743	na	na	7p22.1	no assertion criteria provided	1	-	N	ClinGen:CA215072,OMIM:613653.0002	1	3	4787729	GCTGCTGGACCTGCC	G

    public static List<ClinVarData> parseFile() throws IOException {
        List<ClinVarData> ClinVarDatalist  = new ArrayList<>();
        FileInputStream inputStream = null;
        Scanner sc = null;
        try {
            inputStream = new FileInputStream("src/main/resources/variant_summary.txt");
            sc = new Scanner(inputStream, "UTF-8");
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] fields = line.split("\t");

                ClinVarData.Builder builder = ClinVarData.builder();

                builder.alleleId(fields[0]) // currently saved as string maybe change but earlier connected class also string
                        .type(fields[1])
                        .name(fields[2])
                        .geneId(fields[3])
                        .geneSymbol(fields[4])
                        .hgnc_id(fields[5]);

                String name = fields[2];
                String proteinChange = extractProteinChange(name);
                String cdnaChange = extractCdnaChange(name);
                String genomicChange = extractGenomicChange(name);

                if (proteinChange != null) {
                    builder.setProteinChange(proteinChange);
                }
                if (cdnaChange != null) {
                    builder.setCdnaChange(cdnaChange);
                }
                if (genomicChange != null) {
                    builder.setGenomicChange(genomicChange);
                }

                ClinVarData clinVarData = builder.build();
                ClinVarDatalist.add(clinVarData);
                ClinVarDatalist.add(builder.build());

            }
            if (sc.ioException() != null) {
                throw sc.ioException();
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (sc != null) {
                sc.close();
            }
        }
        return ClinVarDatalist; // Return the list of ClinVarData objects
    }

    private static String extractProteinChange(String name) {
        Matcher matcher = Pattern.compile("(p\\.\\S+)").matcher(name);
        return matcher.find() ? matcher.group() : null;
    }

    private static String extractCdnaChange(String name) {
        Matcher matcher = Pattern.compile("(c\\.\\S+)").matcher(name);
        return matcher.find() ? matcher.group() : null;
    }

    private static String extractGenomicChange(String name) {
        Matcher matcher = Pattern.compile("(g\\.\\S+)").matcher(name);
        return matcher.find() ? matcher.group() : null;
    }
}

