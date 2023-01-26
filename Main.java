import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String fileCities = "cities.txt", fileLocations = "locations.txt"; // specify here your input files

        String startA = "Merzpark", startB = "ErftKarree";
        int maxDistance = 12;

        /* 
         * You have two choices:
         * 1. determine a location by using the local search approach
         * by specifying the names of the starting cities
         * and a maximum edge length, e.g.:
         */

        localSearch(fileCities, fileLocations, maxDistance, startA, startB);

        /* 
         * 2. conduct iterative elimination of dominated strategies by
         * calling: 
         */

        iterativeElimination(fileCities, fileLocations);
    }

    private static void iterativeElimination(String filenameCities, String filenameLocations) {
        ArrayList<City> cities = readCities(filenameCities);
        ArrayList<Location> locations;
        
        if(filenameCities.equals(filenameLocations)) {
            locations = new ArrayList<>();
            for(City city : cities) locations.add(city);
        } else {
            locations = readLocations(filenameLocations);
        }

        int[][] payoffs = createPayoffMatrix(cities, locations);
        Elimination.citiesLeftAfterElimination(payoffs, locations);
    }

    private static void localSearch(String filenameCities, String filenameLocations, int maxDistance, String idA, String idB) throws NoSuchElementException{
        ArrayList<City> cities = readCities(filenameCities);
        ArrayList<Location> locations;
        
        if(filenameCities.equals(filenameLocations)) {
            locations = new ArrayList<>();
            for(City city : cities) locations.add(city);
        } else {
            locations = readLocations(filenameLocations);
        }

        boolean[][] edges = createEdgeMatrix(locations, maxDistance);
        int[][] payoffs = createPayoffMatrix(cities, locations);

        Location startA = null;
        Location startB = null;

        for(Location location : locations) {
            if(location.getName().equalsIgnoreCase(idA)) startA = location;
            else if(location.getName().equalsIgnoreCase(idB)) startB = location;
        }

        if(startA != null && startB != null) {
            LocalSearch.performLocalSearch(locations, edges, payoffs, startA, startB);
        } else throw new NoSuchElementException("Location with given name not in location file");
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

    private static int[][] createPayoffMatrix(ArrayList<City> cities, ArrayList<Location> locations) {
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
