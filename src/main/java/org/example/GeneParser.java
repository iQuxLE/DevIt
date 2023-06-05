package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/*
    ######## VARIANT INDEX ########
Gene Name  	AC         	Variant AA Change   	Source DB ID    	Consequence Type        	Clinical Significance 	Phenotype/Disease                       	Phenotype/Disease Source  	Cytogenetic Band  	Chromosome Coordinate 	Ensembl gene ID                 	Ensembl transcript ID           	Ensembl translation ID          	Evidence
___________	___________	____________________	________________	________________________	______________________	________________________________________	__________________________	__________________	______________________	________________________________	________________________________	________________________________	________________________________
NSRP1	A0A024QZ33	p.Lys2Glu	rs965203080	missense variant	-	-	-	17q11.2	NC_000017.11:g.30172593A>G	ENSG00000126653	ENST00000612959	ENSP00000477862	gnomAD,ClinGen
NSRP1	A0A024QZ33	p.Lys5Gln	rs562675606	missense variant	-	-	-	17q11.2	NC_000017.11:g.30178074A>C	ENSG00000126653	ENST00000612959	ENSP00000477862	TOPMed,ClinGen
NSRP1	A0A024QZ33	p.Lys5Glu	rs562675606	missense variant	-	-	-	17q11.2	NC_000017.11:g.30178074A>G	ENSG00000126653	ENST00000612959	ENSP00000477862	TOPMed,ClinGen
NSRP1	A0A024QZ33	p.Leu6Pro	rs1471378657	missense variant	-	-	-	17q11.2	NC_000017.11:g.30178078T>C	ENSG00000126653	ENST00000612959	ENSP00000477862	TOPMed,ClinGen
NSRP1	A0A024QZ33	p.Lys10Arg	rs1375824145	missense variant	-	-	-	17q11.2	NC_000017.11:g.30178090A>G	ENSG00000126653	ENST00000612959	ENSP00000477862	gnomAD,ClinGen
NSRP1	A0A024QZ33	p.Lys10Asn	rs1005041322	missense variant	-	-	-	17q11.2	NC_000017.11:g.30178091G>C	ENSG00000126653	ENST00000612959	ENSP00000477862	TOPMed,gnomAD,ClinGen
NSRP1	A0A024QZ33	p.Ala13Thr	rs1306071001	missense variant	-	-	-	17q11.2	NC_000017.11:g.30178098G>A	ENSG00000126653	ENST00000612959	ENSP00000477862	gnomAD,ClinGen

 */

public class GeneParser {

//    private final String filename = "homo_sapiens_original.txt";


    public List<GeneTable> readData(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line = reader.readLine();

        // Skip all lines until "#"
        do {
            line = reader.readLine();
        } while (line != null && !line.startsWith("#"));

        // Now, 'line' should be the header line that starts with "#"
        // The next two lines should be skipped
        reader.readLine(); // col1,col2
        reader.readLine(); // AAchange, name etc .

        List<GeneTable> geneTables = new ArrayList<>();
        GeneTable currentGene = null;
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split("\t");
            if (currentGene == null || !currentGene.getGeneName().equals(fields[0])) {
                currentGene = new GeneTable(fields[0]);
                geneTables.add(currentGene);
            }
            currentGene.countConsequence(fields[4]);
        }
        reader.close();
        return geneTables;
    }
}
