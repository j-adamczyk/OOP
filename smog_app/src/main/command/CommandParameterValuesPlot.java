package command;

import model.UtilSingleton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Gets a plot of given parameter values for given stations and time period, concrete command part of Command design pattern.
 */
public class CommandParameterValuesPlot implements ICommand
{
    private String parameter;
    private List<stationParamValue> measurements;
    private String wrongHours;

    /**
     * Gets all information and fills all attribute fields' values.
     * @param stationsString list of stations' names
     * @param parameter name of parameter (in code, e. g. CO2 or C6H6)
     * @param startTime start date and time in format YYYY-MM-DD HH:MM:SS
     * @param endTime end date and time in format YYYY-MM-DD HH:MM:SS
     * @throws ParseException if provided dates were in wrong format
     * @throws NullPointerException if provided arguments were null's or empty Strings
     */
    public void execute (String stationsString, String parameter, String startTime, String endTime) throws ParseException, NullPointerException
    {
        this.wrongHours = checkDates(startTime,endTime);
        if (this.wrongHours != null)
            return;

        List<String> stations = UtilSingleton.getInstance().parseStations(stationsString);
        this.parameter = parameter;

        this.measurements = new ArrayList<>();

        for (String stationName : stations)
        {
            Map<String,Double> tmp;

            try { tmp = UtilSingleton.getInstance().getEntries().
                    get(stationName).getParameterMeasurements().get(parameter); }
            catch (NullPointerException e) { continue; }

            if (tmp == null)
                continue;

            for (Map.Entry<String,Double> measurement : tmp.entrySet())
            {
                Date startDate = null;
                Date endDate = null;
                Date measureDate = null;

                try { startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime); }
                catch (ParseException e)
                {
                    System.out.println("Parse error while parsing provided start date!");
                    throw new ParseException("",0);
                }
                catch (NullPointerException e)
                {
                    System.out.println("NullPointer error while parsing provided start date!");
                    throw new NullPointerException();
                }

                try { endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime); }
                catch (ParseException e)
                {
                    System.out.println("Parse error while parsing provided end date!");
                    throw new ParseException("",0);
                }
                catch (NullPointerException e)
                {
                    System.out.println("NullPointer error while parsing provided end date!");
                    throw new NullPointerException();
                }

                try { measureDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(measurement.getKey()); }
                catch (ParseException e)
                {
                    System.out.println("Parse error while parsing provided measure date!");
                    throw new ParseException("",0);
                }
                catch (NullPointerException e)
                {
                    System.out.println("NullPointer error while parsing provided measure date!");
                    throw new NullPointerException();
                }

                if (measureDate.compareTo(startDate)>=0 && measureDate.compareTo(endDate)<=0)
                {
                    stationParamValue stVal = new stationParamValue();
                    stVal.stationName = stationName;
                    stVal.time = measurement.getKey();
                    stVal.value = measurement.getValue();

                    if (stVal.value != null)
                        this.measurements.add(stVal);
                }
            }
        }

        if (this.measurements.isEmpty())
            this.measurements = null;
    }

    /**
     * Exists to sort parameter measurements by the time of measurement (to present them in right time order).
     */
    public class stationParamValue implements Comparator
    {
        String stationName;
        String time;
        Double value;

        @Override
        public int compare(Object o1, Object o2)
        {
            stationParamValue stVal1 = (CommandParameterValuesPlot.stationParamValue) o1;
            stationParamValue stVal2 = (CommandParameterValuesPlot.stationParamValue) o2;

            Date date1 = new Date();
            Date date2 = new Date();

            try { date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(stVal1.time); }
            catch (Exception e) { System.out.println("Error while parsing dates to compare, dates may not be sorted correctly!"); }

            try { date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(stVal2.time); }
            catch (Exception e) { System.out.println("Error while parsing dates to compare, dates may not be sorted correctly!"); }

            return date1.compareTo(date2);
        }
    }

    /**
     * Utility method for checking if two dates are proper order.
     * @param startDate earlier hour
     * @param endDate later hour
     * @return null if there was no error, "TRUE" if there was an error (i. e. dates were not in correct order)
     */
    private String checkDates (String startDate, String endDate)
    {
        String result = null;

        stationParamValue comparator = new stationParamValue();
        Date date1 = new Date();
        Date date2 = new Date();

        try { date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startDate); }
        catch (Exception e) { System.out.println("Error while parsing dates to compare, dates may not be sorted correctly!"); }

        try { date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endDate); }
        catch (Exception e) { System.out.println("Error while parsing dates to compare, dates may not be sorted correctly!"); }

        if (date1.compareTo(date2)>0)
            result = "TRUE";

        return result;
    }

    /**
     * Prepares output computed by execute() method.
     * @return formatted results
     */
    public String getResult ()
    {
        if (this.wrongHours!=null)
            return "Start date was after end date!";

        if (this.measurements==null)
            return "Measurements for provided arguments are empty! Check station, parameter and hours again!";

        String header = "Measurements of " + this.parameter + ":\n";
        String result = "";

        int maxStationNameLength = 0;
        Double maxParamValue = 0.0;

        this.measurements.sort(new stationParamValue());

        for (stationParamValue measurement : this.measurements)
        {
            if (measurement.stationName.length() > maxStationNameLength)
                maxStationNameLength = measurement.stationName.length();

            if (measurement.value > maxParamValue)
                maxParamValue = measurement.value;
        }

        for (stationParamValue measurement : this.measurements)
        {
            String line = measurement.time + " ";
            line = line + "(" + measurement.stationName + ")" + " ";

            while (line.length() < measurement.time.length() + maxStationNameLength + 5)
                line += " ";

            int signNumber = normalizeNumber(measurement.value);

            for (int i=1; i<= signNumber; i++)
                line += "#";

            line = line + " " + String.format("%.2f", measurement.value) + "\n";

            result += line;
        }

        if (result.equals(""))
            result = "No measurements available for given stations!";
        else
            result = header + result;

        return result;
    }

    private int normalizeNumber(Double number)
    {
        Double tmp = number;

        if (tmp < 1)
            while (tmp < 10)
                tmp *= 10;

        if (tmp > 100)
            while (tmp > 100)
                tmp /= 10;

        tmp /= 2;

        return (int) Math.round(tmp);
    }
}
