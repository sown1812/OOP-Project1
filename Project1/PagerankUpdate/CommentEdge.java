package pagerankup;

public class CommentEdge extends Edge {
    public CommentEdge(String from, String to) {
        super(from, to, 0.7, "comment");
    }
}
