package Pagerank;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import datacollection.WriteFile;

public class Control {
    public static void Start() {
        // Tạo đồ thị
        Graph graph = new Graph();

        WriteFile W = new WriteFile();

        try (BufferedReader br = new BufferedReader(new FileReader("edge.txt"))) {
            String acc;
            while ((acc = br.readLine()) != null && acc != "\n"){
//                System.out.println(acc);
                String[] urls = acc.split(" ");
                if (urls.length < 2) {
                    continue;
                }
                String statusId = extractStatusId(urls[0]);
                String Ad = extractUsername(urls[0]);
                String cmt = extractUsername(urls[1]);
                graph.addEdge(Ad, cmt, 0.7);
                graph.addEdge(Ad, statusId, 0.5);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader br = new BufferedReader(new FileReader("edge2.txt"))) {
            String acc;
            while ((acc = br.readLine()) != null && acc != "\n"){
                String[] urls = acc.split(" ");
                if (urls.length < 2) {
                    continue;
                }
                String statusId = extractStatusId(urls[0]);
                String Ad = extractUsername(urls[0]);
                String rt = extractUsername(urls[1]);
                graph.addEdge(Ad, rt, 1);
                graph.addEdge(Ad, statusId, 0.5);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        PageRankCalculator pageRankCalculator = new PageRankCalculator(graph, 0.85, 100);

        Map<String, Double> pageRank = pageRankCalculator.calculatePageRank();

        List<Map.Entry<String, Double>> kolList = new ArrayList<>();

        for (Map.Entry<String, Double> entry : pageRank.entrySet()) {
            kolList.add(entry);
            W.writeToFile("KOL1.txt", entry.getKey() + " ", true);
            W.writeToFile("KOL1.txt", String.valueOf(entry.getValue()) + "\n", true);
//            System.out.println("Node " + entry.getKey() + " PageRank: " + entry.getValue());
        }

        kolList.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        W.writeToFile("KOL_CMT.txt", "", false);
        int rank = 0;
        for (Map.Entry<String, Double> entry : kolList) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
            String anow = entry.getKey();
            anow = "@" + anow;
            try (BufferedReader br = new BufferedReader(new FileReader("KOL.txt"))) {
                String acc;
                while ((acc = br.readLine()) != null) {
                    String[] urls = acc.split(" ");
                    if(anow.equals(urls[0])) {
                        W.writeToFile("KOL_CMT.txt", acc + " " + String.valueOf(rank) + "\n", true);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            rank++;
        }
    }

    public static String extractUsername(String url) {
        String[] parts = url.split("/");
        return parts[3];
    }

    public static String extractStatusId(String url) {
        int statusIndex = url.indexOf("/status/");

        if (statusIndex != -1) {
            return url.substring(statusIndex + 8);
        } else {
            return "Không tìm thấy mã số status";
        }
    }
}
