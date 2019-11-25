/**
 * Gets current air index for given station, concrete command part of Command design pattern.
 */
public class CommandCurrentAirIndex implements ICommand
{
    private AirIndex currentAirIndex;
    private String stationName;

    /**
     * Gets all information and fills all attribute fields' values.
     * @param stationName name of single station
     */
    public void execute(String stationName)
    {
        this.stationName = stationName;
        this.currentAirIndex = UtilSingleton.getInstance().entries.get(stationName).airIndex;
    }

    /**
     * Prepares output computed by execute() method.
     * @return formatted results
     */
    public String getResult()
    {
        String result;
        result = "Current air quality for station " + this.stationName + " is:\n";
        result = result + "\nOverall air quality: " + this.currentAirIndex.stIndexLevel + "\n";
        for (int i=1; i<=36+this.stationName.length(); i++) result += "-";
        result += "\n";
        result = result + "SO2 level: " + this.currentAirIndex.so2IndexLevel + "\n";
        result = result + "CO level: " + this.currentAirIndex.coIndexLevel + "\n";
        result = result + "O3 level: " + this.currentAirIndex.o3IndexLevel + "\n";
        result = result + "Benzene level: " + this.currentAirIndex.c6h6IndexLevel + "\n";
        result = result + "PM10 level: " + this.currentAirIndex.pm10IndexLevel + "\n";
        result = result + "PM25 level: " + this.currentAirIndex.pm25IndexLevel + "\n";
        result = result + "NO2 level: " + this.currentAirIndex.no2IndexLevel + "\n";

        return result;
    }
}
