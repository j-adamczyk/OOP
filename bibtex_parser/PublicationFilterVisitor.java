import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Filtering visitor that finds only those records that are of any type provided in the filter.
 */
class PublicationFilterVisitor implements IRecordFilteringVisitor
{
    /**
     * Visit method that gets a list of entries filtered by provided record types list.
     * @param filter list of types of records to get
     * @return list of filtered entries
     */
    public List<Entry> visitAndFilter(List<Entry> source, String filter)
    {
        List<Entry> filteredRecords = new ArrayList<>();
        List<String> recordTypes = new ArrayList<>();

        Pattern pattern = Pattern.compile("(\\b\\w*\\b)");
        Matcher mat = pattern.matcher(filter);
        while(mat.find())
            recordTypes.add(mat.group(1).toUpperCase());

        for(Entry entry : source)
        {
            for(String recordType : recordTypes)
                if(recordType.equals(entry.recordType))
                {
                    filteredRecords.add(entry);
                    break;
                }
        }

        return filteredRecords;
    }
}
