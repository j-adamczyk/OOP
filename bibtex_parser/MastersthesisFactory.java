import java.util.*;

/**
 * @see IEntryFactory
 */
public class MastersthesisFactory implements IEntryFactory
{
    public Entry create()
    {
        Entry masterthesis = new Entry();
        masterthesis.recordType = "MASTERSTHESIS";
        masterthesis.requiredAttributes = masterthesis.setRequired(getRequiredFields());
        masterthesis.optionalAttributes = masterthesis.setOptional(getOptionalFields());

        return masterthesis;
    }

    public List<String> getRequiredFields()
    {
        List<String> required = new ArrayList<>();

        Collections.addAll(required,
                "author",
                "title",
                "school",
                "year"
        );

        return required;
    }

    public List<String> getOptionalFields()
    {
        List<String> optional = new ArrayList<>();

        Collections.addAll(optional,
                "type",
                "address",
                "month",
                "note",
                "key"
        );

        return optional;
    }
}