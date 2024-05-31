import java.util.*;
public class TSP_DFS {
    private int minMaxDistance = Integer.MAX_VALUE;
    private List<Integer> bestRoute;
    public void tspDFS(List<double[]> coordinates) {
        int n = coordinates.size();
        bestRoute = new ArrayList<>();
        boolean[] visited = new boolean[n];
        List<Integer> currentRoute = new ArrayList<>();
        currentRoute.add(0);
        visited[0] = true;
        // Calculate distances
        int[][] distances = calculateDistances(coordinates);
        dfs(distances, visited, currentRoute, 0, 0, n);
        System.out.println("DFS Best route: " + bestRoute + ", with max distance: " + minMaxDistance);
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
    private void dfs(int[][] distances, boolean[] visited, List<Integer> currentRoute, int currentCity, int currentMaxDistance, int n) {
        if (currentRoute.size() == n) {
            currentRoute.add(0);
            currentMaxDistance = Math.max(currentMaxDistance, distances[currentCity][0]);
            if (currentMaxDistance < minMaxDistance) {
                minMaxDistance = currentMaxDistance;
                bestRoute = new ArrayList<>(currentRoute);
            }
            currentRoute.remove(currentRoute.size() - 1);
            return;
        }
        for (int nextCity = 0; nextCity < n; nextCity++) {
            if (!visited[nextCity]) {
                visited[nextCity] = true;
                currentRoute.add(nextCity);
                dfs(distances, visited, currentRoute, nextCity, Math.max(currentMaxDistance, distances[currentCity][nextCity]), n);
                visited[nextCity] = false;
                currentRoute.remove(currentRoute.size() - 1);
            }
        }
    }
}
