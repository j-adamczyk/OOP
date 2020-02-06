package model;

import java.util.Map;

/**
 * Data type to store information from API in.
 * It's stored in model.UtilSingleton in map: key is station name and value is model.Entry for that station.
 * parameterMeasurements map has parameter code as key and value is map of measurements for that parameter.
 * That map has measurement date and time as key and measurement value as value.
 * model.AirIndex is simply collection of air quality indexes (overall and for parameters) wrapped in object.
 */
public class Entry
{
    private Integer stationID;
    private String stationName;
    private Map<String,Map<String,Double>> parameterMeasurements;
    private AirIndex airIndex;

    public Integer getStationID() {
        return stationID;
    }

    public void setStationID(Integer stationID) {
        this.stationID = stationID;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public Map<String, Map<String, Double>> getParameterMeasurements() {
        return parameterMeasurements;
    }

    public void setParameterMeasurements(Map<String, Map<String, Double>> parameterMeasurements) {
        this.parameterMeasurements = parameterMeasurements;
    }

    public AirIndex getAirIndex() {
        return airIndex;
    }

    public void setAirIndex(AirIndex airIndex) {
        this.airIndex = airIndex;
    }
}