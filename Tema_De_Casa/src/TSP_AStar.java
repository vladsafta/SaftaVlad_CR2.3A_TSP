import java.util.*;
public class TSP_AStar {
    private static int tspAStarCount = 0; // Counter variable to count tspAStar method calls
    static class Node implements Comparable<Node> {
        int currentCity;
        int gCost;
        List<Integer> path;
        Set<Integer> remainingCities;
        Node(int currentCity, int gCost, List<Integer> path, Set<Integer> remainingCities) {
            this.currentCity = currentCity;
            this.gCost = gCost;
            this.path = new ArrayList<>(path);
            this.remainingCities = new HashSet<>(remainingCities);
        }
        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.gCost, other.gCost);
        }
    }

    public void tspAStar(List<double[]> coordinates) {
        int n = coordinates.size();
        PriorityQueue<Node> pq = new PriorityQueue<>();
        Set<Integer> remainingCities = new HashSet<>();
        for (int i = 1; i < n; i++) {
            remainingCities.add(i);
        }
        pq.add(new Node(0, 0, Arrays.asList(0), remainingCities));
        int minMaxDistance = Integer.MAX_VALUE;
        List<Integer> bestRoute = null;
        // Calculate distances
        int[][] distances = calculateDistances(coordinates);
        while (!pq.isEmpty()) {
            Node currentNode = pq.poll();
            int currentCity = currentNode.currentCity;
            int currentCost = currentNode.gCost;
            List<Integer> currentPath = currentNode.path;
            Set<Integer> currentRemaining = currentNode.remainingCities;
            if (currentRemaining.isEmpty()) {
                currentPath.add(0);
                int maxDistance = getMaxDistance(currentPath, distances);
                if (maxDistance < minMaxDistance) {
                    minMaxDistance = maxDistance;
                    bestRoute = currentPath;
                }
                continue;
            }
            for (int nextCity : currentRemaining) {
                List<Integer> newPath = new ArrayList<>(currentPath);
                newPath.add(nextCity);
                Set<Integer> newRemaining = new HashSet<>(currentRemaining);
                newRemaining.remove(nextCity);
                int gCost = Math.max(currentCost, distances[currentCity][nextCity]);
                int hCost = mstHeuristic(nextCity, newRemaining, distances);
                int fCost = gCost + hCost;
                pq.add(new Node(nextCity, fCost, newPath, newRemaining));
            }
        }
        System.out.println("A* Best route: " + bestRoute + ", with max distance: " + minMaxDistance);
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
    private int mstHeuristic(int currentCity, Set<Integer> remainingCities, int[][] distances) {
        if (remainingCities.isEmpty()) {
            return 0;
        }
        List<Integer> nodes = new ArrayList<>(remainingCities);
        int[][] subgraph = new int[nodes.size()][nodes.size()];
        for (int i = 0; i < nodes.size(); i++) {
            for (int j = 0; j < nodes.size(); j++) {
                subgraph[i][j] = distances[nodes.get(i)][nodes.get(j)];
            }
        }

        return mstCost(subgraph);
    }
    private int mstCost(int[][] graph) {
        int n = graph.length;
        boolean[] visited = new boolean[n];
        int[] minEdge = new int[n];
        Arrays.fill(minEdge, Integer.MAX_VALUE);
        minEdge[0] = 0;
        int mstCost = 0;
        for (int i = 0; i < n; i++) {
            int u = -1;
            for (int j = 0; j < n; j++) {
                if (!visited[j] && (u == -1 || minEdge[j] < minEdge[u])) {
                    u = j;
                }
            }
            visited[u] = true;
            mstCost += minEdge[u];

            for (int v = 0; v < n; v++) {
                if (graph[u][v] < minEdge[v]) {
                    minEdge[v] = graph[u][v];
                }
            }
        }
        return mstCost;
    }
    private int getMaxDistance(List<Integer> path, int[][] distances) {
        int maxDistance = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            int currentCity = path.get(i);
            int nextCity = path.get(i + 1);
            maxDistance = Math.max(maxDistance, distances[currentCity][nextCity]);
        }
        return maxDistance;
    }

}