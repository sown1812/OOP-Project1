package pagerank;

import java.io.*;
import java.util.*;

public class Main2 {
    public static void main(String[] args) {
        // Đọc dữ liệu từ 2 tệp mẫu
        Graph graph = new Graph();
        Set<String> kolSet = new HashSet<>(); // Set để lưu danh sách các KOL

//        // Xử lý file edge.txt (comment)
//        try (BufferedReader readerEdge = new BufferedReader(new FileReader("E:\\Java_Project\\PagerankUpdate\\data\\edge.txt"))) {
//            String line;
//            while ((line = readerEdge.readLine()) != null) {
//                line = line.trim();
//                if (!line.isEmpty()) {
//                    // Tách các URL trong mỗi dòng
//                    String[] parts = line.split(" "); // Tách theo khoảng trắng
//                    if (parts.length == 2) {
//                        String kol = extractUser(parts[0]); // Lấy tên KOL từ URL đầu tiên
//                        String tweetId = extractTweetId(parts[0]); // Lấy ID tweet từ URL đầu tiên
//                        String commenter = extractUser(parts[1]); // Lấy tên người comment từ URL thứ hai
//
//                        // Thêm KOL vào set (giữ danh sách duy nhất)
//                        kolSet.add(kol);
//
//                        // Thêm người dùng, tweet và mối quan hệ comment vào đồ thị
//                        graph.addUser(kol);
//                        graph.addTweet(kol, tweetId);
//                        graph.addUser(commenter);
//                        graph.addInteraction(commenter, kol,"comment");  // Người comment vào tweet của KOL
////                        System.out.println("interactionCount: " + graph.getCommentCountToKOL(kol));
//                    }
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        // Xử lý file edge2.txt (retweet)
        try (BufferedReader readerEdge2 = new BufferedReader(new FileReader("E:\\Java_Project\\PagerankUpdate\\data\\edge2.txt"))) {
            String line;
            while ((line = readerEdge2.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    // Tách các URL trong mỗi dòng
                    String[] parts = line.split(" "); // Tách theo khoảng trắng
                    if (parts.length == 2) {
                        String kol = extractUser(parts[0]); // Lấy tên KOL từ URL đầu tiên
                        String tweetId = extractTweetId(parts[0]); // Lấy ID tweet từ URL đầu tiên
                        String retweeter = extractUser(parts[1]); // Lấy tên người retweet từ URL thứ hai

                        // Thêm KOL vào set (giữ danh sách duy nhất)
                        kolSet.add(kol);

                        // Thêm người dùng, tweet và mối quan hệ retweet vào đồ thị
                        graph.addUser(kol);
                        graph.addTweet(kol, tweetId);
                        graph.addUser(retweeter);
                        graph.addInteraction(retweeter, kol, "retweet"); // Người retweet tweet của KOL

                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Tính toán PageRank
        PageRank pageRank = new PageRank(graph);
        Map<String, Double> ranks = pageRank.calculatePageRank();

        // Lọc và sắp xếp KOLs theo PageRank
        List<Map.Entry<String, Double>> kolRanks = new ArrayList<>();
        for (String kol : kolSet) {
            Double rank = ranks.get(kol);
            if (rank != null) {
                kolRanks.add(Map.entry(kol, rank));
            }
        }

        // Sắp xếp theo thứ tự giảm dần của PageRank
        kolRanks.sort((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()));

        // In thứ tự và xếp hạng các KOLs
        System.out.println("Ranked KOLs based on PageRank:");
        int rank = 1;
        for (Map.Entry<String, Double> entry : kolRanks) {
            System.out.println("Rank " + rank + ": " + entry.getKey() + " - PageRank: " + entry.getValue());
            rank++;
        }

    }

    // Hàm giúp trích xuất tên người dùng từ URL
    private static String extractUser(String url) {
        String[] parts = url.split("/");
        return parts[3]; // Tên người dùng sẽ ở phần thứ 4 của URL
    }

    // Hàm giúp trích xuất ID tweet từ URL
    private static String extractTweetId(String url) {
        String[] parts = url.split("/");
        if (parts.length > 5) {
            return parts[5]; // Nếu có ít nhất 6 phần tử, phần tử thứ 6 là ID tweet
        } else {
            return null; // Trả về null nếu không có ID tweet
        }
    }
}
