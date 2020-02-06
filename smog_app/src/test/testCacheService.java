import gios.API_GIOS;
import gios.CacheService;
import model.Entry;
import model.UtilSingleton;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.naming.CommunicationException;
import java.io.*;
import java.util.*;
public class testCacheService
{
    private CacheService cacheService;

    @Before
    public void initialize()
    {
        this.cacheService = new CacheService();
        this.cacheService.setFilePath("C:\\Users\\jakub\\Desktop\\Obiektowe\\testCache.json");
    }

    @Test
    public void testCreateCacheFromEmptyData() throws IOException
    {
        List<Entry> entries = new ArrayList<>();
        API_GIOS api_gios = new API_GIOS();
        this.cacheService.createCache(entries,api_gios);

        this.cacheService.loadCache();
        Assert.assertTrue(UtilSingleton.getInstance().getEntries().isEmpty());
    }

    @Test (expected = NullPointerException.class)
    public void testCreateCacheFromNullData() throws IOException
    {
        this.cacheService.createCache(null,null);

        this.cacheService.loadCache();
    }

    @Test (expected = FileNotFoundException.class)
    public void testUpdateCacheFromNonexistentFile() throws IOException
    {
        List<Entry> entries = new ArrayList<>();

        for (int i=0; i<10; i++)
            entries.add(new Entry());

        API_GIOS api_gios = new API_GIOS();
        this.cacheService.setFilePath("");
        this.cacheService.createCache(entries,api_gios);

        this.cacheService.loadCache();
    }

    @Test (expected = NullPointerException.class)
    public void testUpdateCacheToNullPath()
    {
        List<Entry> entries = new ArrayList<>();

        for (int i=0; i<10; i++)
            entries.add(new Entry());

        API_GIOS api_gios = new API_GIOS();
        this.cacheService.setFilePath(null);
        this.cacheService.createCache(entries,api_gios);
    }

    @Test (expected = FileNotFoundException.class)
    public void testCheckCacheNonexistentLocation() throws Exception
    {
        this.cacheService.setFilePath("");
        this.cacheService.checkCache();

        this.cacheService.loadCache();
    }

    @Test (expected = NullPointerException.class)
    public void testCheckCacheNullLocation() throws CommunicationException
    {
        this.cacheService.setFilePath(null);
        this.cacheService.checkCache();
    }

    @Test (expected = FileNotFoundException.class)
    public void testLoadCacheNonexistentLocation() throws IOException
    {
        this.cacheService.setFilePath("");
        this.cacheService.loadCache();
    }

    @Test (expected = NullPointerException.class)
    public void testLoadCacheNullLocation() throws IOException
    {
        this.cacheService.setFilePath(null);
        this.cacheService.loadCache();
    }
}
