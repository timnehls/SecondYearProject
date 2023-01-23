import java.util.ArrayList;
import java.util.Arrays;

public class Elimination {

    


    public static City[] citiesLeftAfterElimination(int[][] payoffMatrix, City[] cities) {
        int[][] payoff = payoffMatrix;

        boolean cityDeleted = false;

        ArrayList<City> citiesLeft = new ArrayList<>(Arrays.asList(cities));

        int round = 0;
        do {
            round++;
            System.out.println("Round " + round + ":");

            cityDeleted = false;
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
                    }
                }
            }
        } while(cityDeleted && payoff.length > 1);

        System.out.println("No more cities to be eliminated.");
        System.out.println("Cities left: " + citiesLeft.size());
        for(City city : citiesLeft) System.out.print(city.getName() + " ");
        
        return (City[]) citiesLeft.toArray();
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
