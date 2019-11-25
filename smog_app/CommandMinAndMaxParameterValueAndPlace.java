import java.util.Map;

/**
 * Gets parameters with smallest and largest value at given time (in the entire country), concrete command part of Command design pattern.
 */
public class CommandMinAndMaxParameterValueAndPlace implements ICommand
{
    private String parameter;
    private Double minValue;
    private String minDate;
    private String minStation;
    private Double maxValue;
    private String maxDate;
    private String maxStation;

    /**
     * Gets all information and fills all attribute fields' values.
     * @param parameter name of parameter (in code, e. g. CO2 or C6H6)
     */
    public void execute (String parameter)
    {
        this.parameter = parameter;

        this.minValue = 99999.9;
        this.minDate = "";
        this.minStation = "";
        this.maxValue = 0.0;
        this.maxDate = "";
        this.maxStation = "";

        for (Map.Entry<String,Entry> station : UtilSingleton.getInstance().entries.entrySet())
        {
            Map<String,Double> measurements;

            try { measurements = UtilSingleton.getInstance().entries.get(station.getKey()).parameterMeasurements.get(parameter); }
            catch (NullPointerException e) { continue; }

            if (measurements == null)
                continue;

            for (Map.Entry<String,Double> measurement : measurements.entrySet())
            {
                if (measurement.getValue() == null)
                    continue;

                if (measurement.getValue() < this.minValue)
                {
                    this.minValue = measurement.getValue();
                    this.minDate = measurement.getKey();
                    this.minStation = station.getKey();
                }

                if (measurement.getValue() > this.maxValue)
                {
                    this.maxValue = measurement.getValue();
                    this.maxDate = measurement.getKey();
                    this.maxStation = station.getKey();
                }
            }
        }

        if (this.minStation.equals("") && this.maxStation.equals(""))
        {
            System.out.println("The chosen parameter was not measured at any station!");
        }
    }

    /**
     * Prepares output computed by execute() method.
     * @return formatted results
     */
    public String getResult()
    {
        if (this.minStation.equals("") && this.maxStation.equals(""))
            return "The chosen parameter was not measured at any station!";
        else
            return "Parameter " + this.parameter + " had:\n" +
               "Smallest value: " + String.format("%.2f", this.minValue) + " ug/m^3 at " + this.minDate + ", station " + this.minStation + ",\n" +
               "Largest value: " + String.format("%.2f", this.maxValue) + " ug/m^3 at " + this.maxDate + ", station " + this.maxStation;
    }
}
