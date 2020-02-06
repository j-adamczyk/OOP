package parser;

import io.UtilSingleton;

import java.util.*;
import java.util.regex.*;

/**
 * Simple, default class performing substitutions defined in @STRING records - if no other is chosen, this one is used.
 */
public class DefaultStringSubstituter implements IStringSubstituterStrategy
{
    /**
     * Performs all substitutions defined in @STRING records and saves result as SubstitutedText in io.UtilSingleton.
     * Uses other methods in this class, which are this class's utilities.
     */
    public void substitute()
    {
        List<String> records = getRecords(UtilSingleton.getInstance().getOriginalText());
        Map<String,String> substitutions = getSubstitutions(records);

        for (Map.Entry<String,String> iter : substitutions.entrySet())
        {
            UtilSingleton.getInstance().setSubstitutedText(UtilSingleton.getInstance().getSubstitutedText().replace(iter.getKey(),iter.getValue()));

            Pattern pattern = Pattern.compile("(\".*?\")");
            Matcher mat = pattern.matcher(UtilSingleton.getInstance().getSubstitutedText());
            while (mat.find())
            {
                String tmp = mat.group(1).replace(iter.getValue(),iter.getKey());
                UtilSingleton.getInstance().setSubstitutedText(UtilSingleton.getInstance().getSubstitutedText().replace(mat.group(1),tmp));
            }
        }
    }

    /**
     * Cuts single substitution records from original text.
     * @return list of @STRING records as Strings
     */
    public List<String> getRecords(String records)
    {
        List<String> result = new ArrayList<>();

        Matcher m = Pattern.compile("(@STRING\\s*\\{\\s*.*\\s*\\})", Pattern.CASE_INSENSITIVE).matcher(records);
        while (m.find())
            result.add(m.group());

        return result;
    }

    /**
     * Gets collections of substitutions as map representing pairs (toBeSubstituted,substitution).
     * @param stringRecords list of @STRING records
     * @return collection representing pairs for substitution
     */
    public Map<String, String> getSubstitutions(List<String> stringRecords)
    {
        Map<String,String> substitutions = new LinkedHashMap<>();

        for (String record : stringRecords)
        {
            List<String> pair = new ArrayList<>();

            Pattern pattern = Pattern.compile("\\{\\s*(\\S*)\\s*=\\s*\"(.*)\".*\\s*");
            Matcher mat = pattern.matcher(record);
            while (mat.find())
            {
                pair.add(mat.group(1));
                pair.add(mat.group(2));
            }

            String key = pair.get(0);
            String value = pair.get(1);

            substitutions.put(key,value);
        }

        return substitutions;
    }
}