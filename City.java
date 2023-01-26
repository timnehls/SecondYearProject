// now inherits Location's attributes and methods
public class City extends Location {
    private final int size; 

    public City(String name, int size, double latitude, double longitude) {
        super(name, latitude, longitude);
        this.size = size;
    }
    
    public int getSize() {
        return this.size;
    }
}
