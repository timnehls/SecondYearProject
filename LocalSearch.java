import java.util.ArrayList;

public class LocalSearch {

    // changed City startA, startB to Location startA, startB; mechanics are the same
    public static void performLocalSearch(ArrayList<Location> locations, boolean[][] adjacencyMatrix, int[][] payoffs, Location startA, Location startB) {
        int totalInhabitants = 1626055;
        int idA = locations.indexOf(startA);
        int idB = locations.indexOf(startB);

        System.out.println("\n\n\nLocal search: \n");

        System.out.println("Starting location firm A: " + startA.getName());
        System.out.println("Starting location firm B: " + startB.getName() + "\n");

        int payoffA = payoffs[idA][idB];
        int payoffB = totalInhabitants - payoffA;

        boolean changedA = true, changedB = true;
        
        while(changedA || changedB) {
            ArrayList<Integer> neighboursA = findNeighbours(idA, adjacencyMatrix);
            ArrayList<Integer> neighboursB = findNeighbours(idB, adjacencyMatrix);

            changedA = false;
            changedB = false;

            int prevIDA = idA;
            int prevIDB = idB;

            payoffA = payoffs[idA][idB];

            for(int i = 0; i < neighboursA.size(); i++) {
                int neighbourIDA = neighboursA.get(i);

                int payoffNeighbourA = payoffs[neighbourIDA][idB];

                if(payoffNeighbourA > payoffA) {
                    idA = neighbourIDA;
                    payoffA = payoffNeighbourA;

                    changedA = true;
                }
            }

            if(changedA) System.out.println("Firm A changed from " + locations.get(prevIDA).getName() + " to " + locations.get(idA).getName() + ".");
            else System.out.println("Firm A stays at " + locations.get(idA).getName() + " for this round.");

            payoffB = totalInhabitants - payoffA;

            for(int i = 0; i < neighboursB.size(); i++) {
                int neighbourIDB = neighboursB.get(i);

                int payoffNeighbourB = totalInhabitants - payoffs[idA][neighbourIDB];

                if(payoffNeighbourB > payoffB) {
                    idB = neighbourIDB;
                    payoffB = payoffNeighbourB;

                    changedB = true;
                }
            }

            if(changedB) System.out.println("Firm B changed from " + locations.get(prevIDB).getName() + " to " + locations.get(idB).getName() + ".");
            else System.out.println("Firm B stays at " + locations.get(idB).getName() + " for this round.");
        }

        System.out.println("\nEnd locations: " + locations.get(idA).getName() + " and " + locations.get(idB).getName());
        System.out.println("Payoff firm A: " + payoffA + "; payoff firm B: " + payoffB);

    }

    // changed cityID to locationID
    private static ArrayList<Integer> findNeighbours(int locationID, boolean[][] adjacencyMatrix) {
        ArrayList<Integer> neighbours = new ArrayList<>();
        
        for(int column = 0; column < adjacencyMatrix[locationID].length; column++) {
            if(adjacencyMatrix[locationID][column] && locationID != column) neighbours.add(column);
        }
        
        return neighbours;
    }
}
