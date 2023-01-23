import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Helper {

    public static void main(String[] args) {
        int maxDistance = 15;

        ArrayList<City> cities = readCities("cities.txt");
        boolean[][] edges = createEdgeMatrix(cities, maxDistance);

        int[][] payoffs = createPayoffMatrix(cities);

        printPayoffs(payoffs);

        // Elimination.citiesLeftAfterElimination(payoffs, cities);

        City startA = cities.get(0);
        City startB = cities.get(1);

        LocalSearch.performLocalSearch(cities, edges, payoffs, startA, startB);

    }

    private static void printPayoffs(int[][] payoffs) {
        try {
            File file = new File("payoffsNew.txt");
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


    private static boolean[][] createEdgeMatrix(ArrayList<City> cities, int maxDistance) {
        boolean[][] matrix = new boolean[cities.size()][cities.size()];
        
        for(int i = 0; i < cities.size(); i++) {
            City currentCity = cities.get(i);

            for(int j = 0; j < cities.size(); j++) {
                City otherCity = cities.get(j);
                if(currentCity.dist(otherCity) <= maxDistance) matrix[i][j] = true; 
            }
        }

        return matrix;
    }


    private static ArrayList<City> readCities(String filename) {
        try {
            File file = new File(filename);
            Scanner sc = new Scanner(file);

            int numberOfCities = sc.nextInt();

            ArrayList<City> cities = new ArrayList<>();

            for(int i = 0; i < numberOfCities; i++) {
                String name = sc.next();
                int size = sc.nextInt();
                double latitude = sc.nextDouble();
                double longitude = sc.nextDouble();

                cities.add(new City(name, size, latitude, longitude));
            }

            sc.close();
            return cities;
        } catch (FileNotFoundException e) {
            e.printStackTrace();

            return null;
        }
    }

    private static int[][] createPayoffMatrix(ArrayList<City> cities) {
        int numberOfCities = cities.size();
        int totalNumberOfInhabitants = 0;

        for(City city : cities) totalNumberOfInhabitants += city.getSize();


        int[][] payoffMatrix = new int[numberOfCities][numberOfCities];

        for(int i = 0; i < numberOfCities; i++) {
            for(int j = 0; j < numberOfCities; j++) {
                City firstCity = cities.get(i);
                City secondCity = cities.get(j);

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
