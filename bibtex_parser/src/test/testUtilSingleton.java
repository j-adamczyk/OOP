import io.UtilSingleton;
import org.junit.Assert;
import org.junit.Test;

public class testUtilSingleton
{
    @Test
    public void testSingletonExistence()
    {
        Assert.assertNotNull(UtilSingleton.getInstance());
    }

    @Test
    public void testFileNotFound()
    {
        UtilSingleton.getInstance().readFile("Nonexistent path");
        Assert.assertEquals(UtilSingleton.getInstance().getOriginalText(),"");
    }
}
