import java.util.*;

/**
 * Interface for strategies of performing @STRING substitutions.
 * Any class implementing this interface can be chosen as @STRING substituter.
 * Every strategy should be different from others. Default, simple version is provided.
 */
public interface IStringSubstituterStrategy
{
    void substitute ();
    List<String> getRecords (String records);
    Map<String,String> getSubstitutions (List<String> stringRecords);
}