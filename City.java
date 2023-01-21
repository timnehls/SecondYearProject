public class City {
    private final String name;
    private final int size; 
    private final double latitude, longitude;

    public City(String name, int size, double latitude, double longitude) {
        this.name = name;
        this.size = size;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return this.name;
    }

    public int getSize() {
        return this.size;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }


    // Week 5 implementation in Programming
    public double dist(City anotherCity) {
        double radius = 6372.8;
        double dLat = Math.toRadians(anotherCity.latitude - this.latitude);
        double dLon = Math.toRadians(anotherCity.longitude - this.longitude);

        double lat1 = Math.toRadians(this.latitude);
        double lat2 = Math.toRadians(anotherCity.latitude);

        double a = Math.pow(Math.sin(dLat / 2),2)
            + Math.pow(Math.sin(dLon / 2),2) * Math.cos(lat1) * Math.cos(lat2);

        double c = 2 * Math.asin(Math.sqrt(a));

        return radius * c;
    }


}
