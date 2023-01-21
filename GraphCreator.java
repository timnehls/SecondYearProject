import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class GraphCreator {

    public static void main(String[] args) {
        int maxDistance = Integer.parseInt(args[0]);

        City[] cities = readCities("cities.txt");
        Graph graph = createGraph(cities, maxDistance);

        double[][] matrix = graph.createAdjacencyMatrix();

        // System.out.println(Arrays.deepToString(matrix));

        int[][][] payoffs = Elimination.createPayoffMatrix(graph);

        System.out.println(Arrays.deepToString(payoffs));
    }

    public static Graph createGraph(City[] cities, int maxDistance) {
        Graph graph = new Graph();
        
        if(cities != null) {
            for(int i = 0; i < cities.length; i++) {
                City currentCity = cities[i];

                graph.addCity(currentCity);

                ArrayList<City> citiesInGraph = graph.getCities();
                for(City otherCity : citiesInGraph) {
                    double distance = currentCity.dist(otherCity);
                    if(distance <= maxDistance && otherCity != currentCity) {
                        graph.addEdge(currentCity, otherCity);
                    }
                }
            }
        }

        return graph;
    }


    private static City[] readCities(String filename) {
        try {
            File file = new File(filename);
            Scanner sc = new Scanner(file);

            int numberOfCities = sc.nextInt();

            City[] cities = new City[numberOfCities];

            for(int i = 0; i < cities.length; i++) {
                String name = sc.next();
                int size = sc.nextInt();
                double latitude = sc.nextDouble();
                double longitude = sc.nextDouble();

                cities[i] = new City(name, size, latitude, longitude);
            }

            sc.close();
            return cities;
        } catch (FileNotFoundException e) {
            e.printStackTrace();

            return null;
        }
    }

}
