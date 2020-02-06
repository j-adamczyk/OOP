package factories;

import model.Entry;

import java.util.*;

/**
 * @see IEntryFactory
 */
public class UnpublishedFactory implements IEntryFactory
{
    public Entry create()
    {
        Entry unpublished = new Entry();
        unpublished.setRecordType("UNPUBLISHED");
        unpublished.setRequiredAttributes(unpublished.setRequired(getRequiredFields()));
        unpublished.setOptionalAttributes(unpublished.setOptional(getOptionalFields()));

        return unpublished;
    }

    public List<String> getRequiredFields()
    {
        List<String> required = new ArrayList<>();

        Collections.addAll(required,
                "author",
                "title",
                "note"
        );

        return required;
    }

    public List<String> getOptionalFields()
    {
        List<String> optional = new ArrayList<>();

        Collections.addAll(optional,
                "month",
                "year",
                "key"
        );

        return optional;
    }
}