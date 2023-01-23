import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

public class Helper {

    public static void main(String[] args) {
        int maxDistance = 15;

        City[] cities = readCities("cities.txt");
        boolean[][] edges = createEdgeMatrix(cities, maxDistance);

        int[][] payoffs = createPayoffMatrix(edges, cities);

        printPayoffs(payoffs);

        Elimination.citiesLeftAfterElimination(payoffs, cities);
    }

    private static void printPayoffs(int[][] payoffs) {
        try {
            File file = new File("payoffs.txt");
            FileWriter writer = new FileWriter(file);

            for(int row = 0; row < payoffs.length; row++) {
                for(int col = 0; col < payoffs[0].length; col++) {
                    writer.append(payoffs[row][col] + "  ");
                }
                writer.append("\n");
            }

            writer.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
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

    private static int[][] createPayoffMatrix(boolean[][] edges, City[] cities) {
        int numberOfCities = cities.length;
        int totalNumberOfInhabitants = 0;

        for(City city : cities) totalNumberOfInhabitants += city.getSize();


        int[][] payoffMatrix = new int[numberOfCities][numberOfCities];

        for(int i = 0; i < numberOfCities; i++) {
            for(int j = 0; j < numberOfCities; j++) {
                City firstCity = cities[i];
                City secondCity = cities[j];

                if(firstCity == secondCity) {
                    int payoff = totalNumberOfInhabitants / 2;
                    payoffMatrix[i][j] = payoff;
                } else {
                    int customers = 0;
    
                    for(City city : cities) {
                        int size = city.getSize();
    
                        double distanceToFirst = city.dist(firstCity);
                        double distanceToSecond = city.dist(secondCity);
    
                        if(distanceToFirst < distanceToSecond) {
                            customers += size;
                        } else if(distanceToFirst == distanceToSecond) {
                            customers += size / 2;
                        }
                    }
    
                    int payoff = customers;
                    payoffMatrix[i][j] = payoff;
                }
            }
        }

        return payoffMatrix;
    }

}
