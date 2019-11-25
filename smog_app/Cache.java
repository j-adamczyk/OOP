import java.util.List;

/**
 * Represents cache created, saved and loaded by program. Timestamp is in format YYYY-MM-DD HH:MM:SS, usedAPI is name of APIStrategy class used
 * for getting information for cache and cacheEntries is the actual cache information (all info from API).
 */
public class Cache
{
    String timestamp;
    String usedAPI;
    List<Entry> cacheEntries;
}