import java.util.ArrayList;

public class Elimination {

    public static int[][][] createPayoffMatrix(Graph graph) {
        ArrayList<City> cities = graph.getCities();
        int numberOfCities = cities.size();
        int totalNumberOfInhabitants = 0;

        for(City city : cities) totalNumberOfInhabitants += city.getSize();


        int[][][] payoffMatrix = new int[numberOfCities][numberOfCities][2];

        for(int i = 0; i < numberOfCities; i++) {
            for(int j = 0; j < numberOfCities; j++) {
                City firstCity = cities.get(i);
                City secondCity = cities.get(j);

                if(firstCity == secondCity) {
                    int[] payoffs = {totalNumberOfInhabitants / 2, totalNumberOfInhabitants / 2};
                    payoffMatrix[i][j] = payoffs;
                } else {
                    int customersFirst = 0;
                    int customersSecond = 0;
    
    
                    for(City city : cities) {
                        int size = city.getSize();
    
                        double distanceToFirst = city.dist(firstCity);
                        double distanceToSecond = city.dist(secondCity);
    
                        if(distanceToFirst < distanceToSecond) {
                            customersFirst += size;
                        } else if(distanceToFirst > distanceToSecond) {
                            customersSecond += size;
                        } else {
                            customersFirst += size / 2;
                            customersSecond += size / 2;
                        }
                    }
    
                    int[] payoffs = {customersFirst, customersSecond};
                    payoffMatrix[i][j] = payoffs;
                }
            }
        }

        return payoffMatrix;
    }

    public static void eliminate(int[][][] payoffMatrix, Graph graph) {
        int[][][] matrix = payoffMatrix;

        // row elimination
        for(int i = 0; i < matrix.length; i++) {    
            boolean[] row = new boolean[matrix[i].length];
            
            for(int j = 0; j < matrix.length; j++) {
                if(j != i) {
                    for(int k = 0; k < matrix[i].length; k++) {
                        if(matrix[i][k][0] > matrix[j][k][0]) {
                            row[k] = false;
                            break;
                        } else row[k] = true;
                    }

                    if(isDominated(row)) break;
                }
            }

            if(isDominated(row)) {
                System.out.println("The city " + graph.getCities().get(i).getName() + " has been eliminated.");

                int[][][] newMatrix = new int[matrix.length-1][matrix[1].length][2];

                for(int a = 0; a < newMatrix.length; a++) {
                    if(a != i) {
                        newMatrix[a] = matrix[a];
                    }
                }

                matrix = newMatrix;
            }
        }

        System.out.println("Number of remaining cities: " + matrix.length);
        // return null;
    }

    public static boolean isDominated(boolean[] row) {
        for(boolean payoff : row)
            if(!payoff) return false;
        return true;
    }
}
