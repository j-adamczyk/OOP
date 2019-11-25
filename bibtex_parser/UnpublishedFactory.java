import java.util.*;

/**
 * @see IEntryFactory
 */
public class UnpublishedFactory implements IEntryFactory
{
    public Entry create()
    {
        Entry unpublished = new Entry();
        unpublished.recordType = "UNPUBLISHED";
        unpublished.requiredAttributes = unpublished.setRequired(getRequiredFields());
        unpublished.optionalAttributes = unpublished.setOptional(getOptionalFields());

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