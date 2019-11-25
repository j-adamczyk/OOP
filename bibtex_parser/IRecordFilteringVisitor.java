import java.util.*;

/**
 * Main part of Visitor design pattern. This is an interface for classes filtering parsed records with provided filter.
 * There is only one method required to be implemented, visitAndFilter, which visits given Entry source, filters it with filter
 * and returns results of it's visit (collection of filtered entries).
 */
public interface IRecordFilteringVisitor
{
    List<Entry> visitAndFilter (List<Entry> source, String filter);
}