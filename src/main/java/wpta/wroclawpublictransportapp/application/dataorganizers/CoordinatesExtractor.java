package wpta.wroclawpublictransportapp.application.dataorganizers;

/**
 * Class extract coordinates from javascript response
 */
public class CoordinatesExtractor {
    public Double[] extract(String result) {
        Double[] coordinates = new Double[2];
        StringBuilder tmp = new StringBuilder(result);
        tmp.deleteCharAt(tmp.length()-1);
        tmp.deleteCharAt(0);

        result = tmp.toString();
        result = result.replace(" ", "");

        coordinates[0] = Double.valueOf(result.split(",")[0]);
        coordinates[1] = Double.valueOf(result.split(",")[1]);

        return coordinates;
    }
}
