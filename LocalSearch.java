import java.util.ArrayList;

public class LocalSearch {

    public static void performLocalSearch(ArrayList<Location> cities, boolean[][] adjacencyMatrix, int[][] payoffs, Location startA, Location startB) {
        int totalInhabitants = 1626055;
        int idA = cities.indexOf(startA);
        int idB = cities.indexOf(startB);

        System.out.println("Starting location firm A: " + startA.getName());
        System.out.println("Starting location firm B: " + startB.getName());

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

            if(changedA) System.out.println("Firm A changed from " + cities.get(prevIDA).getName() + " to " + cities.get(idA).getName() + ".");
            else System.out.println("Firm A stays at " + cities.get(idA).getName() + " for this round.");

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

            if(changedB) System.out.println("Firm B changed from " + cities.get(prevIDB).getName() + " to " + cities.get(idB).getName() + ".");
            else System.out.println("Firm B stays at " + cities.get(idB).getName() + " for this round.");
        }

        System.out.println("End locations: " + cities.get(idA).getName() + " and " + cities.get(idB).getName());
        System.out.println("Payoff firm A: " + payoffA + "; payoff firm B: " + payoffB);

    }

    private static ArrayList<Integer> findNeighbours(int cityID, boolean[][] adjacencyMatrix) {
        ArrayList<Integer> neighbours = new ArrayList<>();
        
        for(int column = 0; column < adjacencyMatrix[cityID].length; column++) {
            if(adjacencyMatrix[cityID][column] && cityID != column) neighbours.add(column);
        }
        
        return neighbours;
    }
}
