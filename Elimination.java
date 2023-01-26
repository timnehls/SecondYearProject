import java.util.ArrayList;

public class Elimination {

    public static void citiesLeftAfterElimination(int[][] payoffMatrix, ArrayList<Location> locations) {
        int[][] payoff = payoffMatrix;

        boolean locationDeleted = true;

        ArrayList<Location> locationsLeft = locations;

        System.out.println("\n\nIterated elimination of dominated strategies: \n");

        int round = 0;
        while(locationDeleted && payoff.length > 1) {
            round++;
            System.out.println("\nRound " + round + ":");

            locationDeleted = false;
            for(int row = 0; row < payoff.length; row++) {
                for(int secondRow = 0; secondRow < payoff.length; secondRow++) {
                    boolean rowDominated = true;
                    if(row != secondRow) {
                        for(int column = 0; column < payoff[row].length; column++) {
                            if(payoff[row][column] >= payoff[secondRow][column]) {
                                rowDominated = false;
                                break;
                            }
                        }
                        if(rowDominated) {
                            System.out.println("City " + locationsLeft.get(row).getName() + " has been eliminated.");
                            locationDeleted = true;
                            
                            locationsLeft.remove(row);
                            payoff = removeFromMatrix(payoff, row);
        
                            break;
                        }
                    }
                }
            }
        } 

        System.out.println("No more cities to be eliminated.\n");
        System.out.println("Cities left: " + locationsLeft.size());
        for(Location location : locationsLeft) System.out.print(location.getName() + " ");
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
