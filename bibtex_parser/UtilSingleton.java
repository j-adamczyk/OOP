import java.io.*;
import java.util.*;

/**
 * Implementation of Singleton design pattern. It is used mostly because otherwise elements of singleton would have to be in main class,
 * which would then not be Facade (while it should be).
 * It is used to hold globally unique instances of objects that must be kept by program (loaded text file, text after substitutions and
 * list of parsed records) and utility methods that don't belong to any other class (they would break Single-Responsibility Principle there).
 */
final class UtilSingleton
{
    private static final UtilSingleton instance = new UtilSingleton();

    String originalText;
    String substitutedText;
    List<Entry> parsedRecords;

    /**
     * Singleton is eagerly constructed, so constructor is never used, it's listed to make it private and make cloning it harder.
     */
    private UtilSingleton() {}

    /**
     * Normal mean of getting instance of singleton.
     * @return one and only existing instance of UtilSingleton
     */
    static UtilSingleton getInstance()
    {
        return instance;
    }

    /**
     * Loads file as String and saves it in the singleton.
     * @param filePath path to file, passed to program as an argument
     */
    void readFile(String filePath)
    {
        BufferedReader reader = null;
        StringBuilder builtText = new StringBuilder();

        try
        {
            reader = new BufferedReader(new FileReader(filePath));
            String line;
            //ls - new line character
            String ls = System.getProperty("line.separator");
            while((line = reader.readLine()) != null)
            {
                builtText.append(line);
                builtText.append(ls);
            }

            //Delete last new line (ls) character
            //builtText.deleteCharAt(builtText.length()-1);
        }
        catch(FileNotFoundException exc)
        {
            System.out.println("File not found!");
        }
        catch(IOException exc)
        {
            System.out.println("Error while reading a line!");
        }
        finally
        {
            if (reader != null)
                try
                {
                    reader.close();
                }
                catch(IOException exc)
                {
                    System.out.println("Error while closing reader!");
                }
        }

        originalText = builtText.toString();
        substitutedText = originalText;
    }
}