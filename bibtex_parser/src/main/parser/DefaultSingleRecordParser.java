package parser;

import factories.IEntryFactory;
import model.Entry;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

/**
 * Simple, default class parsing single record - if no other is chosen, this one is used.
 */
public class DefaultSingleRecordParser implements ISingleRecordParserStrategy
{
    /**
     * Parses single record, creating ready, filled model.Entry from String.
     * @param record single record to be parsed
     * @return model.Entry object representing parsed record
     */
    public Entry parseSingleRecord(String record)
    {
        Entry entry;

        String entryType = "";

        Pattern pattern = Pattern.compile("@(\\w*)\\s*\\{");
        Matcher mat = pattern.matcher(record);
        while (mat.find())
            entryType = mat.group(1);

        entryType = entryType.substring(0,1).toUpperCase() + entryType.substring(1).toLowerCase();

        entry = getEmptyEntry(entryType);
        if (entry==null) return null;

        entry.setKey(getKey(record));
        entry.setCrossref(getCrossref(record));
        entry = fillFields(entry,record);

        return entry;
    }

    /**
     * Gets empty model.Entry object of type passed as String. If no such type is defined (e. g. there is no Factory for such type) it returns null.
     * @param entryType type of entry to be created (determines Factory class used)
     * @return empty model.Entry of given type or null (if type is not defined)
     */
    public Entry getEmptyEntry(String entryType)
    {
        Entry entry = null;
        IEntryFactory factory = null;

        try
        {
            factory = (IEntryFactory) Class.forName(entryType+"Factory").newInstance();
            entry = factory.create();
        }
        catch (Exception ignored) {}

        return entry;
    }

    /**
     * Gets key of given record.
     * @param record from which key will be extracted
     * @return key of given record
     */
    public String getKey(String record)
    {
        String key = "";

        Pattern pattern = Pattern.compile("@\\w*\\s*\\{\\s*(.*),");
        Matcher mat = pattern.matcher(record);
        while (mat.find())
            key = mat.group(1);

        try
        {
            if (key.equals(""))
                throw new IllegalArgumentException();
        }
        catch(IllegalArgumentException e)
        {
            System.out.println("Error while reading record, it had no key!");
        }

        return key;
    }

    public String getCrossref (String record)
    {
        String crossref = "";

        Pattern pattern = Pattern.compile("\\s*crossref\\s*=\\s*\"(.*)\"");
        Matcher mat = pattern.matcher(record);
        while (mat.find())
            crossref = mat.group(1);

        return crossref;
    }

    /**
     * Gets attributes values.
     * @param entry model.Entry object which values will be filled
     * @param record String record which model.Entry represents
     * @return model.Entry will filled value fields
     */
    public Entry fillFields (Entry entry, String record)
    {
        List<String> fields = new ArrayList<>();

        Pattern pattern = Pattern.compile("\\s*(\\w*)\\s*=\\s*(.*)");
        Matcher mat = pattern.matcher(record);
        while (mat.find())
        {
            fields.add(mat.group(1));
            fields.add(mat.group(2));
        }

        for (int i=0; i<fields.size(); i+=2)
        {
            String field = fields.get(i);
            String value = fields.get(i+1);

            if (value.substring(value.length()-1).equals(","))
                value = value.substring(0, value.length()-1);

            boolean quotesBalanced = true;
            for(int j = 0; j<value.length(); j++)
            {
                if (value.charAt(j) == '"')
                    quotesBalanced = !quotesBalanced;

                if (value.charAt(j)=='#' && quotesBalanced)
                {
                    String tmp;
                    if (j!=value.length()-1 && j!=0)
                        tmp = value.substring(0,j) + "" + value.substring(j+1);
                    else if (j==value.length()-1)
                        tmp = value.substring(0,j);
                    else
                        tmp = value.substring(j+1);

                    value = tmp;
                }
            }

            value = value.replaceAll("\"\\s*\"","\"\"");
            value = value.replaceAll("\"","");
            value = value.replaceAll("\\s{2,}"," ");
            value = value.replaceAll(" ,",",");

            if(entry.getRequiredAttributes().get(field)!=null)
                entry.getRequiredAttributes().put(field,value);
            else if (entry.getOptionalAttributes().get(field)!=null)
                entry.getOptionalAttributes().put(field,value);
        }

        return entry;
    }
}