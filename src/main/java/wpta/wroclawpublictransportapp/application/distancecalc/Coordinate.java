package wpta.wroclawpublictransportapp.application.distancecalc;

/**
 * Dummy model of coordinate representation in system
 */
public class Coordinate {
    private Double lat;
    private Double lng;

    public Coordinate(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }
}
