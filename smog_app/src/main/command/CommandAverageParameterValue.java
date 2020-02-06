package command;

import model.UtilSingleton;

import java.text.*;
import java.util.*;

/**
 * Gets average parameter value for given time period, concrete command part of Command design pattern.
 */
public class CommandAverageParameterValue implements ICommand
{
    private String startTime;
    private String endTime;
    private String stationName;
    private String parameter;
    private Double avgValue;

    private String error;

    /**
     * Gets all information and fills all attribute fields' values.
     * @param stationName name of single station
     * @param parameter name of parameter (in code, e. g. CO2 or C6H6)
     * @param startTime start date and time in format YYYY-MM-DD HH:MM:SS
     * @param endTime end date and time in format YYYY-MM-DD HH:MM:SS
     * @throws ParseException if provided dates were in wrong format
     * @throws NullPointerException if provided arguments were null's or empty Strings
     */
    public void execute (String stationName, String parameter, String startTime, String endTime) throws ParseException, NullPointerException
    {
        this.startTime = startTime;
        this.endTime = endTime;
        this.stationName = stationName;
        this.parameter = parameter;

        this.error = null;

        Date startDate = null;
        Date endDate = null;

        try
        {
            startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime);
            endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime);
        }
        catch (ParseException e)
        {
            System.out.println("Parse error while parsing provided start and end dates!");
            throw new ParseException("",0);
        }
        catch (NullPointerException e)
        {
            System.out.println("NullPointer error while parsing provided start and end dates!");
            throw new NullPointerException();
        }

        Double sum = 0.0;
        Double count = 0.0;

        Map<String,Double> measurements = UtilSingleton.getInstance().getEntries().
                get(stationName).getParameterMeasurements().get(parameter);

        if (measurements==null)
        {
            System.out.println("Chosen station does not measure chosen parameter!");
            throw new NullPointerException();
        }

        for (Map.Entry<String, Double> measurement : measurements.entrySet())
        {
            Date measureDate = null;

            try { measureDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(measurement.getKey()); }
            catch (ParseException e) { System.out.println("Error while parsing measurement date!"); System.exit(1); }
            catch (NullPointerException e) { System.out.println("NullPointer error while parsing measurement date!"); System.exit(1); }

            if (measureDate.compareTo(startDate)>=0 && measureDate.compareTo(endDate)<=0)
            {
                sum += measurement.getValue();
                count += 1.0;
            }
        }

        this.avgValue = sum/count;
    }

    /**
     * Prepares output computed by execute() method.
     * @return formatted results
     */
    public String getResult()
    {
        if (error==null)
        {
            return "Average value of " + this.parameter + " from " + this.startTime + " to " + this.endTime +
                    " at the station " + this.stationName + " was: " + String.format("%.2f", this.avgValue) + " ug/m^3";
        }
        else return "";
    }
}