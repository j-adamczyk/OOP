package model;

import command.*;
import gios.CacheService;

import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of Singleton design pattern. It is used mostly because otherwise elements of singleton would have to be in main class,
 * which would then not be Facade (while it should be).
 * It is used to hold globally unique instances of objects that must be kept by program (loaded map of entries, cache service)
 * and utility methods that don't belong to any other class (they would break Single-Responsibility Principle there).
 */
public class UtilSingleton
{
    private static final UtilSingleton instance = new UtilSingleton();

    private CacheService cacheService = new CacheService();
    private Map<String,Entry> entries;

    /**
     * Singleton is eagerly constructed, so constructor is never used, it's listed to make it private and make cloning it harder.
     */
    private UtilSingleton(){}

    /**
     * Normal mean of getting instance of singleton.
     * @return one and only existing instance of model.UtilSingleton
     */
    public static UtilSingleton getInstance()
    {
        return instance;
    }

    /**
     * Checks if arguments provided to the program are correct, should be used as soon as possible.
     * @param args program arguments from main
     * @throws IllegalArgumentException if arguments provided were wrong, it should not be caught
     */
    public void checkArguments(String[] args) throws IllegalArgumentException
    {
        if (args.length <= 1)
            printErrorMessage();

        String command = "command" + args[0].substring(2).toLowerCase();

        switch (command)
        {
            case "commandaverageparametervalue" :
                if (args.length!=5)
                    printErrorMessage();
                break;
            case "commandcurrentairindex" :
                if (args.length!=2)
                    printErrorMessage();
                break;
            case "commandmaxnormexceednsensors" :
                if (args.length!=4)
                    printErrorMessage();
                break;
            case "commandminandmaxparametervalueandplace" :
                if (args.length!=2)
                    printErrorMessage();
                break;
            case "commandparameterminvalueattime" :
                if (args.length!=3)
                    printErrorMessage();
                break;
            case "commandparametervalueattime" :
                if (args.length!=4)
                    printErrorMessage();
                break;
            case "commandparametervaluesplot" :
                if (args.length!=5)
                    printErrorMessage();
                break;
            case "commandparameterwithlargestchange" :
                if (args.length!=3)
                    printErrorMessage();
                break;
            default :
                printErrorMessage();
                break;
        }
    }

    /**
     * Parses arguments provided to program, arguments should be checked with checkArguments() before calling this method.
     * It calls appropriate methods and prints results.
     * @param args program arguments from main
     * @throws ParseException if provided dates were in wrong format
     * @throws NullPointerException if provided arguments were null's or empty Strings
     */
    public void parseAndExecuteArguments (String[] args) throws ParseException, NullPointerException
    {
        String command = "command" + args[0].substring(2).toLowerCase();

        switch (command)
        {
            case "commandaverageparametervalue" :
                CommandAverageParameterValue commandAverageParameterValue = new CommandAverageParameterValue();
                commandAverageParameterValue.execute(args[1],args[2],args[3],args[4]);
                System.out.println(commandAverageParameterValue.getResult());
                break;
            case "commandcurrentairindex" :
                CommandCurrentAirIndex commandCurrentAirIndex = new CommandCurrentAirIndex();
                commandCurrentAirIndex.execute(args[1]);
                System.out.println(commandCurrentAirIndex.getResult());
                break;
            case "commandmaxnormexceednsensors" :
                CommandMaxNormExceedNSensors commandMaxNormExceedNSensors = new CommandMaxNormExceedNSensors();
                commandMaxNormExceedNSensors.execute(args[1],args[2],Integer.parseInt(args[3]));
                System.out.println(commandMaxNormExceedNSensors.getResult());
                break;
            case "commandminandmaxparametervalueandplace" :
                CommandMinAndMaxParameterValueAndPlace commandMinAndMaxParameterValueAndPlace = new CommandMinAndMaxParameterValueAndPlace();
                commandMinAndMaxParameterValueAndPlace.execute(args[1]);
                System.out.println(commandMinAndMaxParameterValueAndPlace.getResult());
                break;
            case "commandparameterminvalueattime" :
                CommandParameterMinValueAtTime commandParameterMinValueAtTime = new CommandParameterMinValueAtTime();
                commandParameterMinValueAtTime.execute(args[1],args[2]);
                System.out.println(commandParameterMinValueAtTime.getResult());
                break;
            case "commandparametervalueattime" :
                CommandParameterValueAtTime commandParameterValueAtTime = new CommandParameterValueAtTime();
                commandParameterValueAtTime.execute(args[1],args[2],args[3]);
                System.out.println(commandParameterValueAtTime.getResult());
                break;
            case "commandparametervaluesplot" :
                CommandParameterValuesPlot commandParameterValuesPlot = new CommandParameterValuesPlot();
                commandParameterValuesPlot.execute(args[1],args[2],args[3], args[4]);
                System.out.println(commandParameterValuesPlot.getResult());
                break;
            case "commandparameterwithlargestchange" :
                CommandParameterWithLargestChange commandParameterWithLargestChange = new CommandParameterWithLargestChange();
                commandParameterWithLargestChange.execute(args[1],args[2]);
                System.out.println(commandParameterWithLargestChange.getResult());
                break;
            default :
                printErrorMessage();
                break;
        }
    }

