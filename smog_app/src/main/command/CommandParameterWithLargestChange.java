package command;

import model.UtilSingleton;

import java.text.*;
import java.util.*;

/**
 * Gets parameter that changed the most since given time at given stations, concrete command part of Command design pattern.
 */
public class CommandParameterWithLargestChange implements ICommand
{
    private String startDate;
    private String parameter;

    /**
     * Gets all information and fills all attribute fields' values.
     * @param stationsString list of stations' names
     * @param startTime start date and time in format YYYY-MM-DD HH:MM:SS
     * @throws ParseException if provided dates were in wrong format
     * @throws NullPointerException if provided arguments were null's or empty Strings
     */
    public void execute (String stationsString, String startTime) throws ParseException, NullPointerException
    {
        this.startDate = startTime;

        List<String> stations = UtilSingleton.getInstance().parseStations(stationsString);

        List<String> parameters = new ArrayList<>();
        parameters.add("SO2");
        parameters.add("NO2");
        parameters.add("CO");
        parameters.add("PM10");
        parameters.add("PM25");
        parameters.add("O3");
        parameters.add("C6H6");

        Map<String,Double> paramVarSums = new HashMap<>();
        for (String param : parameters)
            paramVarSums.put(param,0.0);

        for (String station : stations)
        {
            UtilSingleton.getInstance().getEntries().get(station);

            for (String param : parameters)
            {
                Map<String, Double> measurements;

                try { measurements = UtilSingleton.getInstance().getEntries().
                        get(station).getParameterMeasurements().get(param); }
                catch (NullPointerException e) { continue; }

                if (measurements == null)
                    continue;

                Double prev = 0.0;
                Double curr = 0.0;

                Double tmp = 0.0;

                for (Map.Entry<String, Double> measurement : measurements.entrySet())
                {
                    Date startDate = null;
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

                    try { measureDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(measurement.getKey()); }
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

                    if (measureDate.compareTo(startDate)>=0)
                    {
                        if (tmp.equals(0.0))
                            tmp = measurement.getValue();

                        curr = measurement.getValue();

                        if (curr==null)
                            break;

                        Double newValue = paramVarSums.get(param);
                        newValue += Math.abs(curr-prev);
                        paramVarSums.put(param,newValue);

                        prev = curr;
                    }
                }

                Double rightValue = paramVarSums.get(param);
                rightValue -= tmp;
                paramVarSums.put(param,rightValue);
            }
        }

        String result = "";
        Double maxValue = 0.0;

        for (Map.Entry<String, Double> param : paramVarSums.entrySet())
            if (param.getValue() > maxValue)
            {
                result = param.getKey();
                maxValue = param.getValue();
            }

        this.parameter = result;
    }

    /**
     * Prepares output computed by execute() method.
     * @return formatted results
     */
    public String getResult()
    {
        if (this.parameter.equals(""))
            return "Parameter was not measured at any of the provided stations since given date and time!";

        return "Largest change of parameter measurement values from " + this.startDate + " till now was for parameter " + this.parameter;
    }
}
