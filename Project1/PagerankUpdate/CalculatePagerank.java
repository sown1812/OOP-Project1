package pagerankup;
import java.util.*;

public class CalculatePagerank {
    private Graph graph;
    private Map<String, Double> pagerank;

    public CalculatePagerank(Graph graph) {
        this.graph = graph;
        this.pagerank = new HashMap<>();
        initializePageRank();
    }

    // Khởi tạo điểm PageRank
    private void initializePageRank() {
        // Khởi tạo điểm Pagerank của tất cả các user bằng 1.0
        for (String user : graph.getUsers()) {
            pagerank.put(user, 1.0);
        }
    }

    // Tính toán PageRank
    public void computePageRank() {
        double dampingFactor = 0.85;
        int iterations = 100; // Số lần lặp

        for (int i = 0; i < iterations; i++) {
            Map<String, Double> newPageRank = new HashMap<>();
            for (String user : graph.getUsers()) {
                double rankSum = 0.0;
                for (Edge edge : graph.getEdges()) {
                    if (edge.getTo().equals(user)) {
                        double interactionWeight = edge.getWeight();

                        // Nếu là comment, chia theo số lượng comment của user lên tweet của KOL
                        if (edge instanceof CommentEdge) {
                            String tweet = edge.getTo(); // tweet của user
                            String tweetAuthor = graph.getTweetAuthors().get(tweet); // Tìm tác giả của tweet
                            if (tweetAuthor != null && graph.getUserCommentCount().containsKey(tweetAuthor)) {
                                rankSum += pagerank.get(edge.getFrom()) * interactionWeight /
                                        graph.getUserCommentCount().get(edge.getFrom());
                            }
                        }

                        // Nếu là retweet, chia theo số lượng retweet của user lên tweet của KOL
                        if (edge instanceof RetweetEdge) {
                            String tweet = edge.getTo(); // tweet của user
                            String tweetAuthor = graph.getTweetAuthors().get(tweet); // Tìm tác giả của tweet
                            if (tweetAuthor != null && graph.getUserRetweetCount().containsKey(tweetAuthor)) {
                                rankSum += pagerank.get(edge.getFrom()) * interactionWeight /
                                        graph.getUserRetweetCount().get(edge.getFrom());
                            }
                        }
                    }
                }
                newPageRank.put(user, (1 - dampingFactor) + dampingFactor * rankSum);
            }
            pagerank = newPageRank; // Cập nhật điểm Pagerank
        }
    }

    // Lấy kết quả PageRank
    public Map<String, Double> getPageRank() {
        return pagerank;
    }
}
