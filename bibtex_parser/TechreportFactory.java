import java.util.*;

/**
 * @see IEntryFactory
 */
public class TechreportFactory implements IEntryFactory
{
    public Entry create()
    {
        Entry techreport = new Entry();
        techreport.recordType = "TECHREPORT";
        techreport.requiredAttributes = techreport.setRequired(getRequiredFields());
        techreport.optionalAttributes = techreport.setOptional(getOptionalFields());

        return techreport;
    }

    public List<String> getRequiredFields()
    {
        List<String> required = new ArrayList<>();

        Collections.addAll(required,
                "author",
                "title",
                "institution",
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