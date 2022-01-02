package wpta.wroclawpublictransportapp.application.distancecalc;

/**
 * Class calculate distance between two points represented by coordinates based on Haversine formula
 * <a href=https://en.wikipedia.org/wiki/Haversine_formula>Haversine formula</a>
 */
public class DistanceCalculator {

    /**
     * Earth radius
     */
    private final int R = 6378137;

    public double calculateDistance(Coordinate p1, Coordinate p2) {
        double dLat = radians(p2.getLat() - p1.getLat());
        double dLong = radians(p2.getLng() - p1.getLng());

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(radians(p1.getLat())) * Math.cos(radians(p2.getLat())) *
                        Math.sin(dLong / 2) * Math.sin(dLong / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // distance in meters
        return R * c;
    }

    public static Double radians(Double x) {
        return x * Math.PI / 180;
    }
}
