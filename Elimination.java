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

    public int[][][] eliminate(int[][][] payoffMatrix) {
        for(int i = 0; i < payoffMatrix.length; i++) {
            for(int j = 0; j < payoffMatrix[i].length; j++) {
            }
        }

        return null;
    }
}
