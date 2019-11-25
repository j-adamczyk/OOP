/**
 * Gets value of given parameter at given station and time, concrete command part of Command design pattern.
 */
public class CommandParameterValueAtTime implements ICommand
{
    private String dateAndTime;
    private String stationName;
    private String parameter;
    private Double paramValue;

    /**
     * Gets all information and fills all attribute fields' values.
     * @param stationName name of single station
     * @param parameter name of parameter (in code, e. g. CO2 or C6H6)
     * @param dateAndTime date and time in format YYYY-MM-DD HH:MM:SS
     */
    void execute(String stationName, String parameter, String dateAndTime)
    {
        this.dateAndTime = dateAndTime;
        this.stationName = stationName;
        this.parameter = parameter;


        try
        {
            this.paramValue = UtilSingleton.getInstance().entries.get(stationName).parameterMeasurements.get(parameter).get(dateAndTime);
        }
        catch (NullPointerException e)
        {
            this.paramValue = null;
        }
    }

    /**
     * Prepares output computed by execute() method.
     * @return formatted results
     */
    public String getResult()
    {
        if (this.paramValue==null)
            return "Provided station, parameter or date was not found!";

        String result;

        result = "Station: " + this.stationName + "\n";
        result = result + "Parameter: ";
        if (this.parameter.equals("C6H6"))
            result += "benzen\n";
        else
            result = result + this.parameter + "\n";

        result = result + "Date and time: " + this.dateAndTime + "\n";
        result = result + "Value: " + String.format("%.2f", this.paramValue);

        return result;
    }
}