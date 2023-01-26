import java.util.ArrayList;

public class Elimination {

    // changed name of method and list of cities to list of locations; mechanics are the same
    public static void locationsLeftAfterElimination(int[][] payoffMatrix, ArrayList<Location> locations) {
        int[][] payoff = payoffMatrix;

        boolean locationDeleted = true;

        ArrayList<Location> locationsLeft = locations;


        System.out.println("\n\nIterated elimination of dominated strategies: \n");

        int round = 0;
        while(locationDeleted && payoff.length > 1) {
            ArrayList<Location> toDelete = new ArrayList<>();
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
                            Location LocationToDelete = locationsLeft.get(row);
                            locationDeleted = true;

                            toDelete.add(LocationToDelete);
                            
                            break;
                        }

                    }
                }
            }

            for(int i = 0; i < toDelete.size(); i++) {
                Location cityToDelete = toDelete.get(i);

                System.out.println("City " + cityToDelete.getName() + " has been eliminated.");

                payoff = removeFromMatrix(payoff, locationsLeft.indexOf(cityToDelete));
                locationsLeft.remove(locationsLeft.indexOf(cityToDelete));
            }

        } 

        System.out.println("No more cities to be eliminated.\n");
        System.out.println("Cities left: " + locationsLeft.size());
        for(Location location : locationsLeft) System.out.print(location.getName() + " ");
    }
    

    // just taken from the original file
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
}
