package factories;

import model.Entry;

import java.util.*;

/**
 * @see IEntryFactory
 */
public class BookletFactory implements IEntryFactory
{
    public Entry create()
    {
        Entry booklet = new Entry();
        booklet.setRecordType("BOOKLET");
        booklet.setRequiredAttributes(booklet.setRequired(getRequiredFields()));
        booklet.setOptionalAttributes(booklet.setOptional(getOptionalFields()));

        return booklet;
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
                "howpublished",
                "address",
                "month",
                "year",
                "note",
                "key"
        );

        return optional;
    }
}