import java.io.File;
import java.io.FileNotFoundException;
// import java.util.Arrays;
import java.util.Scanner;

public class GraphCreator {

    public static void main(String[] args) {
        int maxDistance = Integer.parseInt(args[0]);

        City[] cities = readCities("cities.txt");
        boolean[][] edges = createEdgeMatrix(cities, maxDistance);

        int[][] payoffs = Elimination.createPayoffMatrix(edges, cities);

        Elimination.eliminate(payoffs, cities);
    }


    private static boolean[][] createEdgeMatrix(City[] cities, int maxDistance) {
        boolean[][] matrix = new boolean[cities.length][cities.length];
        
        for(int i = 0; i < cities.length; i++) {
            City currentCity = cities[i];

            for(int j = 0; j < cities.length; j++) {
                City otherCity = cities[j];
                if(currentCity.dist(otherCity) <= maxDistance) matrix[i][j] = true; 
            }
        }

        // System.out.println(Arrays.deepToString(matrix));

        return matrix;
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
