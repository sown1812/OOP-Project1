package Pagerank;

import java.util.*;

class Graph {
    private Map<String, List<Edge>> adjacencyList;

    public Graph() {
        adjacencyList = new HashMap<>();
    }

    // Thêm đỉnh và cạnh vào đồ thị
    public void addEdge(String source, String destination, double weight) {
        adjacencyList.putIfAbsent(source, new ArrayList<>());
        adjacencyList.get(source).add(new Edge(destination, weight));
    }

    // Lấy danh sách các cạnh liên kết với đỉnh
    public List<Edge> getEdges(String node) {
        return adjacencyList.getOrDefault(node, new ArrayList<>());
    }

    // Lấy tất cả các đỉnh trong đồ thị
    public Set<String> getNodes() {
        return adjacencyList.keySet();
    }
}