package factories;

import model.Entry;

import java.util.*;

/**
 * @see IEntryFactory
 */
public class BookFactory implements IEntryFactory
{
    public Entry create()
    {
        Entry book = new Entry();
        book.setRecordType("BOOK");
        book.setRequiredAttributes(book.setRequired(getRequiredFields()));
        book.setOptionalAttributes(book.setOptional(getOptionalFields()));

        return book;
    }

    public List<String> getRequiredFields()
    {
        List<String> required = new ArrayList<>();

        Collections.addAll(required,
                "author",
                "editor",
                "title",
                "publisher",
                "year"
                );

        return required;
    }

    public List<String> getOptionalFields()
    {
        List<String> optional = new ArrayList<>();

        Collections.addAll(optional,
                "volume",
                "series",
                "address",
                "edition",
                "month",
                "note",
                "key"
        );

        return optional;
    }
}