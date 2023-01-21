public class Edge {
    private final City[] cities;

    public Edge(City firstCity, City secondCity) {
        this.cities = new City[2];

        this.cities[0] = firstCity;
        this.cities[1] = secondCity;

    }

    public City[] getCities() {
        return this.cities;
    }

}
