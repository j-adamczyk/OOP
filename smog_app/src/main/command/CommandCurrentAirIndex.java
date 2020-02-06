package command;

import model.AirIndex;
import model.UtilSingleton;

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
        this.currentAirIndex = UtilSingleton.getInstance().getEntries().get(stationName).getAirIndex();
    }

    /**
     * Prepares output computed by execute() method.
     * @return formatted results
     */
    public String getResult()
    {
        String result;
        result = "Current air quality for station " + this.stationName + " is:\n";
        result = result + "\nOverall air quality: " + this.currentAirIndex.getStIndexLevel() + "\n";
        for (int i=1; i<=36+this.stationName.length(); i++) result += "-";
        result += "\n";
        result = result + "SO2 level: " + this.currentAirIndex.getSo2IndexLevel() + "\n";
        result = result + "CO level: " + this.currentAirIndex.getCoIndexLevel() + "\n";
        result = result + "O3 level: " + this.currentAirIndex.getO3IndexLevel() + "\n";
        result = result + "Benzene level: " + this.currentAirIndex.getC6h6IndexLevel() + "\n";
        result = result + "PM10 level: " + this.currentAirIndex.getPm10IndexLevel() + "\n";
        result = result + "PM25 level: " + this.currentAirIndex.getPm25IndexLevel() + "\n";
        result = result + "NO2 level: " + this.currentAirIndex.getNo2IndexLevel() + "\n";

        return result;
    }
}
