/**
 * Implementation of Facade design pattern. This is main class of the program and regular user will only interact with it.
 * All things that may have been here (global variables, utility methods not belonging logically to any class etc.) were moved to UtilSingleton
 * to spare user technical details (and occasion to mess up the program).
 */
public class Facade
{
    /**
     * Main method of the program, calling other methods to parse records in provided file, filter them according to passed arguments and display results.
     * @param args - array of elements: first one is always program command, others are arguments required by used command
     * @throws Exception various exceptions thrown by methods called in program, they print their own error messages, it should not be caught
     */
    public static void main(String args[]) throws Exception
    {
        UtilSingleton.getInstance().checkArguments(args);

        UtilSingleton.getInstance().cacheService.filePath = "C:\\Users\\jakub\\Desktop\\Obiektowe\\cache.json";
        UtilSingleton.getInstance().cacheService.checkCache();
        UtilSingleton.getInstance().cacheService.loadCache();

        UtilSingleton.getInstance().parseAndExecuteArguments(args);
    }
}