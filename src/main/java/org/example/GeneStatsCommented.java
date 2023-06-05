package org.example;//package org.example;
//
//import java.io.*;
//import java.util.*;
//
//public class GeneStats {
//    private Map<String, PrintWriter> geneFiles = new HashMap<>();
//
//    public void splitFileByGene(String filename) throws IOException {
//        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] fields = line.split("\t");
//                String gene = fields[0];
//                PrintWriter writer = geneFiles.computeIfAbsent(gene, g -> {
//                    try {
//                        return new PrintWriter(g + ".txt");
//                    } catch (IOException e) {
//                        throw new UncheckedIOException(e);
//                    }
//                });
//                writer.println(line);
//            }
//        }
//        for (PrintWriter writer : geneFiles.values()) {
//            writer.close();
//        }
//    }
//
//    public void calculateStats() throws IOException {
//        for (String gene : geneFiles.keySet()) {
//            Map<String, Integer> counts = new HashMap<>();
//            try (BufferedReader reader = new BufferedReader(new FileReader(gene + ".txt"))) {
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    String[] fields = line.split("\t");
//                    String consequence = fields[4];
//                    counts.put(consequence, counts.getOrDefault(consequence, 0) + 1);
//                }
//            }
//            try (PrintWriter writer = new PrintWriter(gene + "_stats.txt")) {
//                for (Map.Entry<String, Integer> entry : counts.entrySet()) {
//                    writer.printf("%s: %d%n", entry.getKey(), entry.getValue());
//                }
//            }
//            new File(gene + ".txt").delete();
//        }
//    }
//}
//
