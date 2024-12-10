package pagerankup;

public class Edge {
    protected String from;
    protected String to;
    protected double weight;
    protected String edgeType;

    public Edge(String from, String to, double weight, String edgeType) {
        this.from = from;
        this.to = to;
        this.weight = weight;
        this.edgeType = edgeType;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public double getWeight() {
        return weight;
    }

    public String getEdgeType() {
        return edgeType;
    }
}
