import java.util.*;
public class TSP_UniformCost {
    static class Node implements Comparable<Node> {
        int currentCity;
        int currentCost;
        List<Integer> path;
        Node(int currentCity, int currentCost, List<Integer> path) {
            this.currentCity = currentCity;
            this.currentCost = currentCost;
            this.path = new ArrayList<>(path);
        }
        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.currentCost, other.currentCost);
        }
    }
    public void tspUniformCostSearch(List<double[]> coordinates) {
        int n = coordinates.size();
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(0, 0, Arrays.asList(0)));
        int minMaxDistance = Integer.MAX_VALUE;
        List<Integer> bestRoute = null;

        // Calculate distances
        int[][] distances = calculateDistances(coordinates);
        while (!pq.isEmpty()) {
            Node currentNode = pq.poll();
            int currentCity = currentNode.currentCity;
            int currentCost = currentNode.currentCost;
            List<Integer> currentPath = currentNode.path;

            if (currentPath.size() == n) {
                currentPath.add(0);
                int maxDistance = getMaxDistance(currentPath, distances);
                if (maxDistance < minMaxDistance) {
                    minMaxDistance = maxDistance;
                    bestRoute = currentPath;
                }
                continue;
            }
            for (int nextCity = 0; nextCity < n; nextCity++) {
                if (!currentPath.contains(nextCity)) {
                    List<Integer> newPath = new ArrayList<>(currentPath);
                    newPath.add(nextCity);
                    int newCost = Math.max(currentCost, distances[currentCity][nextCity]);
                    pq.add(new Node(nextCity, newCost, newPath));
                }
            }
        }
        System.out.println("Uniform Cost Best route: " + bestRoute + ", with max distance: " + minMaxDistance);
    }
    private int[][] calculateDistances(List<double[]> coordinates) {
        int n = coordinates.size();
        int[][] distances = new int[n][n];
        for (int i = 0; i < n; i++) {
            double[] coord1 = coordinates.get(i);
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    distances[i][j] = 0;
                    continue;
                }
                double[] coord2 = coordinates.get(j);
                double dx = coord1[0] - coord2[0];
                double dy = coord1[1] - coord2[1];
                distances[i][j] = (int) Math.round(Math.sqrt(dx * dx + dy * dy));
            }
        }
        return distances;
    }
    private int getMaxDistance(List<Integer> path, int[][] distances) {
        int maxDistance = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            maxDistance = Math.max(maxDistance, distances[path.get(i)][path.get(i + 1)]);
        }
        return maxDistance;
    }
}
