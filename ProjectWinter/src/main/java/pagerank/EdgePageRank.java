package pagerank;

class EdgePageRank {
    String destination; // Đỉnh đích
    double weight; // Trọng số của cạnh

    public EdgePageRank(String destination, double weight) {
        this.destination = destination;
        this.weight = weight;
    }
}

