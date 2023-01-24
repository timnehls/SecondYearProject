public class Location {
    private final String name;
    private final double latitude, longitude;

    public Location(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return this.name;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public double dist(Location anotherLocation) {
        double radius = 6372.8;
        double dLat = Math.toRadians(anotherLocation.latitude - this.latitude);
        double dLon = Math.toRadians(anotherLocation.longitude - this.longitude);

        double lat1 = Math.toRadians(this.latitude);
        double lat2 = Math.toRadians(anotherLocation.latitude);

        double a = Math.pow(Math.sin(dLat / 2),2)
            + Math.pow(Math.sin(dLon / 2),2) * Math.cos(lat1) * Math.cos(lat2);

        double c = 2 * Math.asin(Math.sqrt(a));

        return radius * c;
    }

}
