package pagerankup;

public class RetweetEdge extends Edge {
    public RetweetEdge(String from, String to) {
        super(from, to, 0.9, "retweet");
    }
}
