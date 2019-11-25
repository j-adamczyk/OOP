import java.util.*;

/**
 * Implementation of Facade design pattern. This is main class of the program and regular user will only interact with it.
 * All things that may have been here (global variables, utility methods not belonging logically to any class etc.) were moved to UtilSingleton
 * to spare user technical details (and occasion to mess up the program).
 */
public class Facade
{
    /**
     * Main method of the program, calling other methods to parse records in provided file, filter them according to passed arguments and display results.
     * @param args - array of 3 elements: file path, authors to filter and publication types to filter
     */
    public static void main(String[] args)
    {
        if (args.length!=3 || args[0].equals(""))
        {
            System.out.println("Arguments provided to the program were not correct! There should be:\n" +
                    "1. BibTeX file path\n" +
                    "2. List of authors' surnames (publications must have ALL of them as authors or editors to be printed)\n" +
                    "3. List of publication types (publications of ANY type will be printed)\n" +
                    "Argument 1 must ALWAYS be provided, while the arguments 2. and 3. MAY be empty strings (but empty string \"\" MUST be passed as an argument)."
            );
            return;
        }

        UtilSingleton.getInstance().readFile(args[0]);

        Parser p = new Parser();
        p.parse();

        List<Entry> result = UtilSingleton.getInstance().parsedRecords;

        if (!args[1].equals(""))
        {
            IRecordFilteringVisitor authorsFilterVisitor = new AuthorsFilterVisitor();
            result = authorsFilterVisitor.visitAndFilter(result,args[1]);
        }

        if(!args[2].equals(""))
        {
            IRecordFilteringVisitor publicationFilterVisitor = new PublicationFilterVisitor();
            result = publicationFilterVisitor.visitAndFilter(result,args[2]);
        }

        IOutputBuilder o = new DefaultOutputBuilder();
        System.out.println(o.buildOutput(result,"*"));
    }
}