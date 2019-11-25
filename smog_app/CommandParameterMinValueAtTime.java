import org.mockito.internal.matchers.Null;

import java.util.HashMap;
import java.util.Map;

/**
 * Gets parameter with smallest value at given station and time, concrete command part of Command design pattern.
 */
public class CommandParameterMinValueAtTime implements ICommand
{
    private String dateAndTime;
    private String stationName;
    private String parameter;
    private Double minValue;

    /**
     * Gets all information and fills all attribute fields' values.
     * @param stationName name of single station
     * @param dateAndTime date and time in format YYYY-MM-DD HH:MM:SS
     */
    public void execute (String stationName, String dateAndTime) throws NullPointerException
    {
        this.dateAndTime = dateAndTime;
        this.stationName = stationName;

        String parameter = "";
        Double minValue = 99999.0;

        Map<String,Map<String,Double>> measurements;

        try
        {
            measurements = UtilSingleton.getInstance().entries.get(stationName).parameterMeasurements;
        }
        catch (NullPointerException e)
        {
            this.stationName=null;
            return;
        }

        try
        {
            for (Map.Entry<String, Map<String, Double>> measurement : measurements.entrySet())
                if (measurement.getValue().get(dateAndTime) < minValue)
                {
                    parameter = measurement.getKey();
                    minValue = measurement.getValue().get(dateAndTime);
                }

            this.parameter = parameter;
            this.minValue = minValue;
        }
        catch (NullPointerException e)
        {
            this.parameter = null;
        }
    }

    /**
     * Prepares output computed by execute() method.
     * @return formatted results
     */
    public String getResult()
    {
        if (this.stationName==null)
            return "The chosen station was not found!";
        else if (this.parameter==null)
            return "Parameters' measurements for chosen date and time were not found!";

        return "For the station " + this.stationName + " at " + this.dateAndTime + " the parameter with the smallest value was: "
                + this.parameter + " (" + String.format("%.2f", this.minValue) + " ug/m^3)";
    }
}
