import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
// import java.util.Arrays;
import java.util.Scanner;

public class Helper {

    public static void main(String[] args) {
        int maxDistance = 5;

        ArrayList<City> cities = readCities("cities.txt");
        ArrayList<Location> locations = readLocations("locations.txt");

        /* for(City city : cities) {
            locations.add(city);
        } */

        boolean[][] edgesCities = createEdgeMatrix(locations, maxDistance);


        int[][] payoffs2 = createPayoffsLocation(cities, locations);


        // Elimination.citiesLeftAfterElimination(payoffs2, locations);

        Location startA = locations.get(1);
        Location startB = locations.get(20);

        LocalSearch.performLocalSearch(locations, edgesCities, payoffs2, startA, startB);
    }


    private static boolean[][] createEdgeMatrix(ArrayList<Location> locations, int maxDistance) {
        boolean[][] matrix = new boolean[locations.size()][locations.size()];
        
        for(int i = 0; i < locations.size(); i++) {
            Location currentLocation = locations.get(i);

            for(int j = 0; j < locations.size(); j++) {
                Location otherLocation = locations.get(j);
                if(currentLocation.dist(otherLocation) <= maxDistance) matrix[i][j] = true; 
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

    private static ArrayList<Location> readLocations(String filename) {
        try {
            File file = new File(filename);
            Scanner sc = new Scanner(file);

            int numberOfLocations = sc.nextInt();

            ArrayList<Location> locations = new ArrayList<>();

            for(int i = 0; i < numberOfLocations; i++) {
                String name = sc.next();
                double latitude = sc.nextDouble();
                double longitude = sc.nextDouble();
                locations.add(new Location(name,  latitude, longitude));
            }

            sc.close();


            return locations;

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
    
                    payoffMatrix[i][j] = customers;
                }
            }
        }

        return payoffMatrix;
    }

    private static int[][] createPayoffsLocation(ArrayList<City> cities, ArrayList<Location> locations) {
        int numberOfLocations = locations.size();

        int[][] payoffMatrix = new int[numberOfLocations][numberOfLocations];

        for(int i = 0; i < numberOfLocations; i++) {
            for(int j = 0; j < numberOfLocations; j++) {
                Location firstLocation = locations.get(i);
                Location secondLocation = locations.get(j);

                int customers = 0;

                for(City city : cities) {
                    int size = city.getSize();

                    double distanceToFirst = city.dist(firstLocation);
                    double distanceToSecond = city.dist(secondLocation);

                    if(distanceToFirst < distanceToSecond) {
                        customers += size;
                    } else if(distanceToFirst == distanceToSecond) {
                        customers += size / 2;
                    }
                }

                payoffMatrix[i][j] = customers;
            }
        }

        return payoffMatrix;
    }



}
