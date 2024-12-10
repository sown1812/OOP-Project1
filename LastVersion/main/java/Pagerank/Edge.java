package Pagerank;

class Edge {
    String destination; // Đỉnh đích
    double weight; // Trọng số của cạnh

    public Edge(String destination, double weight) {
        this.destination = destination;
        this.weight = weight;
    }
}

