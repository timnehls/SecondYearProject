import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String fileCities = "cities.txt", fileLocations = "locations.txt"; // specify here your input files
        boolean transport = true; // specify whether you want to enable the second extension here

        /* 
         * You have two choices:
         * 1. conduct iterative elimination of dominated strategies by
         * calling: 
         */

        iterativeElimination(fileCities, fileLocations, transport);

        /*
         * 2. determine a location by using the local search approach
         * by specifying the names of the starting locations
         * and a maximum edge length, e.g.:
         */

        String startA = "Merzpark", startB = "ErftKarree";
        int maxDistance = 12;
 
        localSearch(fileCities, fileLocations, maxDistance, startA, startB, transport);
    }


    // difference to the original method: two input files, possibility to enable the transport extension
    private static void iterativeElimination(String filenameCities, String filenameLocations, boolean transport) {
        ArrayList<City> cities = readCities(filenameCities);
        ArrayList<Location> locations;
        
        // possibility to still conduct the elimination with locations = cities
        if(filenameCities.equals(filenameLocations)) {
            locations = new ArrayList<>();
            for(City city : cities) locations.add(city);
        } else {
            locations = readLocations(filenameLocations);
        }

        int[][] payoffs;

        // extension enabler (payoff tables differ)
        if(!transport) {
            payoffs = createPayoffMatrix(cities, locations);
        } 
        else {
            payoffs = createPayoffMatrixTransport(cities, locations);
        }

        Elimination.locationsLeftAfterElimination(payoffs, locations);
    }

    // difference: two input files
    private static void localSearch(String filenameCities, String filenameLocations, int maxDistance, String idA, String idB, boolean transport) throws NoSuchElementException{
        ArrayList<City> cities = readCities(filenameCities);
        ArrayList<Location> locations;
        
        // locations = cities?
        if(filenameCities.equals(filenameLocations)) {
            locations = new ArrayList<>();
            for(City city : cities) locations.add(city);
        } else {
            locations = readLocations(filenameLocations);
        }

        boolean[][] edges = createEdgeMatrix(locations, maxDistance);

        int[][] payoffs;

        if(!transport) {
            payoffs = createPayoffMatrix(cities, locations);
        } 
        else {
            payoffs = createPayoffMatrixTransport(cities, locations);
        }

        Location startA = null;
        Location startB = null;

        for(Location location : locations) {
            if(location.getName().equalsIgnoreCase(idA)) startA = location;
            else if(location.getName().equalsIgnoreCase(idB)) startB = location;
        }

        if(startA != null && startB != null) {
            LocalSearch.performLocalSearch(locations, edges, payoffs, startA, startB);
        } else throw new NoSuchElementException("Location with given name not in location file!");
    }


    // ----------- helper methods ----------- //

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

    // new method to read in locations (no size attribute)
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

    // now also has parameter for locations
    private static int[][] createPayoffMatrix(ArrayList<City> cities, ArrayList<Location> locations) {
        int numberOfLocations = locations.size();

        int[][] payoffMatrix = new int[numberOfLocations][numberOfLocations];

        // now checks for two locations which one is closer to the cities
        for(int i = 0; i < numberOfLocations; i++) {
            for(int j = 0; j < numberOfLocations; j++) {
                Location firstLocation = locations.get(i);
                Location secondLocation = locations.get(j);

                int customers = 0;

                // distances are now calculated from each city to the two locations
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

    // new calculation parameters
    private static int[][] createPayoffMatrixTransport(ArrayList<City> cities, ArrayList<Location> locations) {
        // new: payoff is dependent on location of railway station that items are collected from
        Location loadingStation = new Location("KoelnEifeltor", 50.88858, 6.92080);
        
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

                // distance from loading station to firm A
                double distLoadingA = firstLocation.dist(loadingStation);

                // changed this calculation
                payoffMatrix[i][j] = customers - (int) (distLoadingA*50) - (int) (distLoadingA / 100 * customers) ;
            }
        }

        return payoffMatrix;
    }



}
