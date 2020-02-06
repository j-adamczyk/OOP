package model;

import java.util.List;

/**
 * Represents cache created, saved and loaded by program. Timestamp is in format YYYY-MM-DD HH:MM:SS, usedAPI is name of APIStrategy class used
 * for getting information for cache and cacheEntries is the actual cache information (all info from API).
 */
public class Cache
{
    private String timestamp;
    private String usedAPI;
    private List<Entry> cacheEntries;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUsedAPI() {
        return usedAPI;
    }

    public void setUsedAPI(String usedAPI) {
        this.usedAPI = usedAPI;
    }

    public List<Entry> getCacheEntries() {
        return cacheEntries;
    }

    public void setCacheEntries(List<Entry> cacheEntries) {
        this.cacheEntries = cacheEntries;
    }
}