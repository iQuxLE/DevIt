package org.example;

import java.io.IOException;
import java.util.List;


//public class Main {
//    public static void main(String[] args) throws IOException {
//        String filePath = "/processed_variant_summary.parquet";
//        ParquetFileParser parser = new ParquetFileParser(filePath);
//        ClinvarVariantSummaryList variantList = parser.parseFile();
//        for (int i = 0; i < variantList.size(); i++) {
//            ClinvarVariantSummary variant = variantList.get(i);
//            System.out.println(variant);
//        }
//
//    }
//}

//public class Main {
////    public static void main(String[] args) throws IOException {
////        String filePath = "src/main/resources/variant_summary.txt";
////        ParquetFileParser parser = new ParquetFileParser(filePath);
////        List<ClinvarVariantSummary> variantList = parser.parseFile();
////
////        // Do something with the list of ClinvarVariantSummary objects
////        for (ClinvarVariantSummary variant : variantList) {
////            System.out.println(variant.getName());
////        }
////    }
//
//}


// normal ClinVarData List (all)


//public class Main {
//    public static void main(String[] args) {
//        try {
//            List<ClinVarData> clinVarDataList = ClinVarTxtParser.parseFile();
//            for (ClinVarData data : clinVarDataList) {
//                System.out.println(data);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}


public class Main {
    public static void main(String[] args) {
        {
            try {
                List<ClinVarData> clinVarDataList = ClinVarTxtParser.parseFile();

                for (ClinVarData data : clinVarDataList) {
                    if (!data.getGenomicChange().isEmpty()) {
                        System.out.println("Found ClinVarData object with non-empty genomic change: " + data);
                    }
                }

                long count = clinVarDataList.stream()
                        .filter(data -> !data.getGenomicChange().isEmpty())
                        .count();
                System.out.println("Number of ClinVarData objects with non-empty genomic change: " + count);

            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                GeneParser geneparser = new GeneParser();
                List<GeneTable> geneTables = geneparser.readData("src/main/resources/first_5000_lines.txt");
                for (GeneTable geneTable : geneTables) {
                    geneTable.printStatistics();
                }

            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}





