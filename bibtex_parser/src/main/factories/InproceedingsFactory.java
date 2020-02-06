package factories;

import model.Entry;

import java.util.*;

/**
 * @see IEntryFactory
 */
public class InproceedingsFactory implements IEntryFactory
{
    public Entry create()
    {
        Entry inproceedings = new Entry();
        inproceedings.setRecordType("INPROCEEDINGS");
        inproceedings.setRequiredAttributes(inproceedings.setRequired(getRequiredFields()));
        inproceedings.setOptionalAttributes(inproceedings.setOptional(getOptionalFields()));

        return inproceedings;
    }

    public List<String> getRequiredFields()
    {
        List<String> required = new ArrayList<>();

        Collections.addAll(required,
                "author",
                "title",
                "booktitle",
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
                "pages",
                "address",
                "month",
                "organization",
                "publisher",
                "note",
                "key"
        );

        return optional;
    }
}