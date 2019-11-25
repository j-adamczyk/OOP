import java.util.*;

/**
 * @see IEntryFactory
 */
public class BookFactory implements IEntryFactory
{
    public Entry create()
    {
        Entry book = new Entry();
        book.recordType = "BOOK";
        book.requiredAttributes = book.setRequired(getRequiredFields());
        book.optionalAttributes = book.setOptional(getOptionalFields());

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