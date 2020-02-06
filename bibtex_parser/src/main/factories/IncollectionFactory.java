package factories;

import model.Entry;

import java.util.*;

/**
 * @see IEntryFactory
 */
public class IncollectionFactory implements IEntryFactory
{
    public Entry create()
    {
        Entry incollection = new Entry();
        incollection.setRecordType("INCOLLECTION");
        incollection.setRequiredAttributes(incollection.setRequired(getRequiredFields()));
        incollection.setOptionalAttributes(incollection.setOptional(getOptionalFields()));

        return incollection;
    }

    public List<String> getRequiredFields()
    {
        List<String> required = new ArrayList<>();

        Collections.addAll(required,
                "author",
                "title",
                "booktitle",
                "publisher",
                "year"
        );

        return required;
    }

    public List<String> getOptionalFields()
    {
        List<String> optional = new ArrayList<>();

        Collections.addAll(optional,
                "editor",
                "volume",
                "number",
                "series",
                "type",
                "chapter",
                "pages",
                "address",
                "edition",
                "month",
                "note",
                "key"
        );

        return optional;
    }
}