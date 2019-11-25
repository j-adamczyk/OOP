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
        this.cacheService.filePath = "C:\\Users\\jakub\\Desktop\\Obiektowe\\testCache.json";
    }

    @Test
    public void testCreateCacheFromEmptyData() throws IOException
    {
        List<Entry> entries = new ArrayList<>();
        API_GIOS api_gios = new API_GIOS();
        this.cacheService.createCache(entries,api_gios);

        this.cacheService.loadCache();
        Assert.assertTrue(UtilSingleton.getInstance().entries.isEmpty());
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
        this.cacheService.filePath = "";
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
        this.cacheService.filePath = null;
        this.cacheService.createCache(entries,api_gios);
    }

    @Test (expected = FileNotFoundException.class)
    public void testCheckCacheNonexistentLocation() throws Exception
    {
        this.cacheService.filePath = "";
        this.cacheService.checkCache();

        this.cacheService.loadCache();
    }

    @Test (expected = NullPointerException.class)
    public void testCheckCacheNullLocation() throws CommunicationException
    {
        this.cacheService.filePath = null;
        this.cacheService.checkCache();
    }

    @Test (expected = FileNotFoundException.class)
    public void testLoadCacheNonexistentLocation() throws IOException
    {
        this.cacheService.filePath = "";
        this.cacheService.loadCache();
    }

    @Test (expected = NullPointerException.class)
    public void testLoadCacheNullLocation() throws IOException
    {
        this.cacheService.filePath = null;
        this.cacheService.loadCache();
    }
}
