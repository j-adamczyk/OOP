package factories;

import model.Entry;

import java.util.*;

public class PhdthesisFactory implements IEntryFactory
{
    public Entry create()
    {
        Entry phdthesis = new Entry();
        phdthesis.setRecordType("PHDTHESIS");
        phdthesis.setRequiredAttributes(phdthesis.setRequired(getRequiredFields()));
        phdthesis.setOptionalAttributes(phdthesis.setOptional(getOptionalFields()));

        return phdthesis;
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