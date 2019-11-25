import java.util.*;
import java.util.regex.*;

/**
 * Main parsing class, which does most of program's work and uses other classes as helpers in parsing.
 * Parent class ParsingContext sets all strategies used for parsing (@STRING substitution strategy and single record parsing strategy).
 */
public class Parser extends ParsingContext
{
    public ParsingContext ParsingContext;

    /**
     * Simple constructor creating context for given parser instance, so there can be many parsers available with different set strategies.
     */
    Parser()
    {
        this.ParsingContext = new ParsingContext();
    }

    /**
     * Main parsing method, uses Collection Pipeline pattern to get parsed entries and save them in UtilSingleton.
     * Collection Pipeline is used, because methods implement Null Object pattern - instead of NULL value, empty object is passed further,
     * which prevents NullPointerException.
     */
    public void parse()
    {
        this.ParsingContext.getStringSubstituter().substitute();

        UtilSingleton.getInstance().parsedRecords = getEntries(getRecords());

        for (Entry entry : UtilSingleton.getInstance().parsedRecords)
            checkRequired(entry);
    }

    /**
     * Gets list of all records in text (after substitutions).
     * @return list of records as Strings
     */
    public List<String> getRecords()
    {
        List<String> records = new ArrayList<>();

        Pattern pattern = Pattern.compile("(@\\w*\\{[\\s\\S]*?\\})\\s*[\\r\\n]+");
        Matcher m = pattern.matcher(UtilSingleton.getInstance().substitutedText);
        while (m.find())
            records.add(m.group(1));

        return records;
    }

    /**
     * Converts list of records as Strings to list of records as Entry objects (with set keys, tags, values etc.)
     * @param records list of records as Strings
     * @return list of parsed records as Entry objects
     */
    public List<Entry> getEntries(List<String> records)
    {
        List<Entry> entries = new ArrayList<>();

        for (String record : records)
        {
            Entry parsedEntry = this.ParsingContext.getSingleRecordParser().parseSingleRecord(record);
            if (parsedEntry!=null)
                entries.add(parsedEntry);
        }

        for (int i=0; i<entries.size(); i++)
        {
            if (!entries.get(i).crossref.equals(""))
                for (int j=i+1; j<entries.size(); j++)
                    if (entries.get(j).key.equalsIgnoreCase(entries.get(i).crossref))
                    {
                        for (Map.Entry<String,String> iter : entries.get(i).requiredAttributes.entrySet())
                            if (iter.getValue().equals("") && entries.get(j).requiredAttributes.containsKey(iter.getKey()) && !entries.get(j).requiredAttributes.get(iter.getKey()).equals(""))
                                entries.get(i).requiredAttributes.put(iter.getKey(),entries.get(j).requiredAttributes.get(iter.getKey()));

                        for (Map.Entry<String,String> iter : entries.get(i).optionalAttributes.entrySet())
                            if (iter.getValue().equals("") && entries.get(j).optionalAttributes.containsKey(iter.getKey()) && !entries.get(j).optionalAttributes.get(iter.getKey()).equals(""))
                                entries.get(i).optionalAttributes.put(iter.getKey(),entries.get(j).optionalAttributes.get(iter.getKey()));
                    }
        }

        return entries;
    }

    /**
     * Checks if all fields required for given entry type are filled. If not, it throws an exception and prints error message.
     * @param entry Entry object to checked
     */
    public void checkRequired(Entry entry)
    {
        if (entry.requiredAttributes.containsKey("author"))
        {
            try
            {
                if (entry.requiredAttributes.get("author").equals(""))
                    throw new IllegalArgumentException();
            }
            catch (IllegalArgumentException exc)
            {
                System.out.println("Error while reading record with key: "
                        + entry.key + ", required argument author had no value!");
            }
        }
        else if (entry.requiredAttributes.containsKey("editor"))
        {
            try
            {
                if (entry.requiredAttributes.get("editor").equals(""))
                    throw new IllegalArgumentException();
            }
            catch (IllegalArgumentException exc)
            {
                System.out.println("Error while reading record with key: "
                        + entry.key + ", required argument editor had no value!");
            }
        }

        for(Map.Entry<String,String> tag : entry.requiredAttributes.entrySet())
        {
            try
            {
                if (!tag.getKey().equals("author") && !tag.getKey().equals("editor") && tag.getValue().equals(""))
                    throw new IllegalArgumentException();
            }
            catch (IllegalArgumentException exc)
            {
                System.out.println("Error while reading record with key: "
                        + entry.key + ", required argument "
                        + tag.getKey() + " had no value!");
            }
        }
    }
}