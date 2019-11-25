import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Filtering visitor that finds only those records that have all of the authors provided in the filter in the field "author".
 */
public class AuthorsFilterVisitor implements IRecordFilteringVisitor
{
    /**
     * Visit method that gets a list of entries filtered by provided authors list.
     * @param filter list of surnames of authors that have to be included in entry (either as authors or editors)
     * @return list of filtered entries
     */
    public List<Entry> visitAndFilter(List<Entry> source, String filter)
    {
        List<Entry> filteredRecords = new ArrayList<>();
        List<String> authors = getAuthorsSurnames(getAuthorsList(filter));

        for(Entry entry : source)
        {
            if(entry.requiredAttributes.containsKey("author"))
            {
                List<String> entryAuthors = getAuthorsSurnames(getAuthorsList(entry.requiredAttributes.get("author")));

                boolean add = true;
                for (String author : authors)
                {
                    if (!entryAuthors.contains(author))
                    {
                        add = false;
                        break;
                    }
                }

                if (add)
                    filteredRecords.add(entry);
            }
            else if (entry.optionalAttributes.containsKey("author"))
            {
                List<String> entryAuthors = getAuthorsSurnames(getAuthorsList(entry.optionalAttributes.get("author")));

                boolean add = true;
                for (String author : authors)
                    if(!entryAuthors.contains(author))
                    {
                        add=false;
                        break;
                    }

                if (add)
                    filteredRecords.add(entry);
            }
        }

        return filteredRecords;
    }

    /**
     * Cuts separate authors.
     * @param authors list of all authors as one String
     * @return list of separate authors
     */
    private List<String> getAuthorsList (String authors)
    {
        List<String> result = new ArrayList<>();

        String[] tmp = authors.split("and");

        for (String i : tmp)
            result.add(i);

        return result;
    }

    /**
     * Cuts surnames of authors (handling versions both with or without pipe |).
     * @param authors list of separate authors
     * @return list of authors' surnames
     */
    private List<String> getAuthorsSurnames (List<String> authors)
    {
        List<String> authorsSurnames = new ArrayList<>();

        for (String author : authors)
        {
            if (Pattern.compile("(^[a-zA-Z-]+$)").matcher(author).find())
            {
                Matcher mat = Pattern.compile("(^[a-zA-Z-]+$)").matcher(author);
                if (mat.find())
                    authorsSurnames.add(mat.group(1));
            }

            if (Pattern.compile("\\|").matcher(author).find())
            {
                Matcher mat = Pattern.compile("\\s*\\b(.*)\\b.*\\|").matcher(author);
                if (mat.find())
                    authorsSurnames.add(mat.group(1));
            }
            else
            {
                Matcher mat = Pattern.compile("\\s([\\S]*?)\\s*$").matcher(author);
                if (mat.find())
                    authorsSurnames.add(mat.group(1));
            }
        }

        return authorsSurnames;
    }
}
