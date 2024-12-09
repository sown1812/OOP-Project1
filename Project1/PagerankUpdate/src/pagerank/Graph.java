package pagerank;

import java.util.*;

public class Graph {
    private final Map<String, Set<Edge>> adjList; // Danh sách các đỉnh và các cạnh của chúng
    private final Map<String, Set<String>> tweetLinks; // Lưu trữ các tweet của mỗi KOL

    public Graph() {
        adjList = new HashMap<>();
        tweetLinks = new HashMap<>();
    }

    // Thêm một KOL vào đồ thị
    public void addUser(String user) {
        adjList.putIfAbsent(user, new HashSet<>());
        tweetLinks.putIfAbsent(user, new HashSet<>());
    }

    // Thêm mối quan hệ theo dõi giữa hai user (cạnh KOL-user)
    public void addFollowRelation(String follower, String followee) {
        adjList.putIfAbsent(follower, new HashSet<>());
        adjList.get(follower).add(new Edge(follower, followee, 5.0, "follow"));
    }

    // Thêm tweet cho KOL (cạnh KOL -> Tweet)
    public void addTweet(String kol, String tweet) {
        tweetLinks.putIfAbsent(kol, new HashSet<>());
        tweetLinks.get(kol).add(tweet);
    }

    // Thêm mối quan hệ comment hoặc retweet từ User hoặc KOL tới tweet
    public void addInteraction(String user, String tweet, String interactionType) {
        double weight = interactionType.equals("retweet") ? 11.0 : 10.0;
        adjList.putIfAbsent(user, new HashSet<>());
        adjList.get(user).add(new Edge(user, tweet, weight, interactionType));
    }

    // Thêm mối quan hệ từ KOL tới KOL khác (cạnh KOL -> KOL)
    public void addFollowRelationKOL(String kol1, String kol2) {
        adjList.putIfAbsent(kol1, new HashSet<>());
        adjList.get(kol1).add(new Edge(kol1, kol2, 5.0, "follow"));
    }

    // Lấy các followers của một KOL
    public Set<Edge> getFollowers(String kol) {
        return adjList.getOrDefault(kol, Collections.emptySet());
    }

    // Lấy các tweet của KOL
    public Set<String> getTweets(String kol) {
        return tweetLinks.getOrDefault(kol, Collections.emptySet());
    }

    // Lấy tất cả các KOL trong đồ thị
    public Set<String> getAllUsers() {
        return adjList.keySet();
    }

    // Lấy số lượng comment của một user đối với các KOL
    public int getCommentCountToKOL(String user) {
        int count = 0;
        Set<Edge> interactions = adjList.getOrDefault(user, Collections.emptySet());
        for (Edge edge : interactions) {
            if (edge.getType().equals("comment")) {
                count++;
            }
        }
        return count;
    }

    // Lấy số lượng retweet của một user đối với các KOL
    public int getRetweetCountToKOL(String user) {
        int count = 0;
        Set<Edge> interactions = adjList.getOrDefault(user, Collections.emptySet());
        for (Edge edge : interactions) {
            if (edge.getType().equals("retweet")) {
                count++;
            }
        }
        return count;
    }

    // Lấy số lượng follower của một user đối với các KOL
    public int getFollowerCountToKOL(String user) {
        int count = 0;
        Set<Edge> followers = adjList.getOrDefault(user, Collections.emptySet());
        for (Edge edge : followers) {
            if (edge.getType().equals("follow")) {
                count++;
            }
        }
        return count;
    }
}
