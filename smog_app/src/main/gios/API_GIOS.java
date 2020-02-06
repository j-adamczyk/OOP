package gios;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.AirIndex;
import model.Entry;

import javax.naming.CommunicationException;
import java.io.*;
import java.lang.reflect.Type;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * API strategy for GIOS air quality API, part of Strategy pattern. All private nested classes are here for GSON to enable deserializing JSON files
 * and are later converted to normal classes (and stored that way in cache).
 */
public class API_GIOS implements IAPIStrategy
{
    private StringBuffer log;

    /**
     * Method required by gios.IAPIStrategy interface, return all information from API as properly formatted model.Entry list.
     * @return list of all information from API as model.Entry list
     * @throws CommunicationException if there was a problem connecting to the Internet or GIOS site
     */
    public List<Entry> getInfo() throws CommunicationException
    {
        this.log = new StringBuffer("Error log:\n");

        List<Entry> result = new ArrayList<>();

        Gson gson = new GsonBuilder().serializeNulls().create();

        List<Station> stations = getStations();

        ExecutorService executorService = Executors.newFixedThreadPool(20);

        for (Station station : stations)
        {
            executorService.execute(() ->
            {
                Entry entry = new Entry();

                entry.setStationID(station.id);
                entry.setStationName(station.stationName);

                List<Sensor> sensors = getSensors(station.id.toString());

                entry.setParameterMeasurements(new HashMap<>());

                for (Sensor sensor : sensors)
                    entry.getParameterMeasurements().
                            put(sensor.param.paramCode, getSensorMeasurements(sensor.id.toString()));

                AirIndexOutside airIndex = getAirIndex(station.id.toString());

                AirIndex airIndexCache = new AirIndex();

                airIndexCache.setStIndexLevel(
                        (airIndex.stIndexLevel != null) ? airIndex.stIndexLevel.indexLevelName : "-");
                airIndexCache.setSo2IndexLevel(
                        (airIndex.so2IndexLevel != null) ? airIndex.so2IndexLevel.indexLevelName : "-");
                airIndexCache.setNo2IndexLevel(
                        (airIndex.no2IndexLevel != null) ? airIndex.no2IndexLevel.indexLevelName : "-");
                airIndexCache.setCoIndexLevel(
                        (airIndex.coIndexLevel != null) ? airIndex.coIndexLevel.indexLevelName : "-");
                airIndexCache.setPm10IndexLevel(
                        (airIndex.pm10IndexLevel != null) ? airIndex.pm10IndexLevel.indexLevelName : "-");
                airIndexCache.setPm25IndexLevel(
                        (airIndex.pm25IndexLevel != null) ? airIndex.pm25IndexLevel.indexLevelName : "-");
                airIndexCache.setO3IndexLevel(
                        (airIndex.o3IndexLevel != null) ? airIndex.o3IndexLevel.indexLevelName : "-");
                airIndexCache.setC6h6IndexLevel(
                        (airIndex.c6h6IndexLevel != null) ? airIndex.c6h6IndexLevel.indexLevelName : "-");

                entry.setAirIndex(airIndexCache);

                result.add(entry);
            });
        }

        executorService.shutdown();

        try { executorService.awaitTermination(30, TimeUnit.SECONDS); }
        catch (InterruptedException e) { System.out.println("Error while waiting for executor service termination!"); }

        if (!this.log.toString().equals("Error log:\n"))
            System.out.println("There were errors while getting data, see error log for more details.");

        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("C:\\Users\\jakub\\Desktop\\Obiektowe\\log.txt")));
            writer.write(this.log.toString());
            writer.flush();
            writer.close();
        }
        catch (IOException e) { System.out.println("Error while saving log file!"); }

        return result;
    }

    /**
     * Utility method for getting list of all stations and basic information about them.
     * @return list of stations
     * @throws CommunicationException if there was a problem connecting to the Internet or GIOS site
     */
    public List<Station> getStations() throws CommunicationException
    {
        List<Station> stations = new ArrayList<>();

        boolean downloaded = false;
        int tries_number = 0;

        while (!downloaded && tries_number < 10)
        {
            try
            {
                Gson gson = new GsonBuilder().serializeNulls().create();
                URL url = new URL("http://api.gios.gov.pl/pjp-api/rest/station/findAll");
                InputStreamReader reader = new InputStreamReader(url.openStream());
                Type stationListType = new TypeToken<ArrayList<Station>>()
                {
                }.getType();
                stations = gson.fromJson(reader, stationListType);
                reader.close();
            }
            catch (Exception e)
            {
                tries_number++;
                continue;
            }

            downloaded = true;
        }

        if (!downloaded)
        {
            System.out.println("Error while getting list of stations, 10 attempts failed, terminating program!");
            throw new CommunicationException();
        }

        return stations;
    }

    /**
     * Utility method for getting list of all sensors and basic information about them for given station.
     * @param stationID station identification number
     * @return list of sensors
     */
    public List<Sensor> getSensors(String stationID)
    {
        List<Sensor> sensors = new ArrayList<>();

        if (stationID == null || stationID.equals(""))
            return sensors;

        boolean downloaded = false;
        int tries_number = 0;

        while (!downloaded && tries_number < 10)
        {
            try
            {
                Gson gson = new GsonBuilder().serializeNulls().create();
                URL url = new URL("http://api.gios.gov.pl/pjp-api/rest/station/sensors/" + stationID);
                InputStreamReader reader = new InputStreamReader(url.openStream());
                Type sensorListType = new TypeToken<ArrayList<Sensor>>() {}.getType();
                sensors = gson.fromJson(reader, sensorListType);
                reader.close();
            }
            catch (Exception e)
            {
                tries_number++;
                continue;
            }

            downloaded = true;
        }

        if (!downloaded)
        {
            this.log.append("Error while getting list of sensors from station with ID ").append(stationID).
                    append(", 10 attempts failed, data may be partially corrupted!\n");
        }

        return sensors;
    }

    /**
     * Utility method for getting parameter measurements from single sensor.
     * @param sensorID sensor identification number
     * @return map of measurements from given sensor
     */
    public Map<String,Double> getSensorMeasurements(String sensorID)
    {
        Map<String, Double> measurementValues = new TreeMap<>();
        MeasurementData measurements = new MeasurementData();

        if (sensorID == null || sensorID.equals(""))
            return measurementValues;

        boolean downloaded = false;
        int tries_number = 0;

        while (!downloaded && tries_number < 10)
        {
            try
            {
                Gson gson = new GsonBuilder().serializeNulls().create();
                URL url = new URL("http://api.gios.gov.pl/pjp-api/rest/data/getData/" + sensorID);
                InputStreamReader reader = new InputStreamReader(url.openStream());
                measurements = gson.fromJson(reader, MeasurementData.class);
                reader.close();
            }
            catch (Exception e)
            {
                tries_number++;
                continue;
            }

            downloaded = true;
        }

        try
        {
            for (Measurement measurement : measurements.values)
                measurementValues.put(measurement.date, measurement.value);
        }
        catch (NullPointerException ignored) {}

        if (!downloaded)
        {
            this.log.append("Error while getting parameter measurements from sensor with ID ").append(sensorID).
                    append(", 10 attempts failed, data may be partially corrupted!\n");
        }

        return measurementValues;
    }

    /**
     * Utility method for air index (overall and for all parameters) for given station.
     * @param stationID station identification number
     * @return air index object consisting of all appropriate air indices
     */
    public AirIndexOutside getAirIndex(String stationID)
    {
        AirIndexOutside airIndex = new AirIndexOutside();

        if (stationID == null || stationID.equals(""))
            return airIndex;

        boolean downloaded = false;
        int tries_number = 0;

        while (!downloaded && tries_number < 10)
        {
            try
            {
                Gson gson = new GsonBuilder().serializeNulls().create();
                URL url = new URL("http://api.gios.gov.pl/pjp-api/rest/aqindex/getIndex/" + stationID);
                InputStreamReader reader = new InputStreamReader(url.openStream());
                airIndex = gson.fromJson(reader, AirIndexOutside.class);
                reader.close();
            }
            catch (Exception e)
            {
                tries_number++;
                continue;
            }

            downloaded = true;
        }

        if (!downloaded)
        {
            this.log.append("Error while getting air index from station with ID ").append(stationID).
                    append(", 10 attempts failed, data may be partially corrupted!\n");
        }

        return airIndex;
    }

    class MeasurementData
    {
        Measurement[] values;

        MeasurementData()
        { this.values = new Measurement[1]; }
    }

    class AirIndexInside
    {
        String indexLevelName;

        AirIndexInside()
        { this.indexLevelName = ""; }
    }

    class AirIndexOutside
    {
        AirIndexInside stIndexLevel;
        AirIndexInside so2IndexLevel;
        AirIndexInside no2IndexLevel;
        AirIndexInside coIndexLevel;
        AirIndexInside pm10IndexLevel;
        AirIndexInside pm25IndexLevel;
        AirIndexInside o3IndexLevel;
        AirIndexInside c6h6IndexLevel;

        AirIndexOutside()
        {
            this.stIndexLevel = new AirIndexInside();
            this.so2IndexLevel = new AirIndexInside();
            this.no2IndexLevel = new AirIndexInside();
            this.coIndexLevel = new AirIndexInside();
            this.pm10IndexLevel = new AirIndexInside();
            this.pm25IndexLevel = new AirIndexInside();
            this.o3IndexLevel = new AirIndexInside();
            this.c6h6IndexLevel = new AirIndexInside();
        }
    }

    class Measurement
    {
        String date;
        Double value;

        Measurement()
        {
            this.date = "0000-00-00 00:00:00";
            this.value = 0.0;
        }
    }

    class Parameter
    {
        String paramCode;

        Parameter()
        { this.paramCode = ""; }
    }

    class Sensor
    {
        Integer id;
        Parameter param;

        Sensor()
        {
            this.id = 0;
            this.param = new Parameter();
        }
    }

    class Station
    {
        Integer id;
        String stationName;

        Station()
        {
            this.id = 0;
            this.stationName = "";
        }
    }
}