import java.util.ArrayList;
import java.util.Arrays;

public class Elimination {

    public static int[][] createPayoffMatrix(boolean[][] edges, City[] cities) {
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


    public static int[][] eliminate(int[][] payoffMatrix, City[] cities) {
        ArrayList<City> citiesLeft = new ArrayList<>(Arrays.asList(cities));

        int[][] matrix = payoffMatrix;
        int countEliminated = 0;
        int round = 1;

        do {
            System.out.println("Round " + round);
            for(int i = 0; i < matrix.length; i++) {    
                boolean[] isRowDominated = new boolean[matrix[i].length];
                
                for(int j = 0; j < matrix.length; j++) {
                    if(j != i) {
                        for(int k = 0; k < matrix[i].length; k++) {
                            if(matrix[i][k] > matrix[j][k]) {
                                isRowDominated[k] = false;
                                break;
                            } else isRowDominated[k] = true;
                        }
    
                        if(isDominated(isRowDominated)) break;
                    }
                }
    
                if(isDominated(isRowDominated)) {
                    City removedCity = citiesLeft.get(i);
                    System.out.println("City " + removedCity.getName() + " has been removed.");
                    citiesLeft.remove(i);

                    matrix = deleteCity(matrix, i);
                    countEliminated++;
                }
            }
    
            System.out.println("Number of remaining cities: " + matrix.length);
            round++;
        } while(countEliminated > 0 && matrix.length > 1);


        return matrix;
    }


    private static int[][] deleteCity(int[][] matrix, int cityID) {
        // System.out.println("The city " + graph.getCities().get(cityID).getName() + " has been eliminated.");

        int[][] newMatrix = new int[matrix.length-1][matrix[0].length];

        for(int a = 0; a < newMatrix.length; a++) {
            if(a != cityID) {
                newMatrix[a] = matrix[a];
            }
        }

        matrix = newMatrix;

        newMatrix = new int[matrix.length][matrix[0].length-1];

        for(int a = 0; a < newMatrix.length; a++) {
            for(int b = 0; b < newMatrix[0].length; b++) {
                if(b != cityID) {
                    newMatrix[a][b] = matrix[a][b];
                }
            }
        }

        matrix = newMatrix;
        return matrix;
    }

    public static boolean isDominated(boolean[] row) {
        for(boolean payoff : row)
            if(!payoff) return false;
        return true;
    }
}
