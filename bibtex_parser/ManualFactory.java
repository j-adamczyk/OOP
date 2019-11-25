import java.util.*;

/**
 * @see IEntryFactory
 */
public class ManualFactory implements IEntryFactory
{
    public Entry create()
    {
        Entry manual = new Entry();
        manual.recordType = "MANUAL";
        manual.requiredAttributes = manual.setRequired(getRequiredFields());
        manual.optionalAttributes = manual.setOptional(getOptionalFields());

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