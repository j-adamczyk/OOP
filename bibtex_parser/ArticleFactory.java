import java.util.*;

/**
 * @see IEntryFactory
 */
public class ArticleFactory implements IEntryFactory
{
    public Entry create()
    {
        Entry article = new Entry();
        article.recordType = "ARTICLE";
        article.requiredAttributes = article.setRequired(getRequiredFields());
        article.optionalAttributes = article.setOptional(getOptionalFields());

        return article;
    }

    public List<String> getRequiredFields()
    {
        List<String> required = new ArrayList<>();

        Collections.addAll(required,
                "author",
                "title",
                "journal",
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
                "pages",
                "month",
                "note",
                "key"
        );

        return optional;
    }
}