    /**
     * Prints help message, used when arguments passed to the program were wrong.
     * @throws IllegalArgumentException to terminate program, should not be caught
     */
    private void printErrorMessage() throws IllegalArgumentException
    {
        System.out.println
            (
                "\nArguments provided to the program were not correct!\n" +
                "First argument should be command to be executed and further arguments should be arguments required by that command.\n" +
                "Stations' names in list should be divided with \"|\", \":\" or \";\" sign.\n" +
                "Parameters should be from list: C6H6, CO, NO2, O3, PM10, PM25, SO2\n" +
                "Time should be in format YYYY-MM-DD HH:MM:SS. Measurements are usually taken at full hour (MM:SS is usually 00:00).\n" +
                "Possible commands with required arguments: \n" +
                "--AverageParameterValue, calculates average parameter value for given time and station. Arguments: " +
                "stationName parameter startTime endTime\n" +
                "--CurrentAirIndex, prints current air index for given station (overall and for all parameters). Arguments: " +
                "stationName\n" +
                "--MaxNormExceedNSensors, gets N parameters that exceed their norms the most for given station. Arguments: " +
                "stationName dateAndTime N\n" +
                "--MinAndMaxParameterValueAndPlace, finds when and where given parameter had smallest and largest value. Arguments: " +
                "parameter\n" +
                "--ParameterMinValueAtTime, finds parameter with smallest value for given time and station. Arguments: " +
                "stationName dateAndTime\n" +
                "--ParameterValueAtTime, prints parameter value for given time and station. Arguments: " +
                "stationName parameter dateAndTime\n" +
                "--ParameterValuesPlot, plots histogram with parameter values for given stations. Arguments: " +
                "stationsList parameter startTime endTime\n" +
                "--ParameterWithLargestChange, finds parameter which changed the most since given time for given stations. Arguments: " +
                "stationsList startTime\n"
            );
        try{TimeUnit.MILLISECONDS.sleep(1);}catch(InterruptedException ignored) {}
        throw new IllegalArgumentException();
    }

    /**
     * Parses list of stations passed as single string, cuts them as separate. Stations can be divided by pipes ("|" sign),
     * colons (":" sign) or semicolons (";" sign).
     * @param stations stations as single String
     * @return list of stations as separate Strings
     */
    public List<String> parseStations (String stations)
    {
        try
        {
            stations = stations.replaceAll(";", "\\|");
            stations = stations.replaceAll(":", "\\|");
            String[] tmp = stations.split("\\|");

            List<String> result = new ArrayList<>();

            for (String station : tmp)
            {
                station = station.replaceAll("\\s{2,}", " ");
                if (station.startsWith(" "))
                    station = station.substring(1);

                if (station.endsWith(" "))
                    station = station.substring(0, station.length() - 1);

                result.add(station);
            }

            return result;
        }
        catch (NullPointerException e)
        {
            return new ArrayList<>();
        }
    }

    public CacheService getCacheService() {
        return cacheService;
    }

    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    public Map<String, Entry> getEntries() {
        return entries;
    }

    public void setEntries(Map<String, Entry> entries) {
        this.entries = entries;
    }
}