package pagerankup;
import java.util.*;

public class Graph {
    private Set<String> users; // Các user trong đồ thị
    private Set<String> tweets; // Các tweet trong đồ thị
    private Map<String, String> tweetAuthors; // Mã hóa tweet với người đăng
    private List<Edge> edges; // Các cạnh trong đồ thị
    private Map<String, Integer> userCommentCount; // Số lượng comment của user lên tweet của KOL
    private Map<String, Integer> userRetweetCount; // Số lượng retweet của user lên tweet của KOL

    public Graph() {
        users = new HashSet<>();
        tweets = new HashSet<>();
        tweetAuthors = new HashMap<>();
        edges = new ArrayList<>();
        userCommentCount = new HashMap<>();
        userRetweetCount = new HashMap<>();
    }

    // Thêm user vào đồ thị
    public void addUser(String user) {
        users.add(user);
    }

    // Thêm tweet vào đồ thị và liên kết với tác giả
    public void addTweet(String tweet, String author) {
        tweets.add(tweet);
        tweetAuthors.put(tweet, author);
    }

    // Thêm các cạnh (mối quan hệ giữa các user và tweet)
    public void addEdge(Edge edge) {
        edges.add(edge);

        // Nếu là comment, tăng số lượng comment của user đối với tất cả các tweet của các KOL
        if (edge instanceof CommentEdge) {
            String tweet = edge.getTo(); // Tweet mà user đã comment
            String tweetAuthor = tweetAuthors.get(tweet); // Tìm tác giả của tweet
            if (tweetAuthor != null) {
                if (users.contains(edge.getFrom())) {
                    // Tăng số lượng comment của user lên tất cả các tweet của KOL
                    userCommentCount.put(edge.getFrom(), userCommentCount.getOrDefault(edge.getFrom(), 0) + 1);
                }
            }
        }

        // Nếu là retweet, tăng số lượng retweet của user đối với tất cả các tweet của các KOL
        if (edge instanceof RetweetEdge) {
            String tweet = edge.getTo(); // Tweet mà user đã retweet
            String tweetAuthor = tweetAuthors.get(tweet); // Tìm tác giả của tweet
            if (tweetAuthor != null) {
                if (users.contains(edge.getFrom())) {
                    // Tăng số lượng retweet của user lên tất cả các tweet của KOL
                    userRetweetCount.put(edge.getFrom(), userRetweetCount.getOrDefault(edge.getFrom(), 0) + 1);
                }
            }
        }
    }

    // Lấy danh sách user trong đồ thị
    public Set<String> getUsers() {
        return users;
    }

    // Lấy danh sách tweet trong đồ thị
    public Set<String> getTweets() {
        return tweets;
    }

    // Lấy thông tin tác giả tweet
    public Map<String, String> getTweetAuthors() {
        return tweetAuthors;
    }

    // Lấy danh sách các cạnh trong đồ thị
    public List<Edge> getEdges() {
        return edges;
    }

    // Lấy số lượng comment của user
    public Map<String, Integer> getUserCommentCount() {
        return userCommentCount;
    }

    // Lấy số lượng retweet của user
    public Map<String, Integer> getUserRetweetCount() {
        return userRetweetCount;
    }
}
