import java.util.Map;

/**
 * Data type to store information from API in.
 * It's stored in UtilSingleton in map: key is station name and value is Entry for that station.
 * parameterMeasurements map has parameter code as key and value is map of measurements for that parameter.
 * That map has measurement date and time as key and measurement value as value.
 * AirIndex is simply collection of air quality indexes (overall and for parameters) wrapped in object.
 */
public class Entry
{
    Integer stationID;
    String stationName;
    Map<String,Map<String,Double>> parameterMeasurements;
    AirIndex airIndex;
}