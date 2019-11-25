import java.util.*;

/**
 * @see IEntryFactory
 */
public class InbookFactory implements IEntryFactory
{
    public Entry create()
    {
        Entry inbook = new Entry();
        inbook.recordType = "INBOOK";
        inbook.requiredAttributes = inbook.setRequired(getRequiredFields());
        inbook.optionalAttributes = inbook.setOptional(getOptionalFields());

        return inbook;
    }

    public List<String> getRequiredFields()
    {
        List<String> required = new ArrayList<>();

        Collections.addAll(required,
                "author",
                "editor",
                "title",
                "chapter",
                "pages",
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
                "number",
                "series",
                "type",
                "address",
                "edition",
                "month",
                "note",
                "key"
        );

        return optional;
    }
}