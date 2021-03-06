package factories;

import model.Entry;

import java.util.*;

/**
 * @see IEntryFactory
 */
public class MiscFactory implements IEntryFactory
{
    public Entry create()
    {
        Entry misc = new Entry();
        misc.setRecordType("MISC");
        misc.setRequiredAttributes(misc.setRequired(getRequiredFields()));
        misc.setOptionalAttributes(misc.setOptional(getOptionalFields()));

        return misc;
    }

    public List<String> getRequiredFields()
    {
        List<String> required = new ArrayList<>();

        return required;
    }

    public List<String> getOptionalFields()
    {
        List<String> optional = new ArrayList<>();

        Collections.addAll(optional,
                "author",
                "title",
                "howpublished",
                "month",
                "year",
                "note",
                "key"
        );

        return optional;
    }
}