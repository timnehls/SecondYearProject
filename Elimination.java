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
        int[][] payoff = payoffMatrix;

        boolean cityDeleted = false;

        ArrayList<City> citiesLeft = new ArrayList<>(Arrays.asList(cities));

        do {
            for(int row = 0; row < payoff.length; row++) {
                boolean rowDominated = true;
    
                for(int secondRow = 0; secondRow < payoff.length; secondRow++) {
                    if(row != secondRow) {
                        for(int column = 0; column < payoff[row].length; column++) {
                            if(payoff[row][column] >= payoff[secondRow][column]) {
                                rowDominated = false;
                                break;
                            }
                        }
                        if(rowDominated) {
                            System.out.println("City " + citiesLeft.get(row).getName() + " has been eliminated.");
                            cityDeleted = true;
                            
                            citiesLeft.remove(row);
                            payoff = removeFromMatrix(payoff, row);
        
                            break;
                        }
                        else cityDeleted = false;
                    }
                }
            }
        } while(cityDeleted && payoff.length > 1);

        System.out.println("Cities left: " + citiesLeft.size());
        for(City city : citiesLeft) System.out.print(city.getName() + " ");
        
        return payoff;
    }


    private static int[][] removeFromMatrix(int[][] matrix, int cityID) {
        int[][] newMatrix = new int[matrix.length-1][matrix[0].length-1];
        
        int countRow = 0;
        for(int ro = 0; ro < matrix.length; ro++) {
            if(cityID == ro) {
                continue;
            }

            int countColumn = 0;
            for(int col = 0; col < matrix.length; col++) {
                if(cityID == col) {
                    continue;
                }
                newMatrix[countRow][countColumn] = matrix[ro][col];
                countColumn++;
            }

            countRow++;
        }
        
        return newMatrix;
    }

    public static boolean isDominated(boolean[] row) {
        for(boolean payoff : row)
            if(!payoff) return false;
        return true;
    }
}
