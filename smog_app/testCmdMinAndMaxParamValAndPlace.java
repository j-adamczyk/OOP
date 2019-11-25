import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.naming.CommunicationException;
import java.io.IOException;

public class testCmdMinAndMaxParamValAndPlace
{
    @Before
    public void initialize() throws CommunicationException, IOException
    {
        UtilSingleton.getInstance().cacheService.filePath = "C:\\Users\\jakub\\Desktop\\Obiektowe\\cache.json";
        UtilSingleton.getInstance().cacheService.checkCache();
        UtilSingleton.getInstance().cacheService.loadCache();
    }

    @Test
    public void testEmptyArguments()
    {
        CommandMinAndMaxParameterValueAndPlace command = new CommandMinAndMaxParameterValueAndPlace();
        command.execute("");

        Assert.assertNotNull(command.getResult());
    }

    @Test
    public void testNullArguments()
    {
        CommandMinAndMaxParameterValueAndPlace command = new CommandMinAndMaxParameterValueAndPlace();
        command.execute(null);

        Assert.assertNotNull(command.getResult());
    }

    @Test
    public void testNonexistentParameter() throws NullPointerException
    {
        CommandMinAndMaxParameterValueAndPlace command = new CommandMinAndMaxParameterValueAndPlace();
        command.execute("XYZ");

        Assert.assertNotNull(command.getResult());
    }

    @Test
    public void testNormalWork() throws NullPointerException
    {
        CommandMinAndMaxParameterValueAndPlace command = new CommandMinAndMaxParameterValueAndPlace();
        command.execute("NO2");

        Assert.assertNotNull(command.getResult());
    }
}
