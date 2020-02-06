package factories;

import model.Entry;

import java.util.*;

/**
 * @see IEntryFactory
 */
public class ManualFactory implements IEntryFactory
{
    public Entry create()
    {
        Entry manual = new Entry();
        manual.setRecordType("MANUAL");
        manual.setRequiredAttributes(manual.setRequired(getRequiredFields()));
        manual.setOptionalAttributes(manual.setOptional(getOptionalFields()));

        return manual;
    }

    public List<String> getRequiredFields()
    {
        List<String> required = new ArrayList<>();

        Collections.addAll(required,
                "title"
        );

        return required;
    }

    public List<String> getOptionalFields()
    {
        List<String> optional = new ArrayList<>();

        Collections.addAll(optional,
                "author",
                "organization",
                "address",
                "edition",
                "month",
                "year",
                "note",
                "key"
        );

        return optional;
    }
}