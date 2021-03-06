import command.CommandCurrentAirIndex;
import model.UtilSingleton;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.naming.CommunicationException;
import java.io.IOException;

public class testCmdCurrAirIndex
{
    @Before
    public void initialize() throws CommunicationException, IOException
    {
        UtilSingleton.getInstance().getCacheService().setFilePath("C:\\Users\\jakub\\Desktop\\Obiektowe\\cache.json");
        UtilSingleton.getInstance().getCacheService().checkCache();
        UtilSingleton.getInstance().getCacheService().loadCache();
    }

    @Test (expected = NullPointerException.class)
    public void testEmptyArguments()
    {
        CommandCurrentAirIndex command = new CommandCurrentAirIndex();
        command.execute("");
    }

    @Test (expected = NullPointerException.class)
    public void testNullArguments()
    {
        CommandCurrentAirIndex command = new CommandCurrentAirIndex();
        command.execute(null);
    }

    @Test (expected = NullPointerException.class)
    public void testNonexistentStation() throws NullPointerException
    {
        CommandCurrentAirIndex command = new CommandCurrentAirIndex();
        command.execute("XYZ");
    }

    @Test
    public void testNormalWork() throws NullPointerException
    {
        CommandCurrentAirIndex command = new CommandCurrentAirIndex();
        command.execute("Kraków, ul. Bujaka");

        Assert.assertNotNull(command.getResult());
    }
}
