package pagerank;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

class PageRank {
    private GraphPageRank graph;
    private double dampingFactor;
    private int maxIterations;

    public PageRank(GraphPageRank graph, double dampingFactor, int maxIterations) {
        this.graph = graph;
        this.dampingFactor = dampingFactor;
        this.maxIterations = maxIterations;
    }

    // Tính toán PageRank
    public Map<String, Double> calculatePageRank() {
        Map<String, Double> pageRank = new HashMap<>();
        Set<String> nodes = graph.getNodes();
        int N = nodes.size();

        // Khởi tạo giá trị PageRank ban đầu
        for (String node : nodes) {
            pageRank.put(node, 1.0 / N);
        }

        // Thuật toán PageRank
        for (int iter = 0; iter < maxIterations; iter++) {
            Map<String, Double> newPageRank = new HashMap<>();

            // Duyệt qua tất cả các đỉnh trong đồ thị
            for (String node : nodes) {
                double rankSum = 0.0;

                // Tính tổng PageRank nhận được từ các đỉnh liên kết với node
                for (String fromNode : nodes) {
                    List<EdgePageRank> edgePageRanks = graph.getEdges(fromNode);
                    for (EdgePageRank edgePageRank : edgePageRanks) {
                        if (edgePageRank.destination.equals(node)) {
                            int outDegree = edgePageRanks.size();
                            rankSum += (pageRank.get(fromNode) * edgePageRank.weight) / outDegree;
                        }
                    }
                }

                // Cập nhật PageRank cho node
                double newRank = (1 - dampingFactor) / N + dampingFactor * rankSum;
                newPageRank.put(node, newRank);
            }

            // Cập nhật giá trị PageRank sau mỗi vòng lặp
            pageRank = new HashMap<>(newPageRank);
        }

        return pageRank;
    }
}
