//package pagerank;
//
//import java.io.*;
//import java.util.*;
//
//public class MainUpdate{
//    public static void main(String[] args) {
//        // Đọc dữ liệu từ 2 tệp mẫu
//        Graph graph = new Graph();
//        HashSet kols = new HashSet();
//        // Xử lý file a.txt (comment)
//        try (BufferedReader readerA = new BufferedReader(new FileReader("E:\\Java_Project\\PagerankUpdate\\data\\edge.txt"))) {
//            String line;
//            while ((line = readerA.readLine()) != null) {
//                line = line.trim();
//                if (!line.isEmpty()) {
//                    // Xử lý nhiều URL trong mỗi dòng
//                    String[] parts = line.split(" "); // Tách các URL trong dòng
//                    for (String part : parts) {
//                        String kol = extractUser(part); // Lấy tên KOL từ URL
//                        String tweetId = extractTweetId(part); // Lấy ID tweet từ URL
//                        String commenter = extractUser(parts[1]); // Lấy tên người comment từ URL (sử dụng URL thứ hai)
//
//                        kols.add(kol);
//                        // Thêm người dùng, tweet và mối quan hệ comment vào đồ thị
//                        graph.addUser(kol);
//                        graph.addTweet(kol, tweetId);
//                        graph.addUser(commenter);
//                        graph.addInteraction(commenter, kol, weight, tweetId);  // Người comment vào tweet của KOL
//                    }
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // Xử lý file b.txt (retweet)
//        try (BufferedReader readerB = new BufferedReader(new FileReader("E:\\Java_Project\\PagerankUpdate\\data\\edge2.txt"))) {
//            String line;
//            while ((line = readerB.readLine()) != null) {
//                line = line.trim();
//                if (!line.isEmpty()) {
//                    // Xử lý nhiều URL trong mỗi dòng
//                    String[] parts = line.split(" "); // Tách các URL trong dòng
//                    for (String part : parts) {
//                        String kol = extractUser(part); // Lấy tên KOL từ URL
//                        String tweetId = extractTweetId(part); // Lấy ID tweet từ URL
//
//                        if (tweetId != null) { // Kiểm tra nếu ID tweet hợp lệ
//                            String commenter = extractUser(parts[1]); // Lấy tên người comment từ URL (sử dụng URL thứ hai)
//
//                            kols.add(kol);
//                            // Thêm người dùng, tweet và mối quan hệ comment vào đồ thị
//                            graph.addUser(kol);
//                            graph.addTweet(kol, tweetId);
//                            graph.addUser(commenter);
//                            graph.addInteraction(commenter, kol, weight, tweetId);  // Người comment vào tweet của KOL
//                        }
//                    }
//
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // Tính toán PageRank
//        PageRank pageRank = new PageRank(graph);
//        Map<String, Double> ranks = pageRank.calculatePageRank();
//
//        // In kết quả PageRank
//        System.out.println("PageRank results:");
//        for (Map.Entry<String, Double> entry : ranks.entrySet()) {
//            if(kols.contains(entry.getKey()))
//                System.out.println(entry.getKey() + ": " + entry.getValue());
//        }
//    }
//
//    // Hàm giúp trích xuất tên người dùng từ URL
//    private static String extractUser(String url) {
//        String[] parts = url.split("/");
//        return parts[3]; // Tên người dùng sẽ ở phần thứ 4 của URL
//    }
//
//    // Hàm giúp trích xuất ID tweet từ URL
//    private static String extractTweetId(String url) {
//        String[] parts = url.split("/");
//        if (parts.length > 5) {
//            return parts[5]; // Nếu có ít nhất 6 phần tử, phần tử thứ 6 là ID tweet
//        } else {
//            return null; // Trả về null nếu không có ID tweet
//        }
//    }
//}
