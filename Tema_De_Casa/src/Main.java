import java.io.*;
import java.util.*;
public class Main {
    public static void main(String[] args) {
        try {
            // Read input from file
            List<double[]> coordinates = readCoordinatesFromFile("lin105.tsp");

            measureRuntime("TSP_DFS", () -> {
                TSP_DFS tsp_dfs = new TSP_DFS();
                tsp_dfs.tspDFS(coordinates);
            });

            measureRuntime("TSP_UniformCost", () -> {
                TSP_UniformCost tspUniformCost = new TSP_UniformCost();
                tspUniformCost.tspUniformCostSearch(coordinates);
            });

            measureRuntime("TSP_AStar", () -> {
                TSP_AStar tspAStar = new TSP_AStar();
                tspAStar.tspAStar(coordinates);
            });


            TSP_DFS tsp_dfs = new TSP_DFS();
            tsp_dfs.tspDFS(coordinates);

            TSP_UniformCost tspUniformCost = new TSP_UniformCost();
            tspUniformCost.tspUniformCostSearch(coordinates);

            TSP_AStar tspAStar = new TSP_AStar();
            tspAStar.tspAStar(coordinates);

        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
        }
    }
    private static List<double[]> readCoordinatesFromFile(String filename) throws IOException {
        List<double[]> coordinates = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        boolean readingCoordinates = false;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("NODE_COORD_SECTION")) {
                readingCoordinates = true;
                continue;
            }
            if (line.startsWith("EOF")) {
                break;
            }
            if (readingCoordinates) {
                String[] parts = line.trim().split("\\s+");
                double x = Double.parseDouble(parts[1]);
                double y = Double.parseDouble(parts[2]);
                coordinates.add(new double[]{x, y});
            }
        }
        reader.close();
        return coordinates;
    }
        private static void measureRuntime(String className, Runnable runnable) {
            long startTime = System.currentTimeMillis();
            runnable.run();
            long endTime = System.currentTimeMillis();
            System.out.println(className + " runtime: " + (endTime - startTime) + " milliseconds");
        }
    }

