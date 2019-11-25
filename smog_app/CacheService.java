import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.naming.CommunicationException;
import java.io.*;
import java.net.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * This class holds all utility method for managing cache: creating it from provided informations, checking it, updating and loading.
 * Nested classes are here for GSON to properly serialize and deserialize cache file.
 */
public class CacheService
{
    String filePath;

    /**
     * Creates cache from provided data.
     * @param entries list of data from API
     * @param usedAPI instance of class used to get data from API
     */
    void createCache(List<Entry> entries, IAPIStrategy usedAPI)
    {
        Cache cache = new Cache();

        cache.cacheEntries = entries;
        cache.usedAPI = usedAPI.getClass().getSimpleName();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime currentTime = LocalDateTime.now();

        cache.timestamp = dtf.format(currentTime);

        Gson gson = new GsonBuilder().serializeNulls().create();

        try
        {
            FileWriter fileWriter = new FileWriter(this.filePath);
            gson.toJson(cache, fileWriter);
            fileWriter.close();
        }
        catch (IOException e)
        {
            System.out.println("Error while saving cache file!");
        }
    }

    /**
     * Checks if cache exists or needs to be updated. Creates or updates cache if needed (updates from the same API that was originally
     * used to create cache).
     * @throws CommunicationException if there was a problem connecting to the Internet or API site
     */
    void checkCache() throws CommunicationException
    {
        Gson gson = new GsonBuilder().serializeNulls().create();

        File cacheFile = new File(this.filePath);
        if(!cacheFile.exists())
        {
            System.out.println("Cache doesn't exist, please wait while cache is being created.");
            IAPIStrategy api_gios = new API_GIOS();
            createCache(api_gios.getInfo(),api_gios);
            return;
        }

        Timestamp cacheTimestamp = new Timestamp();

        try
        {
            FileReader fileReader = new FileReader(this.filePath);
            cacheTimestamp = gson.fromJson(fileReader,Timestamp.class);
            fileReader.close();
        }
        catch (IOException e)
        {
            System.out.println("Error while reading cache file!");
        }

        if (cacheTimestamp==null)
        {
            System.out.println("Cache outdated, please wait while cache is being updated.");
            IAPIStrategy api_gios = new API_GIOS();
            createCache(api_gios.getInfo(),api_gios);
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime cacheTime = LocalDateTime.parse(cacheTimestamp.timestamp, formatter);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime currentTime = LocalDateTime.now();

        Duration duration = Duration.between(currentTime,cacheTime);
        long dateDiff = Math.abs(duration.toMinutes());

        if (dateDiff >= 60)
        {
            System.out.println("Cache outdated, please wait while cache is being updated.");
            updateCache();
        }
    }

    private class Timestamp
    {
        String timestamp;
    }

    /**
     * Loads cache from file. Throws exceptions and terminates program when cache can't be loaded for whatever reason.
     * @throws IOException if there was a problem finding or reading a cache file
     */
    void loadCache() throws IOException
    {
        try
        {
            Gson gson = new GsonBuilder().serializeNulls().create();
            FileReader fileReader = new FileReader(this.filePath);
            Cache cache = gson.fromJson(fileReader, Cache.class);
            fileReader.close();

            UtilSingleton.getInstance().entries = new HashMap<>();

            for (Entry entry : cache.cacheEntries)
                UtilSingleton.getInstance().entries.put(entry.stationName,entry);
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Error while loading cache data, cache file not found!");
            throw new FileNotFoundException();
        }
        catch (IOException e)
        {
            System.out.println("Error while reading cache file!");
            throw new IOException();
        }
    }

    /**
     * Updates cache if possible, uses the same API that was originally used to create it. If updating cache is not possible, it will
     * not be updated and program will use the latest cache available.
     * @throws CommunicationException if there was a problem connecting to the Internet or API site
     */
    void updateCache () throws CommunicationException
    {
        try
        {
            URL url = new URL("https://www.google.com/");
            URLConnection connection = url.openConnection();
            connection.connect();
        }
        catch (Exception e)
        {
            System.out.println("Unable to connect to the Internet, using old cache.");
            return;
        }

        Gson gson = new GsonBuilder().serializeNulls().create();

        usedAPI usedAPIName = null;

        try
        {
            FileReader fileReader = new FileReader(this.filePath);
            usedAPIName = gson.fromJson(fileReader, usedAPI.class);
            fileReader.close();
        }
        catch (IOException e)
        {
            System.out.println("Error while reading cache file!");
        }

        if (usedAPIName==null)
        {
            System.out.println("Cache outdated, please wait while cache is being updated.");
            IAPIStrategy api_gios = new API_GIOS();
            createCache(api_gios.getInfo(),api_gios);
            return;
        }

        IAPIStrategy api = null;

        try
        {
            api = (IAPIStrategy) Class.forName(usedAPIName.usedAPI).newInstance();
        }
        catch (Exception e)
        {
            System.out.println("Unable to update cache, wrong API in cache file!");
            return;
        }

        List<Entry> entries = api.getInfo();

        createCache(entries,api);
    }

    private class usedAPI
    {
        String usedAPI;
    }
}
