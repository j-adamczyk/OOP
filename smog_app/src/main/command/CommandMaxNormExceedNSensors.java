package command;

import model.UtilSingleton;

import java.util.*;

/**
 * Gets at most N parameters from sensors from given station and time that exceed their norms, concrete command part of Command design pattern.
 */
public class CommandMaxNormExceedNSensors implements ICommand
{
    private String station;
    private String dateAndTime;
    private Map<String,Double> maxExceedMeasurements;
    private int sensorNumber;

    /**
     * Gets all information and fills all attribute fields' values.
     * @param stationName name of single station
     * @param dateAndTime date and time in format YYYY-MM-DD HH:MM:SS
     * @param sensorNumber maximum number of sensors (parameters) to get
     */
    public void execute (String stationName, String dateAndTime, int sensorNumber)
    {
        this.station = stationName;
        this.dateAndTime = dateAndTime;
        this.maxExceedMeasurements = new HashMap<>();
        this.sensorNumber = sensorNumber;

        Map<String,Double> norms = new HashMap<>();
        norms.put("C6H6",0.00057);
        norms.put("NO2",200.0);
        norms.put("SO2",350.0);
        norms.put("CO",1250.0);
        norms.put("PM10",2.08333);
        norms.put("PM2.5",0.02853);
        norms.put("O3",15.0);

        for (Map.Entry<String,Map<String,Double>> measurements :
                UtilSingleton.getInstance().getEntries().get(station).getParameterMeasurements().entrySet())
        {
            Double paramValue = 0.0;

            if (measurements.getValue().containsKey(dateAndTime))
                paramValue = measurements.getValue().get(dateAndTime);
            else
                continue;

            if (paramValue == null)
                continue;

            Double paramExceedValue = Math.abs(paramValue - norms.get(measurements.getKey()));

            if (this.maxExceedMeasurements.containsKey(measurements.getKey()))
            {
                if (paramExceedValue > this.maxExceedMeasurements.get(measurements.getKey()))
                    this.maxExceedMeasurements.put(measurements.getKey(), paramExceedValue);
            }
            else if (paramExceedValue > norms.get(measurements.getKey()))
                this.maxExceedMeasurements.put(measurements.getKey(), paramExceedValue);
        }
    }

    /**
     * Exists to sort measurements by their exceed value and take up to N parameters with largest exceed value.
     */
    private class Measurement implements Comparator
    {
        String parameter;
        Double exceedValue;

        @Override
        public int compare(Object o1, Object o2)
        {
            Measurement m1 = (Measurement) o1;
            Measurement m2 = (Measurement) o2;
            if (m1.exceedValue - m2.exceedValue > 0)
                return 1;
            else if (m1.exceedValue - m2.exceedValue < 0)
                return -1;
            else
                return 0;
        }
    }

    /**
     * Prepares output computed by execute() method.
     * @return formatted results
     */
    public String getResult()
    {
        if (this.maxExceedMeasurements.entrySet().isEmpty())
            return "At the station " + this.station + " at the time " + this.dateAndTime + " there were no parameters exceeding their norms!";

        Integer sensorNumber = Math.min(this.sensorNumber,this.maxExceedMeasurements.size());

        String result = "At the station " + this.station + " at the time " + this.dateAndTime + " " + sensorNumber +
                        " parameters exceeding their norms the most were (with exceed values): \n";

        List<Measurement> measurements = new ArrayList<>();

        for (Map.Entry<String,Double> measurement : this.maxExceedMeasurements.entrySet())
        {
            Measurement tmp = new Measurement();
            tmp.parameter = measurement.getKey();
            tmp.exceedValue = measurement.getValue();
            measurements.add(tmp);
        }

        Measurement Comparator = new Measurement();
        measurements.sort(Comparator);

        int index = 0;

        for (Measurement measurement : measurements)
        {
            if (index >= measurements.size() - this.sensorNumber)
                result = result + measurement.parameter + ", exceed value: " + String.format("%.2f", measurement.exceedValue) + " ug/m^3\n";
        }

        return result;
    }
}
