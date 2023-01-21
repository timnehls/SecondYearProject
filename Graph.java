import java.util.ArrayList;

public class Graph {
    private ArrayList<City> cities;
    private ArrayList<Edge> edges;

    public Graph() {
        this.cities = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    public ArrayList<City> getCities() {
        return this.cities;
    }

    public ArrayList<Edge> getEdges() {
        return this.edges;
    }

    public void addCity(City city) {
        this.cities.add(city);
    }

    public void addEdge(City city, City anotherCity) {
        this.edges.add(new Edge(city, anotherCity));
    }

    public double[][] createAdjacencyMatrix() {
        double[][] matrix = new double[50][50];

        for(Edge edge : edges) {
            City firstCity = edge.getCities()[0];
            City secondCity = edge.getCities()[1];

            int posOfFirstInList = cities.indexOf(firstCity);
            int posOfSecondInList = cities.indexOf(secondCity);

            matrix[posOfFirstInList][posOfSecondInList] = firstCity.dist(secondCity);
            matrix[posOfSecondInList][posOfFirstInList] = firstCity.dist(secondCity);
        }

        return matrix;
    }

}
