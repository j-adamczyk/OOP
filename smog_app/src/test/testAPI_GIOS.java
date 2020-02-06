import gios.API_GIOS;
import org.junit.*;

import javax.naming.CommunicationException;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * Integration tests for GIOS API.
 */
public class testAPI_GIOS
{
    private API_GIOS api;

    @Before
    public void initialize()
    {
        this.api = new API_GIOS();

        boolean internet = true;
        try
        {
            URL url = new URL("https://www.google.com/");
            URLConnection connection = url.openConnection();
            connection.connect();
        }
        catch (Exception e)
        { internet = false; }

        Assert.assertTrue(internet);
    }

    @Test
    public void testGetListOfStations() throws CommunicationException
    {
        List<API_GIOS.Station> stations = this.api.getStations();

        Assert.assertFalse(stations.isEmpty());
    }

    @Test
    public void testGetSensors()
    {
        List<API_GIOS.Sensor> sensors = this.api.getSensors("14");

        Assert.assertFalse(sensors.isEmpty());
    }

    @Test
    public void testGetSensorsWithEmptyID()
    {
        List<API_GIOS.Sensor> sensors = this.api.getSensors("");

        Assert.assertTrue(sensors.isEmpty());
    }

    @Test
    public void testGetSensorsWithNullID()
    {
        List<API_GIOS.Sensor> sensors = this.api.getSensors(null);

        Assert.assertTrue(sensors.isEmpty());
    }

    @Test
    public void testGetSensorMeasurements()
    {
        Map<String,Double> sensorMeasurements = this.api.getSensorMeasurements("92");

        Assert.assertFalse(sensorMeasurements.isEmpty());
    }

    @Test
    public void testGetSensorMeasurementsWithEmptyID()
    {
        Map<String,Double> measurements = this.api.getSensorMeasurements("");

        Assert.assertTrue(measurements.isEmpty());
    }

    @Test
    public void testGetSensorMeasurementsWithNullID()
    {
        Map<String,Double> measurements = this.api.getSensorMeasurements(null);

        Assert.assertTrue(measurements.isEmpty());
    }

    @Test
    public void testGetAirIndex()
    {
        API_GIOS.AirIndexOutside airIndex = this.api.getAirIndex("52");

        Assert.assertNotNull(airIndex);

        for (Field field : airIndex.getClass().getDeclaredFields())
        {
            field.setAccessible(true);
            try
            {
                Assert.assertNotEquals("", field.get(airIndex));
            }
            catch (IllegalAccessException ignored) { }
        }
    }

    @Test
    public void testGetAirIndexWithEmptyID()
    {
        API_GIOS.AirIndexOutside airIndex = this.api.getAirIndex("");

        Assert.assertNotNull(airIndex);

        for (Field field : airIndex.getClass().getDeclaredFields())
        {
            field.setAccessible(true);
            try
            {
                Assert.assertNotNull(field.get(airIndex));
            }
            catch (IllegalAccessException ignored) { }
        }
    }

    @Test
    public void testGetAirIndexWithNullID()
    {
        API_GIOS.AirIndexOutside airIndex = this.api.getAirIndex(null);

        Assert.assertNotNull(airIndex);

        for (Field field : airIndex.getClass().getDeclaredFields())
        {
            field.setAccessible(true);
            try
            {
                Assert.assertNotNull(field.get(airIndex));
            }
            catch (IllegalAccessException ignored) { }
        }
    }
}
