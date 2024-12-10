package pagerankup;
import java.util.*;
public class Main {
    public static void main(String[] args) {
        // Tạo đồ thị và các KOL, tweet, user
        Graph graph = new Graph();

        // Thêm users vào đồ thị
        graph.addUser("kol1");
        graph.addUser("kol2");
        graph.addUser("user1");
        graph.addUser("user2");
        graph.addUser("user3");

        // Thêm tweets vào đồ thị
        graph.addTweet("tweet1", "kol1");
        graph.addTweet("tweet2", "kol1");
        graph.addTweet("tweet3", "kol2");
        graph.addTweet("tweet4", "kol2");

        // Thêm các cạnh (mối quan hệ giữa các user và tweet)
        graph.addEdge(new CommentEdge("user1", "tweet1"));
//        graph.addEdge(new RetweetEdge("user2", "kol1"));
//        graph.addEdge(new CommentEdge("user3", "kol2"));
//        graph.addEdge(new RetweetEdge("user1", "kol1"));
//        graph.addEdge(new CommentEdge("user2", "kol1"));
//        graph.addEdge(new RetweetEdge("user3", "kol1"));
//        graph.addEdge(new CommentEdge("user1", "kol1"));
//        graph.addEdge(new RetweetEdge("user2", "kol2"));

        // Tính toán PageRank
        CalculatePagerank pagerankCalculator = new CalculatePagerank(graph);
        pagerankCalculator.computePageRank();

        // In ra điểm Pagerank
        Map<String, Double> pagerankScores = pagerankCalculator.getPageRank();
        for (String user : pagerankScores.keySet()) {
            System.out.println("User: " + user + " -> PageRank: " + pagerankScores.get(user));
        }
    }
}
